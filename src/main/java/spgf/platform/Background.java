package spgf.platform;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;


public class Background
{
    private final BufferedImage image;
    private final int width;
    private final int height;
    
    public Background (BufferedImage image)
    {
        this.image = requireNonNull (image);
        this.width = image.getWidth();
        this.height = image.getHeight();
    }
    
    public void draw (Graphics g, Viewport vp)
    {
        Point p = vp.getOffset();
        for (int x = (p.x % width) - width; x < vp.width + width; x += width)
        {
            for (int y = (p.y % height) - height; y < vp.height + height; y += height)
            {
                g.drawImage(image, x, y, null);
            }
        }
    }
}
