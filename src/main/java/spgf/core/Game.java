package spgf.core;
/**
 * The <code>Game</code> interface defines the required methods for any game. It
 * is used by the <code>Loop</class>.
 * @author tinman
 */
public interface Game
{

    /**
     * The <code>start</code> method is called once by the <code>Loop</code>
     * instance just prior to entering game loop.
     */
    public void start();

    /**
     * The <code>tick</code> method is called by the <code>Loop</code> instance
     * once per game tick.
     * @param elapsedMilliseconds number of milliseconds that have passed since
     * the last call.
     */
    public void tick(long elapsedMilliseconds);

    /**
     * The <code>stop</code> method is called once by the <code>Loop</code>
     * instance after the game loop has exited.
     */
    public void stop();
}
