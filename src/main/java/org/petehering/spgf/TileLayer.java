package org.petehering.spgf;

import java.awt.Graphics2D;
import static java.util.Objects.requireNonNull;
import static org.petehering.spgf.Utility.requireGreaterThan;

/**
 * A TileLayer is a 2D array of Tiles.
 *
 * @author tinman
 */
public class TileLayer
{

    private final Tileset tileset;
    public final int rows;
    public final int columns;
    public final int width;
    public final int height;
    private final int[][] tiles;

    public TileLayer(Tileset tileset, int rows, int columns)
    {
        this.tileset = requireNonNull(tileset);
        this.rows = requireGreaterThan(0, rows);
        this.columns = requireGreaterThan(0, columns);
        this.tiles = new int[rows][columns];
        this.width = columns * tileset.tileWidth;
        this.height = rows * tileset.tileHeight;
    }
    
    public void draw (Graphics2D g, Viewport vp)
    {
        int cols = getColumn (vp.width);
        int rows = getRow (vp.height);
        int c1 = getColumn (vp.getX());
        int r1 = getRow (vp.getY());
        
        for (int r = 0; r < rows; r++)
        {
            int r2 = r + r1;
            
            if (r2 >= tiles.length)
            {
                break;
            }
            
            for (int c = 0; c < cols; c++)
            {
                int c2 = c + c1;
                
                if (c2 >= tiles[r2].length)
                {
                    break;
                }
            }
        }
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
    
    public boolean isBlocked (float x, float y)
    {
        int row = getRow (y);
        int col = getColumn (x);
        return isBlocked (row, col);
    }

    public boolean isBlocked(int row, int column)
    {
        int i = getId(row, columns);
        return tileset.isBlocked(i);
    }
    
    public boolean isHidden (float x, float y)
    {
        int row = getRow (y);
        int col = getColumn (x);
        return isHidden (row, col);
    }
    
    public boolean isHidden(int row, int column)
    {
        int i = getId(row, columns);
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
