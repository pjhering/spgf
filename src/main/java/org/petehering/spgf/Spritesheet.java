package org.petehering.spgf;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import static java.util.Objects.requireNonNull;
import javax.imageio.ImageIO;

public class Spritesheet
{

    private BufferedImage image;
    
    public Spritesheet (BufferedImage image)
    {
        this.image = requireNonNull (image);
    }

    public Spritesheet(String path)
    {
        try (InputStream stream = getClass().getResourceAsStream(path))
        {
            this.image = ImageIO.read(stream);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public BufferedImage[] getImageArray(int x, int y, int width, int height, int rows, int columns)
    {
        return Utility.getSubimageArray(image, x, y, width, height, rows, columns);
    }

    public BufferedImage[] getImageArray(int rows, int columns)
    {
        return getImageArray(0, 0, image.getWidth(), image.getHeight(), rows, columns);
    }

    public BufferedImage getImage(int x, int y, int width, int height)
    {
        return image.getSubimage(x, y, width, height);
    }
}
