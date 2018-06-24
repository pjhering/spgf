package spgf.platform;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;

public class Stage
{

    private final Background background;
    private final TileLayer tileLayer;
    private final List<Actor> actors;
    private final List<Actor> added;
    private final List<Actor> removed;

    public Stage(Background bg, TileLayer tl, List<Actor> a)
    {
        this.background = requireNonNull(bg);
        this.tileLayer = requireNonNull(tl);
        this.actors = requireNonNull(a);
        this.added = new ArrayList<>();
        this.removed = new ArrayList<>();
    }

    public void update(long elapsed, Viewport vp)
    {
        int size = actors.size();
        Point p = vp.getOffset();

        for (int i = 0; i < size; i++)
        {
            Actor a1 = actors.get(i);
            a1.update(elapsed, p);
            a1.act(elapsed);
        }

        for (int i = 0; i < size; i++)
        {
            Actor a1 = actors.get(i);
            Tile[] four = tileLayer.get(a1);
            TILES:
            for (Tile t : four)
            {
                if (t != null && t.blocked)
                {
                    a1.hitTiles(four);
                    break TILES;
                }
            }

            for (int j = i + 1; j < size; j++)
            {
                Actor a2 = actors.get(j);

                if (a1 != a2 && a1.intersects(a2))
                {
                    a1.hitActor(a2);
                }
            }

            added.addAll(a1.added);
            a1.added.clear();

            removed.addAll(a1.removed);
            a1.removed.clear();
        }

        actors.addAll(added);
        added.clear();

        actors.removeAll(removed);
        removed.clear();
    }

    public void draw(Graphics g, Viewport vp)
    {
        background.draw(g, vp);
        tileLayer.draw(g, vp);
        for (Actor a : actors)
        {
            a.draw(g);
        }
    }

    public Background getBackground()
    {
        return background;
    }

    public TileLayer getTileLayer()
    {
        return tileLayer;
    }

    public List<Actor> getActors()
    {
        return actors;
    }
}
