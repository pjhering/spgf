package spgf.platform;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static spgf.core.Utility.requireGreaterThan;

/**
 * A TileLayer is a 2D array of Tiles.
 *
 * @author tinman
 */
public class TileLayer
{

    public final int rows;
    public final int columns;
    public final int tileWidth;
    public final int tileHeight;
    public final int width;
    public final int height;
    private final Tile[][] tiles;

    public TileLayer(int rows, int columns, int tileWidth, int tileHeight)
    {
        this.rows = requireGreaterThan(0, rows);
        this.columns = requireGreaterThan(0, columns);
        this.tileWidth = requireGreaterThan(0, tileWidth);
        this.tileHeight = requireGreaterThan(0, tileHeight);
        this.width = columns * tileWidth;
        this.height = rows * tileHeight;
        this.tiles = new Tile[rows][columns];
    }

    public Tile[] get(Actor a)
    {
        return new Tile[]
        {
            get(a.x, a.y),
            get(a.x + a.width, a.y),
            get(a.x, a.y + a.height),
            get(a.x + a.width, a.y + a.height)
        };
    }

    public Tile get(float x, float y)
    {
        int row = (int) (y / tileHeight);
        int col = (int) (x / tileWidth);
        return get(row, col);
    }

    public Tile get(int row, int column)
    {
        if (0 <= row && row < tiles.length)
        {
            if (0 <= column && column < tiles[row].length)
            {
                return tiles[row][column];
            }
        }

        return null;
    }

    public void set(int row, int column, BufferedImage image, int id, boolean blocked, boolean hidden)
    {
        if (0 <= row && row < tiles.length)
        {
            if (0 <= column && column < tiles[row].length)
            {
                int x = column * tileHeight;
                int y = row * tileWidth;
                tiles[row][column] = new Tile(image, id, x, y, tileWidth, tileHeight, blocked, hidden);
            }
        }
    }

    public void set(int row, int column, BufferedImage image, int id)
    {
        set(row, column, image, id, true, false);
    }
    
    private int column (float x)
    {
        return (int)(x / tileWidth);
    }
    
    private int row (float y)
    {
        return (int)(y / tileHeight);
    }

    public void draw(Graphics g, Viewport vp)
    {
        OUTER:
        for (int r = row (vp.getY()); r < rows; r++)
        {
            INNER:
            for (int c = column (vp.getX()); c < columns; c++)
            {
                if (tiles[r][c] != null && !tiles[r][c].hidden)
                {
                    if (vp.contains(tiles[r][c]))
                    {
                        tiles[r][c].draw(g, vp.getOffset());
                    }
                    else
                    {
                        break INNER;
                    }
                }
            }
        }
    }
}
