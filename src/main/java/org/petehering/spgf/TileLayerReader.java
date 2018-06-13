package org.petehering.spgf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import static org.petehering.spgf.Utility.SPACES;
import static org.petehering.spgf.Utility.requireGreaterThan;

public class TileLayerReader
{

    private Tileset tileset;
    private List<int[]> lines;
    private int viewWidth;
    private int viewHeight;

    public TileLayerReader(String path, int viewWidth, int viewHeight)
    {
        this.viewWidth = requireGreaterThan(0, viewWidth);
        this.viewHeight = requireGreaterThan(0, viewHeight);

        try (InputStream stream = getClass().getResourceAsStream(path))
        {
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader buffer = new BufferedReader(reader);
            String[] tokens = buffer.readLine().trim().split(SPACES);

            String tilesetFile = tokens[0];
            int rows = parseInt(tokens[1]);
            int columns = parseInt(tokens[2]);

            tileset = new Tileset(tilesetFile, rows, columns);

            lines = new ArrayList<>();
            String string;

            while ((string = buffer.readLine()) != null)
            {
                tokens = string.trim().split(SPACES);
                int[] array = new int[tokens.length];

                for (int i = 0; i < tokens.length; i++)
                {
                    array[i] = parseInt(tokens[i]);
                }

                lines.add(array);
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public Tileset getTileset()
    {
        return tileset;
    }

    public TileLayer getTileLayer(Function<Integer, Boolean> blocked)
    {
        Tile[][] tiles = new Tile[lines.size()][];

        for (int row = 0; row < lines.size(); row++)
        {
            int y = row * tileset.tileHeight;

            int[] ints = lines.get(row);
            tiles[row] = new Tile[ints.length];

            for (int j = 0; j < ints.length; j++)
            {
                if (0 > ints[j] || ints[j] >= tileset.length())
                {
                    // no subimage; leave null
                    continue;
                }
                else
                {
                    int x = j * tileset.tileWidth;
                    boolean b = blocked.apply(ints[j]);

                    tiles[row][j] = new Tile(
                            ints[j],
                            tileset.getSubImage(ints[j]),
                            x, y,
                            tileset.tileWidth, tileset.tileHeight,
                            b);
                }
            }
        }

        return new TileLayer(tiles, tileset.tileWidth, tileset.tileHeight, viewWidth, viewHeight);
    }
}
