package spgf.core;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/**
 * The <code>Mouse</code> provides access to mouse input.  If event-based
 * input is needed, add a <code>MouseListener</code> or 
 * <code>MouseMotionListener</code> to the <code>GamePanel</code>.
 * @author tinman
 */
public class Mouse implements MouseInputListener
{

    private boolean down;
    private int button;
    private int clickCount;
    private Point location;
    private int deltaX;
    private int deltaY;

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
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        this.down = false;
        this.button = -1;
        this.clickCount = 0;
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
        Point p = e.getPoint();
        if (location != null)
        {
            this.deltaX = p.x - location.x;
            this.deltaY = p.y - location.y;
        }
        this.location = p;
    }

    /**
     * return turns true if a mouse button is currently pressed.
     * @return 
     */
    public boolean isDown()
    {
        return down;
    }

    /**
     * @return the button number or -1 if no button is pressed.
     */
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

    public int getDeltaX()
    {
        return deltaX;
    }

    public int getDeltaY()
    {
        return deltaY;
    }
}
