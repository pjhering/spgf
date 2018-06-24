package spgf.core;

import java.awt.Color;
import static java.awt.Color.BLACK;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * The <code>GamePanel</code> is the visual player interface.  It provides a
 * <code>Graphics</code> context for rendering the game and a
 * <code>Keyboard</code> and <code>Mouse</code> object to access user input.
 * @author tinman
 */
public class GamePanel extends JComponent
{

    private final BufferedImage page1;
    private final BufferedImage page2;
    private BufferedImage front;
    private final Keyboard keyboard;
    private final Mouse mouse;

    public GamePanel(int width, int height)
    {
        this(new Dimension(width, height));
    }

    public GamePanel(Dimension size)
    {
        this.setSize(size);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);

        this.page1 = new BufferedImage(size.width, size.height, TYPE_INT_ARGB);
        this.page2 = new BufferedImage(size.width, size.height, TYPE_INT_ARGB);
        this.front = page1;

        this.keyboard = new Keyboard();
        this.addKeyListener(keyboard);

        this.mouse = new Mouse();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);

        this.setFocusable(true);
    }

    /**
     * The <code>openInWindow</code> method is provided as a convienence.
     * @param title the <code>JFrame</code> title
     * @return the newly created <code>JFrame</code>.
     */
    public JFrame openInWindow(String title)
    {
        JFrame frame = new JFrame(title);
        frame.setContentPane(this);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        return frame;
    }

    public Mouse getMouse()
    {
        return mouse;
    }

    public Keyboard getKeyboard()
    {
        return keyboard;
    }

    /**
     * Fills the buffer with black
     */
    public void clear()
    {
        clear(BLACK);
    }

    /**
     * Fills the buffer with the provided color
     * @param color the clear <code>Color</code>
     */
    public void clear(Color color)
    {
        Graphics g = this.getDrawGraphics();
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * The <code>Graphics</code> context that should be used to render the game
     * @return the graphics context
     */
    public Graphics getDrawGraphics()
    {
        return ((front == page1) ? page2 : page1).getGraphics();
    }

    /**
     * Flips the buffers and draws the front buffer to the screen. This must be
     * called to make whatever rendering has been performed visible.
     */
    public void present()
    {
        front = (front == page1) ? page2 : page1;
        repaint();
        getToolkit().sync();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(front, 0, 0, this);
    }
}
