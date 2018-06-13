package org.petehering.spgf;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import static org.petehering.spgf.Utility.getSubimageArray;
import static org.petehering.spgf.Utility.requireInRange;

/**
 * A Tileset is an image that is divided into uniformly sized subimages by rows
 * and columns.
 *
 * @author tinman
 */
public class Tileset
{

    private final BufferedImage image;
    private final BufferedImage[] subimages;
    public final int width;
    public final int height;
    public final int rows;
    public final int columns;
    public final int tileWidth;
    public final int tileHeight;

    public Tileset(String path, int rows, int columns)
    {
        try (InputStream stream = getClass().getResourceAsStream(path))
        {
            this.image = ImageIO.read(stream);
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.rows = requireInRange(rows, 0, height);
            this.columns = requireInRange(columns, 0, width);
            this.tileHeight = height / rows;
            this.tileWidth = width / columns;
            this.subimages = subimages();
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public BufferedImage getSubImage(int i)
    {
        return subimages[i];
    }

    public int length()
    {
        return subimages.length;
    }

    private BufferedImage[] subimages()
    {
        return getSubimageArray(image, 0, 0, image.getWidth(), image.getHeight(), rows, columns);
    }
}
