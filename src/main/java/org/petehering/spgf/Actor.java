package org.petehering.spgf;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import static java.lang.Math.round;

public abstract class Actor
{
    protected Animation[] animations;
    protected int index;
    private int type;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float moveX;
    protected float moveY;
    protected float deltaX;
    protected float deltaY;
    protected final Point topLeft;
    protected final Point topRight;
    protected final Point bottomLeft;
    protected final Point bottomRight;
    protected StageManager stageManager;
    
    public Actor (float x, float y, float width, float height)
    {
        this (null, x, y, width, height);
    }
    
    public Actor (Animation[] animations, float x, float y, float width, float height)
    {
        this.animations = animations;
        this.index = 0;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.moveX = 0f;
        this.moveY = 0f;
        this.deltaX = 0f;
        this.deltaY = 0f;
        this.topLeft = new Point ();
        this.topRight = new Point ();
        this.bottomLeft = new Point ();
        this.bottomRight = new Point ();
    }
    
    public abstract void act (long elapsed);
    
    public abstract void hitTiles (Tile topLeft, Tile topRight, Tile bottomLeft, Tile bottomRight);
    
    public abstract void hitActor (Actor other);
    
    public boolean intersects (Actor that)
    {
        return this.x < that.x + that.width
            && that.x < this.x + this.width
            && this.y < that.y + that.height
            && that.y < this.y + this.height;
    }
    
    public boolean isA (int type)
    {
        return (this.type & type) == type;
    }
    
    public boolean contains (Actor that)
    {
        return this.x <= that.x
            && this.width >= that.width
            && this.y <= that.y
            && this.height >= that.height;
    }
    
    public void move (long elapsed)
    {
        this.moveX = deltaX * elapsed;
        this.moveY = deltaY * elapsed;
        
        this.x += moveX;
        this.y += moveY;
        
        this.topLeft.setLocation (x, y);
        this.topRight.setLocation (x + width, y);
        this.bottomLeft.setLocation (x, y + height);
        this.bottomRight.setLocation (x + width, y + height);
    }
    
    public void draw (Graphics g, int xOffset, int yOffset)
    {
        if (animations != null)
        {
            BufferedImage image = animations[index].getCurrentFrame ();
            int _x = round (x + ((width - image.getWidth ()) / 2) - xOffset);
            int _y = round (y + ((height - image.getHeight ()) / 2) - yOffset);
            g.drawImage (image, _x, _y, image.getWidth (), image.getHeight (), null);
        }
    }
    
    public void changeAnimation (int i)
    {
        if (animations == null)
        {
            return;
        }
        
        if (0 <= i && i < animations.length)
        {
            index = i;
            animations[index].reset ();
        }
    }

    public float getX ()
    {
        return x;
    }

    public float getY ()
    {
        return y;
    }

    public float getWidth ()
    {
        return width;
    }

    public float getHeight ()
    {
        return height;
    }

    public void setDeltaX (float deltaX)
    {
        this.deltaX = deltaX;
    }

    public void setDeltaY (float deltaY)
    {
        this.deltaY = deltaY;
    }

    public Point getTopLeft ()
    {
        return topLeft;
    }

    public Point getTopRight ()
    {
        return topRight;
    }

    public Point getBottomLeft ()
    {
        return bottomLeft;
    }

    public Point getBottomRight ()
    {
        return bottomRight;
    }

    public void setStageManager (StageManager stageManager)
    {
        this.stageManager = stageManager;
    }

    public int getType ()
    {
        return type;
    }

    public void setType (int type)
    {
        this.type = type;
    }
}
