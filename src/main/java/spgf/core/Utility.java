package spgf.core;

import java.awt.image.BufferedImage;
/**
 * The <code>Utility</code> class provides several useful utility functions.
 * @author tinman
 */
public final class Utility
{

    public static final String SPACES = "\\s+";

    private Utility()
    {
        //not used
    }

    public static BufferedImage subimage(BufferedImage source, int x, int y, int width, int height)
    {
        return source.getSubimage(x, y, width, height);
    }

    public static BufferedImage[] subimages(BufferedImage source, int rows, int columns)
    {
        return subimages(source, 0, 0, source.getWidth(), source.getHeight(), rows, columns);
    }

    public static BufferedImage[] subimages(BufferedImage source, int x, int y, int width, int height, int rows, int columns)
    {
        BufferedImage[] array = new BufferedImage[rows * columns];
        int subWidth = width / columns;
        int subHeight = height / rows;
        int i = 0;

        for (int r = 0; r < rows; r++)
        {
            int subY = y + (r * subHeight);

            for (int c = 0; c < columns; c++)
            {
                int subX = x + (c * subWidth);

                array[i] = source.getSubimage(subX, subY, subWidth, subHeight);
                i += 1;
            }
        }

        return array;
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @param max the high limit
     * @return the value or either the low or high limit
     */
    public static int clamp(int value, int min, int max)
    {
        if (value < min)
        {
            return min;
        }

        if (value > max)
        {
            return max;
        }

        return value;
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @param max the high limit
     * @return the value or either the low or high limit
     */
    public static long clamp(long value, long min, long max)
    {
        if (value < min)
        {
            return min;
        }

        if (value > max)
        {
            return max;
        }

        return value;
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @param max the high limit
     * @return the value or either the low or high limit
     */
    public static float clamp(float value, float min, float max)
    {
        if (value < min)
        {
            return min;
        }

        if (value > max)
        {
            return max;
        }

        return value;
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @param max the high limit
     * @return the value or throw a <code>RuntimeException</code>.
     */
    public static int requireInRange(int value, int min, int max)
    {
        if (min < value && value <= max)
        {
            return value;
        }

        throw new RuntimeException();
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @param max the high limit
     * @return the value or throw a <code>RuntimeException</code>.
     */
    public static long requireInRange(long value, long min, long max)
    {
        if (min < value && value <= max)
        {
            return value;
        }

        throw new RuntimeException();
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @param max the high limit
     * @return the value or throw a <code>RuntimeException</code>.
     */
    public static float requireInRange(float value, float min, float max)
    {
        if (min < value && value <= max)
        {
            return value;
        }

        throw new RuntimeException();
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @return the value or throw a <code>RuntimeException</code>.
     */
    public static int requireGreaterThanOrEqual(int min, int value)
    {
        if (min <= value)
        {
            return value;
        }

        throw new RuntimeException();
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @return the value or throw a <code>RuntimeException</code>.
     */
    public static long requireGreaterThanOrEqual(long min, long value)
    {
        if (min <= value)
        {
            return value;
        }

        throw new RuntimeException();
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @return the value or throw a <code>RuntimeException</code>.
     */
    public static float requireGreaterThanOrEqual(float min, float value)
    {
        if (min <= value)
        {
            return value;
        }

        throw new RuntimeException();
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @return the value or throw a <code>RuntimeException</code>.
     */
    public static int requireGreaterThan(int min, int value)
    {
        if (min < value)
        {
            return value;
        }

        throw new RuntimeException();
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @return the value or throw a <code>RuntimeException</code>.
     */
    public static long requireGreaterThan(long min, long value)
    {
        if (min < value)
        {
            return value;
        }

        throw new RuntimeException();
    }

    /**
     * @param value the target value
     * @param min the low limit
     * @return the value or throw a <code>RuntimeException</code>.
     */
    public static float requireGreaterThan(float min, float value)
    {
        if (min < value)
        {
            return value;
        }

        throw new RuntimeException();
    }

    /**
     * Requires that the array and all its elements are not null.
     * @param <T>
     * @param array
     * @return <code>array</code> or throw a <code>RuntimeException</code>.
     */
    public static <T> T[] requireNonNull(T[] array)
    {
        if (array != null)
        {
            int i = 0;

            for (T elem : array)
            {
                if (elem == null)
                {
                    throw new RuntimeException("element " + i + " is null");
                }

                i += 1;
            }

            return array;
        }
        else
        {
            throw new RuntimeException("null array");
        }
    }

    /**
     * Requires that the array and all its elements are not null.
     * @param <T>
     * @param array
     * @return <code>array</code> or throw a <code>RuntimeException</code>.
     */
    public static <T> T[][] requireNonNull(T[][] array)
    {
        if (array != null)
        {
            for (T[] elem : array)
            {
                requireNonNull(elem);
            }
        }

        throw new RuntimeException();
    }
}
