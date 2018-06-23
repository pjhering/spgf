package spgf.core;

import static java.lang.System.currentTimeMillis;
import static java.util.Objects.requireNonNull;

public class Loop extends Thread
{

    private final Game game;
    private final long millisecondsPerTick;
    private long startTime;
    private long elapsedTime;
    private boolean running;

    public Loop(Game game, int ticksPerSecond)
    {
        super("loop");

        this.game = requireNonNull(game);
        this.millisecondsPerTick = 1000L / ticksPerSecond;
    }

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
