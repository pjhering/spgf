package spgf.platform;

import java.awt.Graphics;
import java.awt.Point;
import static spgf.core.Utility.requireGreaterThan;
import static spgf.core.Utility.requireNonNull;


public abstract class Actor
{
    protected final State[] states;
    protected int index;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    
    public Actor (State[] states, float x, float y, float width, float height)
    {
        this.states = requireNonNull (states);
        this.index = 0;
        this.x = x;
        this.y = y;
        this.width = requireGreaterThan (0f, width);
        this.height = requireGreaterThan (0f, height);
    }
    
    public abstract void act (long elapsed);
    
    protected void setState (int i)
    {
        if (0 <= i && i < states.length && i != index)
        {
            index = i;
            states[index].reset();
        }
    }
    
    public void draw (Graphics g)
    {
        states[index].draw(g);
    }
    
    void update (long elapsed, Point offset)
    {
        float _x = x + (width / 2) - offset.x;
        float _y = y + (height / 2) - offset.y;
        states[index].update(elapsed, _x, _y);;
    }
}
