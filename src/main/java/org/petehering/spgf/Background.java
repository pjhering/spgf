package org.petehering.spgf;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;

public class Background
{

    private final BufferedImage image;
    private final int width;
    private final int height;

    public Background(BufferedImage image)
    {
        this.image = requireNonNull(image);
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public void draw(Graphics g, int xOffset, int yOffset)
    {
        int x = xOffset % width;
        int y = yOffset % height;

        g.drawImage(image, x, y, null);

        if (x < 0)
        {
            g.drawImage(image, x + width, y, null);
        }

        if (y < 0)
        {
            g.drawImage(image, x, y + height, null);
        }

        if (x > 0)
        {
            g.drawImage(image, x - width, y, null);
        }

        if (y > 0)
        {
            g.drawImage(image, x, y - height, null);
        }
    }
}
