package spgf.core;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.KEY_FIRST;
import static java.awt.event.KeyEvent.KEY_LAST;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener
{

    private final boolean[] current = new boolean[KEY_LAST];
    private final boolean[] previous = new boolean[KEY_LAST];

    public Keyboard()
    {
    }

    public boolean isDown(int key)
    {
        return current[key];
    }

    public boolean isPressed(int key)
    {
        return current[key] && !previous[key];
    }

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