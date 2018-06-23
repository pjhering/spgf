package spgf.platform;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;


public class Tile
{
    public final BufferedImage image;
    public final int id;
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final boolean blocked;
    public final boolean hidden;
    
    public Tile(BufferedImage img, int id, int x, int y, int width, int height)
    {
        this(img, id, x, y, width, height, true, false);
    }
    
    public Tile(BufferedImage img, int id, int x, int y, int width, int height, boolean blocked, boolean hidden)
    {
        this.image = requireNonNull(img);
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.blocked = blocked;
        this.hidden = hidden;
    }

    void draw(Graphics g, Point p)
    {
        g.drawImage(image, x - p.x, y - p.y, width, height, null);
    }
}
