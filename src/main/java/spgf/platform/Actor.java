package spgf.platform;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
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
    protected Set<Actor> added;
    protected Set<Actor> removed;
    
    public Actor (State[] states, float x, float y, float width, float height)
    {
        this.states = requireNonNull (states);
        this.index = 0;
        this.x = x;
        this.y = y;
        this.width = requireGreaterThan (0f, width);
        this.height = requireGreaterThan (0f, height);
        this.added = new HashSet<>();
        this.removed = new HashSet<>();
    }
    
    public abstract void act (long elapsed);
    
    protected boolean add (Actor actor)
    {
        return added.add(actor);
    }
    
    protected boolean remove (Actor actor)
    {
        return removed.add(actor);
    }
    
    public boolean intersects (Actor that)
    {
        return  this.x < that.x + that.width &&
                that.x < this.x + this.width &&
                this.y < that.y + that.height &&
                that.y < this.y + this.height;
    }
    
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
