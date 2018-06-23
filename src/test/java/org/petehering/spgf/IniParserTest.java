package org.petehering.spgf;

import java.io.IOException;

public class IniParserTest
{

    public static void main(String[] args) throws IOException
    {
        IniParser parser = new IniParser("/test01.ini");
        Tileset ts = parser.getTileset();
        TileLayer tl = parser.getTileLayer();
    }
}
