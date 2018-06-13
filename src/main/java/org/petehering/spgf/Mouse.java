package org.petehering.spgf;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

public class Mouse implements MouseInputListener
{

    private boolean down;
    private int button;
    private int clickCount;
    private Point location;

    public Mouse()
    {
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        this.down = true;
        this.button = e.getButton();
        this.clickCount = e.getClickCount();
        this.location = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        this.down = false;
        this.button = -1;
        this.clickCount = 0;
        this.location = null;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        this.location = e.getPoint();
    }

    public boolean isDown()
    {
        return down;
    }

    public int getButton()
    {
        return button;
    }

    public int getClickCount()
    {
        return clickCount;
    }

    public Point getLocation()
    {
        return location;
    }
}
