package org.petehering.spgf;

import java.awt.Graphics;
import java.util.HashSet;
import static java.util.Objects.requireNonNull;
import java.util.Set;

public class Stage implements StageManager
{
    
    private final Background background;
    private final TileLayer tiles;
    private final Viewport viewport;
    public final int width;
    public final int height;
    private final Set<Actor> actors;
    private final Set<Actor> added;
    private final Set<Actor> removed;
    private Actor player;
    
    public Stage (Background background, TileLayer tiles, Viewport viewport)
    {
        this.background = background;
        this.tiles = requireNonNull (tiles);
        this.width = tiles.getLayerWidth();
        this.height = tiles.getLayerHeight();
        this.viewport = requireNonNull (viewport);
        this.actors = new HashSet<> ();
        this.added = new HashSet<> ();
        this.removed = new HashSet<> ();
    }
    
    public void draw (Graphics g)
    {
        
    }
    
    public void update (long elapsed)
    {
        actors.forEach ((Actor a) -> a.act (elapsed));
        doActorCollisions ();
        doAddRemove ();
        
        viewport.update(this);
    }

    private void doActorCollisions ()
    {
        actors.forEach ((Actor a) ->
        {
            actors.forEach ((Actor b) ->
            {
                if (a != b && a.intersects (b))
                {
                    a.hitActor (b);
                }
            });
        });
    }

    private void doAddRemove ()
    {
        this.actors.addAll (added);
        added.clear ();

        this.actors.removeAll (removed);
        removed.clear ();
    }

    @Override
    public boolean add (Actor actor)
    {
        return added.add (actor);
    }

    @Override
    public boolean remove (Actor actor)
    {
        return removed.add (actor);
    }

    public Actor getPlayer()
    {
        return player;
    }

    public void setPlayer(Actor player)
    {
        this.player = player;
    }
}
