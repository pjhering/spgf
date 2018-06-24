package spgf.core;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.KEY_LAST;
import java.awt.event.KeyListener;

/**
 * The <code>Keyboard</code> provides access to keyboard input.  If event-based
 * input is needed, add a <code>KeyListener</code> to the
 * <code>GamePanel</code>.
 * @author tinman
 */
public class Keyboard implements KeyListener
{

    private final boolean[] current = new boolean[KEY_LAST];
    private final boolean[] previous = new boolean[KEY_LAST];

    public Keyboard()
    {
    }

    /**
     * @param key a keycode defined in the <code>KeyEvent</code> class.
     * @return is the specified key in the down (pressed) state.
     */
    public boolean isDown(int key)
    {
        return current[key];
    }

    /**
     * @param key a keycode defined in the <code>KeyEvent</code> class.
     * @return returns true only just after the key has been pressed.
     */
    public boolean isPressed(int key)
    {
        return current[key] && !previous[key];
    }

    /**
     * The <code>update</code> method should be called after all queries have
     * been made.
     */
    public void update()
    {
        for (int i = 0; i < current.length; i++)
        {
            previous[i] = current[i];
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        current[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        current[e.getKeyCode()] = false;
    }
}
