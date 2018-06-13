package org.petehering.spgf;

import java.awt.Point;
import static org.petehering.spgf.Utility.clamp;
import static org.petehering.spgf.Utility.requireGreaterThan;


public class Viewport
{
    private float x;
    private float y;
    private final float width;
    private final float height;
    private final Point offset;
    
    public Viewport (float width, float height)
    {
        this.x = 0f;
        this.y = 0f;
        this.width = requireGreaterThan (0f, width);
        this.height = requireGreaterThan (0f, height);
        this.offset = new Point ();
    }
    
    public Point getOffset ()
    {
        return offset;
    }
    
    public void update (Stage stage)
    {
        Actor a = stage.getPlayer();
        
        if (a != null)
        {
            float _x = a.getCenterX ();
            float _y = a.getCenterY ();
            
            this.x = clamp (_x, 0f, stage.width - width);
            this.y = clamp (_y, 0f, stage.height - height);
        }
        else
        {
            x = 0f;
            y = 0f;
        }
        
        this.offset.setLocation(x, y);
    }
}
