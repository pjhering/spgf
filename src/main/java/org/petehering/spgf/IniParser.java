package org.petehering.spgf;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.lang.System.out;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import static org.petehering.spgf.Utility.SPACES;
import static org.petehering.spgf.Utility.subimages;

public class IniParser
{

    private String line;
    private String[] tokens;
    private int lineNumber;
    private Map<String, BufferedImage> images;
    private Tileset tileset;
    private TileLayer tileLayer;

    public IniParser(String path) throws IOException
    {
        images = new HashMap<>();

        try (InputStream stream = getClass().getResourceAsStream(path))
        {
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);

            while (next(buffer))
            {
                switch (line)
                {
                    case "[tileset]":
                        parseTileset(buffer);
                        break;
                    case "[tilelayer]":
                        parseTileLayer(buffer);
                        break;
                    default:
                        throw exception("unexpected input: " + line);
                }
            }
        }
    }

    private void parseTileset(BufferedReader buffer) throws IOException
    {
//        if (!next (buffer)) throw exception ("expected tileset configuration");
        next(buffer);

        int tileWidth = parseInt(tokens[1]);
        int tileHeight = parseInt(tokens[2]);
        int rows = parseInt(tokens[3]);
        int columns = parseInt(tokens[4]);

        BufferedImage source = loadImage(tokens[0]);
        if (source == null)
        {
            out.println("source is null");
        }

        BufferedImage[] subimages = subimages(source, rows, columns);
        if (subimages == null)
        {
            out.println("subimages array is null");
        }

        tileset = new Tileset(subimages, tileWidth, tileHeight);

        for (int i = 0; i < subimages.length; i++)
        {
            if (!next(buffer))
            {
                throw exception("expected tileset element definition");
            }

            int index = parseInt(tokens[0]);
            boolean blocked = parseBoolean(tokens[1]);
            boolean hidden = parseBoolean(tokens[2]);

            tileset.setBlocked(index, blocked);
            tileset.setHidden(index, hidden);
        }
    }

    private void parseTileLayer(BufferedReader buffer) throws IOException
    {
        if (!next(buffer))
        {
            throw exception("expected tilelayer configuration");
        }

        int rows = parseInt(tokens[0]);
        int columns = parseInt(tokens[1]);

        tileLayer = new TileLayer(tileset, rows, columns);

        for (int r = 0; r < rows; r++)
        {
            if (!next(buffer))
            {
                throw exception("expected tilelayer row");
            }

            for (int c = 0; c < columns; c++)
            {
                if (c >= tokens.length)
                {
                    throw exception("expected tilelayer column");
                }

                int id = parseInt(tokens[c]);
                tileLayer.setId(r, c, id);
            }
        }
    }

    private IOException exception(String message)
    {
        return new IOException(format("%s [line: %d]", message, lineNumber));
    }

    private BufferedImage loadImage(String path) throws IOException
    {
        String key = path.startsWith("/") ? path : "/" + path;

        if (!images.containsKey(key))
        {
            try (InputStream stream = getClass().getResourceAsStream(key))
            {
                BufferedImage image = ImageIO.read(stream);
                images.put(key, image);
            }
        }

        return images.get(key);
    }

    private boolean next(BufferedReader buffer) throws IOException
    {
        String in = buffer.readLine();

        if (in == null)
        {
            return false;
        }

        in = in.trim();
        lineNumber += 1;

        if (in.isEmpty() || in.startsWith("#"))
        {
            return next(buffer);
        }

        line = in;
        tokens = line.split(SPACES);

        return true;
    }

    public Tileset getTileset()
    {
        return tileset;
    }

    public TileLayer getTileLayer()
    {
        return tileLayer;
    }
}
