package spgf.core;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import static java.util.Objects.requireNonNull;
import javax.imageio.ImageIO;
import static spgf.core.Utility.subimages;

public class Spritesheet
{

    private BufferedImage image;

    public Spritesheet(BufferedImage image)
    {
        this.image = requireNonNull(image);
    }

    public Spritesheet(String path)
    {
        try (InputStream stream = getClass().getResourceAsStream(path))
        {
            this.image = ImageIO.read(stream);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public BufferedImage[] array(int x, int y, int width, int height, int rows, int columns)
    {
        return subimages(image, x, y, width, height, rows, columns);
    }

    public BufferedImage[] array(int rows, int columns)
    {
        return array(0, 0, image.getWidth(), image.getHeight(), rows, columns);
    }

    public BufferedImage subimage(int x, int y, int width, int height)
    {
        return image.getSubimage(x, y, width, height);
    }
}
