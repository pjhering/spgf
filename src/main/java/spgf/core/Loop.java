package spgf.core;

import static java.lang.System.currentTimeMillis;
import static java.util.Objects.requireNonNull;

/**
 * The <code>Loop</code> is a <code>Thread</code> that manages calls to a
 * <code>Game</code>'s <code>tick</code> method.  It attempts to make a call
 * a given number of times per second.
 * @author tinman
 */
public class Loop extends Thread
{

    private final Game game;
    private final long millisecondsPerTick;
    private long startTime;
    private long elapsedTime;
    private boolean running;

    /**
     * 
     * @param game the <code>Game</code> to be managed.
     * @param ticksPerSecond the target number of calls to <code>tick</code>
     */
    public Loop(Game game, int ticksPerSecond)
    {
        super("loop");

        this.game = requireNonNull(game);
        this.millisecondsPerTick = 1000L / ticksPerSecond;
    }

    /**
     * Calling <code>quit</code> causes the thread to exit before the next call
     * to the <code>tick</code> method.
     */
    public synchronized void quit()
    {
        running = false;
    }

    @Override
    public void run()
    {
        running = true;

        game.start();

        startTime = currentTimeMillis();

        while (running)
        {
            game.tick(elapsedTime);

            long currentTime = currentTimeMillis();
            elapsedTime = currentTime - startTime;
            startTime = currentTime;

            if (elapsedTime > millisecondsPerTick)
            {
                long sleepTime = elapsedTime - millisecondsPerTick;

                try
                {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException ex)
                {
                    ex.printStackTrace();
                    running = false;
                }
            }
        }

        game.stop();
    }
}
