package spgf.platform;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.lang.System.out;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import spgf.core.Spritesheet;
import static spgf.core.Utility.SPACES;
import static spgf.core.Utility.subimages;

public class IniParser
{

    private static final Class[] ACTOR_ARGS =
    {
        State[].class, float.class, float.class, float.class, float.class
    };

    private String line;
    private String[] tokens;
    private int lineNumber;
    private Map<String, BufferedImage> imageMap;
    private Map<String, State[]> statesMap;
    private List<Actor> actorsList;
    private Background background;
    private TileLayer tileLayer;
    private BufferedImage[] tileset;
    private boolean[] blocked;
    private boolean[] hidden;
    private Stage stage;

    public IniParser(String path) throws IOException
    {
        imageMap = new HashMap<>();
        statesMap = new HashMap<>();
        actorsList = new ArrayList<>();

        try (InputStream stream = getClass().getResourceAsStream(path))
        {
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);

            while (next(buffer))
            {
                switch (line)
                {
                    case "[background]":
                        parseBackground(buffer);
                        break;
                    case "[tileset]":
                        parseTileset(buffer);
                        break;
                    case "[tilelayer]":
                        parseTileLayer(buffer);
                        break;
                    case "[states]":
                        parseStates(buffer);
                        break;
                    case "[actors]":
                        parseActors(buffer);
                        break;
                    default:
                        throw exception("unexpected input: " + line);
                }
            }

            stage = new Stage(background, tileLayer, actorsList);
        }
    }

    private void parseBackground(BufferedReader buffer) throws IOException
    {
        if (!next(buffer))
        {
            throw exception("expected background configuration");
        }

        background = new Background(loadImage(tokens[0]));
    }

    private void parseTileset(BufferedReader buffer) throws IOException
    {
        if (!next(buffer))
        {
            throw exception("expected tileset configuration");
        }

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

    private void parseStates(BufferedReader buffer) throws IOException
    {
        if (!next(buffer))
        {
            throw exception("expected states configuration");
        }

        String key = tokens[0];
        Spritesheet source = new Spritesheet(loadImage(tokens[1]));
        int count = parseInt(tokens[2]);
        State[] states = new State[count];

        for (int i = 0; i < count; i++)
        {
            if (next(buffer))
            {
                switch (tokens.length)
                {
                    case 4: // single frame
                    {
                        int x = parseInt(tokens[0]);
                        int y = parseInt(tokens[1]);
                        int w = parseInt(tokens[2]);
                        int h = parseInt(tokens[3]);
                        states[i] = new State(i, source.subimage(x, y, w, h));
                    }
                    break;

                    case 8: // multiple frame
                    {
                        int x = parseInt(tokens[0]);
                        int y = parseInt(tokens[1]);
                        int w = parseInt(tokens[2]);
                        int h = parseInt(tokens[3]);
                        int r = parseInt(tokens[4]);
                        int c = parseInt(tokens[5]);
                        long fps = parseLong(tokens[6]);
                        boolean loop = parseBoolean(tokens[7]);
                        states[i] = new State(i, source.array(x, y, w, h, r, c), 1000L / fps, loop);
                    }
                    break;

                    default:
                        throw exception("expected 4 or 8 arguments for state definition");
                }
            }
            else
            {
                throw exception("expected state definition");
            }
        }
        statesMap.put(key, states);
    }

    private void parseActors(BufferedReader buffer) throws IOException
    {
        if (!next(buffer))
        {
            throw exception("expected actors configuration");
        }

        int count = parseInt(tokens[0]);

        for (int i = 0; i < count; i++)
        {
            if (!next(buffer))
            {
                throw exception("expected actor definition");
            }

            try
            {
                Class c = Class.forName(tokens[0]);
                State[] s = statesMap.get(tokens[1]);
                float x = parseFloat(tokens[2]);
                float y = parseFloat(tokens[3]);
                float w = parseFloat(tokens[4]);
                float h = parseFloat(tokens[5]);
                Constructor ctor = c.getConstructor(ACTOR_ARGS);
                Actor a = (Actor) ctor.newInstance(s, x, y, w, h);

                actorsList.add(a);
            }
            catch (ClassNotFoundException |
                    NoSuchMethodException |
                    SecurityException |
                    InstantiationException |
                    IllegalAccessException |
                    IllegalArgumentException |
                    InvocationTargetException ex)
            {
                throw exception(ex.getMessage());
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

        if (!imageMap.containsKey(key))
        {
            try (InputStream stream = getClass().getResourceAsStream(key))
            {
                BufferedImage image = ImageIO.read(stream);
                imageMap.put(key, image);
            }
        }

        return imageMap.get(key);
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

    public List<Actor> getActors()
    {
        return actorsList;
    }

    public State[] getStates(String key)
    {
        return statesMap.get(key);
    }

    public TileLayer getTileLayer()
    {
        return tileLayer;
    }

    public Background getBackground()
    {
        return background;
    }

    public Stage getStage()
    {
        return stage;
    }
}
