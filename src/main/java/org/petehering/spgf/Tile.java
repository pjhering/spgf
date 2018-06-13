package org.petehering.spgf;

import static java.awt.Color.CYAN;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;

/**
 * A Tile is an image that is displayed at a specific location in a tile map.
 *
 * @author tinman
 */
public class Tile
{

    private final int id;
    private final BufferedImage image;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final boolean blocked;

    public Tile(int id, BufferedImage image, int x, int y, int w, int h, boolean b)
    {
        this.id = id;
        this.image = requireNonNull(image);
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.blocked = b;
    }

    @Override
    public String toString()
    {
        return new StringBuilder()
                .append("Tile{id=")
                .append(id)
                .append(", x=")
                .append(x)
                .append(", y=")
                .append(y)
                .append(", width=")
                .append(width)
                .append(", height=")
                .append(height)
                .append(", blocked=")
                .append(blocked)
                .append("}")
                .toString();
    }

    public void draw(Graphics g, int xOffset, int yOffset)
    {
        int _x = x - xOffset;
        int _y = y - yOffset;
        g.drawImage(image, _x, _y, width, height, null);
        g.setColor(CYAN);
        g.drawRect(_x, _y, width, height);
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean isBlocked()
    {
        return blocked;
    }
}
