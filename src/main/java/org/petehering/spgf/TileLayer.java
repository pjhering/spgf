package org.petehering.spgf;

import static java.util.Objects.requireNonNull;
import static org.petehering.spgf.Utility.requireGreaterThan;

/**
 * A TileLayer is a 2D array of Tiles.
 *
 * @author tinman
 */
public class TileLayer
{

    private Tileset tileset;
    public final int rows;
    public final int columns;
    public final int width;
    public final int height;
    private int[][] tiles;

    public TileLayer(Tileset tileset, int rows, int columns)
    {
        this.tileset = requireNonNull(tileset);
        this.rows = requireGreaterThan(0, rows);
        this.columns = requireGreaterThan(0, columns);
        this.tiles = new int[rows][columns];
        this.width = columns * tileset.tileWidth;
        this.height = rows * tileset.tileHeight;
    }

    public int getTileWidth()
    {
        return tileset.tileWidth;
    }

    public int getTileHeight()
    {
        return tileset.tileHeight;
    }

    public int getColumn(float x)
    {
        return (int) (x / tileset.tileWidth);
    }

    public int getRow(float y)
    {
        return (int) (y / tileset.tileHeight);
    }

    public boolean isBlocked(int row, int column)
    {
        int i = tiles[row][columns];
        return tileset.isBlocked(i);
    }

    public boolean isHidden(int row, int column)
    {
        int i = tiles[row][columns];
        return tileset.isHidden(i);
    }

    public int getId(int row, int column)
    {
        return tiles[row][column];
    }

    void setId(int row, int column, int id)
    {
        tiles[row][column] = id;
    }
}
