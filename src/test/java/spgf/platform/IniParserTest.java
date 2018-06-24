package spgf.platform;

import java.io.IOException;
import static java.lang.System.out;
import java.util.List;

public class IniParserTest
{

    public static void main(String[] args) throws IOException
    {
        IniParser parser = new IniParser("/test01.ini");
        Background bg = parser.getBackground();
        TileLayer tl = parser.getTileLayer();
        State[] player = parser.getStates("testStates1");
        List<Actor> actors = parser.getActors ();
        
        for (int r = 0; r < tl.rows; r++)
        {
            for (int c = 0; c < tl.columns; c++)
            {
                Tile t = tl.get(r, c);
                
                if (t != null)
                {
                    out.printf ("%3d", tl.get(r, c).id);
                }
                else
                {
                    out.print ("   ");
                }
            }
            out.println ();
        }
        
        for (State s : player)
        {
            out.printf("%2d: %b\n", s.id, s.loop);
        }
        
        for (Actor a : actors)
        {
            out.println (a.getClass().getSimpleName());
        }
    }
}
