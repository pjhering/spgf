package spgf.platform;


public class TestActor extends Actor
{

    public TestActor(State[] states, float x, float y, float width, float height)
    {
        super(states, x, y, width, height);
    }

    @Override
    public void act(long elapsed)
    {
    }

    @Override
    public void hitTiles(Tile[] tiles)
    {
    }

    @Override
    public void hitActor(Actor a)
    {
    }
}
