package org.petehering.spgf;

import java.awt.Graphics;
import static java.lang.Math.max;
import static java.util.Objects.requireNonNull;

/**
 * A TileMap is a 2D array of uniformly sized tiles.
 *
 * @author tinman
 */
public class TileLayer
{

    private final Tile[][] tiles;
    private final int rows;
    private final int columns;
    private final int layerWidth;
    private final int layerHeight;
    private final int tileWidth;
    private final int tileHeight;
    private final int viewWidth;
    private final int viewHeight;
    private final int numberOfRowsToDraw;
    private final int numberOfColumnsToDraw;

    public TileLayer(Tile[][] tiles, int tileWidth, int tileHeight, int viewWidth, int viewHeight)
    {
        this.tiles = requireNonNull(tiles);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.numberOfColumnsToDraw = 2 + viewWidth / tileWidth;
        this.numberOfRowsToDraw = 2 + viewHeight / tileHeight;
        this.layerHeight = tiles.length * tileHeight;
        int maxColumnCount = 0;
        for (int i = 0; i < tiles.length; i++)
        {
            if (tiles[i].length > maxColumnCount)
            {
                maxColumnCount = tiles[i].length;
            }
        }
        this.layerWidth = maxColumnCount * tileWidth;

        this.rows = tiles.length;
        this.columns = maxColumnCount;
    }

    public void draw(Graphics g, int xOffset, int yOffset)
    {
        int startingRow = max(0, yOffset / tileHeight - 1);
        int startingColumn = max(0, xOffset / tileWidth - 1);

        for (int row = startingRow; row < numberOfRowsToDraw; row++)
        {
            if (row < tiles.length)
            {
                for (int col = startingColumn; col < numberOfColumnsToDraw; col++)
                {
                    if (col < tiles[row].length)
                    {
                        if (tiles[row][col] != null)
                        {
                            tiles[row][col].draw(g, xOffset, yOffset);
                        }
                    }
                }
            }
        }
    }

    public Tile getTile(float x, float y)
    {
        int row = (int) (y / tileHeight);
        int col = (int) (x / tileWidth);

        if (0 <= row && row < tiles.length)
        {
            if (0 <= col && col <= tiles[row].length)
            {
                return tiles[row][col];
            }
        }

        return null;
    }

    public boolean isBlocked(float x, float y)
    {
        Tile tile = getTile(x, y);

        return tile != null && tile.isBlocked();
    }

    public int getRows()
    {
        return rows;
    }

    public int getColumns()
    {
        return columns;
    }

    public int getMapWidth()
    {
        return layerWidth;
    }

    public int getMapHeight()
    {
        return layerHeight;
    }

    public int getTileWidth()
    {
        return tileWidth;
    }

    public int getTileHeight()
    {
        return tileHeight;
    }

    public int getViewWidth()
    {
        return viewWidth;
    }

    public int getViewHeight()
    {
        return viewHeight;
    }

    public int getLayerWidth()
    {
        return layerWidth;
    }

    public int getLayerHeight()
    {
        return layerHeight;
    }
}
