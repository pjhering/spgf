package org.petehering.spgf;

import spgf.platform.IniParser;
import spgf.platform.TileLayer;
import java.io.IOException;
import static java.lang.System.out;
import spgf.platform.Tile;

public class IniParserTest
{

    public static void main(String[] args) throws IOException
    {
        IniParser parser = new IniParser("/test01.ini");
        TileLayer tl = parser.getTileLayer();
        
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
    }
}
