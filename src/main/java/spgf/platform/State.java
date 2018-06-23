package spgf.platform;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Long.MAX_VALUE;
import static java.lang.System.currentTimeMillis;
import static spgf.core.Utility.requireGreaterThan;
import static spgf.core.Utility.requireGreaterThanOrEqual;
import static spgf.core.Utility.requireNonNull;


public class State
{
    public final int id;
    private final BufferedImage[] frames;
    private final long millisecondsPerFrame;
    public final boolean loop;
    private boolean complete;
    private int index;
    private long startTime;
    private long elapsedTime;
    private int x;
    private int y;
    private int width;
    private int height;
    
    public State(int id, BufferedImage[] frames, long millisPerFrame, boolean loop)
    {
        this.id = requireGreaterThanOrEqual (0, id);
        this.frames = requireNonNull(frames);
        this.millisecondsPerFrame = requireGreaterThan (0L, millisPerFrame);
        this.loop = loop;
        this.width = frames[0].getWidth();
        this.height = frames[0].getHeight();
        reset();
    }
    
    public State(int id, BufferedImage frame)
    {
        this(id, new BufferedImage[]{frame}, MAX_VALUE, false);
    }
    
    public final void reset()
    {
        this.complete = false;
        this.index = 0;
        this.startTime = currentTimeMillis();
        this.elapsedTime = 0L;
    }
    
    public void update (long elapsed, float centerX, float centerY)
    {
        x = (int)(centerX - (width / 2f));
        y = (int)(centerY - (height / 2f));
        
        if (complete || frames.length == 1)
        {
            return;
        }
        
        elapsedTime += elapsed;
        
        LOOP:
        while (elapsedTime >= millisecondsPerFrame)
        {
            elapsedTime -= millisecondsPerFrame;
            index += 1;
            
            if (index >= frames.length)
            {
                if (loop)
                {
                    index = 0;
                }
                else
                {
                    index = frames.length - 1;
                    complete = true;
                    break LOOP;
                }
            }
        }
    }
    
    public void draw(Graphics g)
    {
        g.drawImage(frames[index], x, y, width, height, null);
    }
}
