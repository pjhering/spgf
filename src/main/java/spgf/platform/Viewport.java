package spgf.platform;

import java.awt.Point;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static spgf.core.Utility.requireGreaterThan;

public class Viewport
{

    private float x;
    private float y;
    public final float width;
    public final float height;
    private final Point offset;

    public Viewport(float width, float height)
    {
        this.width = requireGreaterThan(0f, width);
        this.height = requireGreaterThan(0f, height);
        this.offset = new Point();
    }

    public void update(float centerX, float centerY, TileLayer tileLayer)
    {
        float targetX = centerX - (width / 2f);
        float targetY = centerY - (height / 2f);

        float maxX = tileLayer.width - width;
        float maxY = tileLayer.height - height;

        x = max(0f, min(targetX, maxX));
        y = max(0f, min(targetY, maxY));

        offset.setLocation(x, y);
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    boolean contains(Actor actor)
    {
        return this.x < actor.x + actor.width
                && actor.x < this.x + this.width
                && this.y < actor.y + actor.height
                && actor.y < this.x + this.height;
    }

    boolean contains(Tile tile)
    {
        return x < tile.x + tile.width
                && y < tile.y + tile.height
                && tile.x < x + width
                && tile.y < y + height;
    }

    public Point getOffset()
    {
        return offset;
    }
}
