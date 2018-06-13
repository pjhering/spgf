package org.petehering.spgf;

public interface Game
{

    public void start();

    public void tick(long elapsedMilliseonds);

    public void stop();
}
