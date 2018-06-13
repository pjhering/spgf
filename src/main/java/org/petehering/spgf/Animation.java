package org.petehering.spgf;

import java.awt.image.BufferedImage;
import static java.lang.System.currentTimeMillis;
import static org.petehering.spgf.Utility.requireGreaterThan;
import static org.petehering.spgf.Utility.requireNonNull;

public class Animation
{
    private final BufferedImage[] frames;
    private int index;
    private final long millisecondsPerFrame;
    private long startTime;
    private long elapsedTime;
    private boolean loop;
    private boolean complete;
    
    public Animation (BufferedImage[] frames)
    {
        this (frames, 30L, false);
    }
    
    public Animation (BufferedImage[] frames, long framesPerSecond)
    {
        this (frames, framesPerSecond, false);
    }
    
    public Animation (BufferedImage[] frames, long framesPerSecond, boolean loop)
    {
        this.frames = requireNonNull (frames);
        this.index = 0;
        this.millisecondsPerFrame = 1000L / requireGreaterThan (0L, framesPerSecond);
        this.startTime = currentTimeMillis ();
        this.elapsedTime = 0L;
        this.loop = loop;
        this.complete = false;
    }
    
    public void reset ()
    {
        this.index = 0;
        this.startTime = currentTimeMillis ();
        this.elapsedTime = 0L;
        this.complete = false;
    }
    
    public void update (long elapsed)
    {
        if (!complete)
        {
            elapsedTime += elapsed;
            
            UPDATE:
            while (elapsedTime > millisecondsPerFrame)
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
                        break UPDATE;
                    }
                }
            }
        }
    }
    
    public BufferedImage getCurrentFrame ()
    {
        return frames[index];
    }

    boolean isLoop ()
    {
        return loop;
    }

    boolean isComplete ()
    {
        return complete;
    }
}
