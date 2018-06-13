package org.petehering.spgf;

import java.util.HashSet;
import java.util.Set;

public class Stage implements StageManager
{
    private final Set<Actor> actors;
    private final Set<Actor> added;
    private final Set<Actor> removed;

    public Stage ()
    {
        this.actors = new HashSet<> ();
        this.added = new HashSet<> ();
        this.removed = new HashSet<> ();
    }
    
    public void update (long elapsed)
    {
        actors.forEach ((Actor a) -> a.act (elapsed));
        doActorCollisions ();
        doAddRemove ();
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
}
