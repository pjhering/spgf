package org.petehering.spgf;

import java.awt.image.BufferedImage;
import static org.petehering.spgf.Utility.requireGreaterThan;
import static org.petehering.spgf.Utility.requireNonNull;

/**
 * A Tileset is a collection of uniformly sized images that may be blocked and
 * may be hidden.
 * @author tinman
 */
public class Tileset
{
    private BufferedImage[] images;
    public final int tileWidth;
    public final int tileHeight;
    private boolean[] blocked;
    private boolean[] hidden;
    
    public Tileset (BufferedImage[] images, int tileWidth, int tileHeight)
    {
        this.images = requireNonNull (images);
        this.tileWidth = requireGreaterThan (0, tileWidth);
        this.tileHeight = requireGreaterThan (0, tileHeight);
        this.hidden = new boolean[images.length];
        this.blocked = new boolean[images.length];
    }
    
    public int length ()
    {
        return images.length;
    }
    
    public BufferedImage getImage (int i)
    {
        return images[i];
    }
    
    public boolean isHidden (int i)
    {
        return hidden[i];
    }
    
    public void setHidden (int i, boolean b)
    {
        this.hidden[i] = b;
    }
    
    public boolean isBlocked (int i)
    {
        return blocked[i];
    }
    
    public void setBlocked (int i, boolean b)
    {
        this.blocked[i] = b;
    }
}
