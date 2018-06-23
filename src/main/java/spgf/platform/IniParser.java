package spgf.platform;

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
import static spgf.core.Utility.SPACES;
import static spgf.core.Utility.subimages;

public class IniParser
{

    private String line;
    private String[] tokens;
    private int lineNumber;
    private Map<String, BufferedImage> images;
    private TileLayer tileLayer;
    private BufferedImage[] tileset;
    private boolean[] blocked;
    private boolean[] hidden;

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
        if (!next (buffer)) throw exception ("expected tileset configuration");

        int tileWidth = parseInt(tokens[1]);
        int tileHeight = parseInt(tokens[2]);
        int rows = parseInt(tokens[3]);
        int columns = parseInt(tokens[4]);

        BufferedImage source = loadImage(tokens[0]);
        tileset = subimages(source, rows, columns);
        blocked = new boolean[tileset.length];
        hidden = new boolean[tileset.length];

        for (int i = 0; i < tileset.length; i++)
        {
            if (!next(buffer))
            {
                throw exception("expected tileset element definition");
            }

            int index = parseInt(tokens[0]);
            blocked[index] = parseBoolean(tokens[1]);
            hidden[index] = parseBoolean(tokens[2]);
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
        int tileWidth = parseInt(tokens[2]);
        int tileHeight = parseInt(tokens[3]);

        tileLayer = new TileLayer(rows, columns, tileWidth, tileHeight);

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

                try
                {
                    int id = parseInt(tokens[c]);
                    
                    if (id <= id && id < tileset.length)
                    {
                        tileLayer.set(r, c, tileset[id], id, blocked[id], hidden[id]);
                    }
                }
                catch (NumberFormatException ex)
                {
                    // ignore
                }
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
        out.printf("%4d: %s\n", lineNumber, in);

        if (in.isEmpty() || in.startsWith("#"))
        {
            return next(buffer);
        }

        line = in;
        tokens = line.split(SPACES);

        return true;
    }

    public TileLayer getTileLayer()
    {
        return tileLayer;
    }
}
