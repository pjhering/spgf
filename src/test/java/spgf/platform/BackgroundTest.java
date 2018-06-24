package spgf.platform;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import spgf.core.Game;
import spgf.core.GamePanel;
import spgf.core.Loop;


public class BackgroundTest implements Game
{
    public static void main(String[] args) throws IOException
    {
        BufferedImage image;
        try (InputStream stream = BackgroundTest.class.getResourceAsStream("/test03.png"))
        {
            image = ImageIO.read(stream);
        }
        
        GamePanel gp = new GamePanel (640, 480);
        Background bg = new Background (image);
        Viewport vp = new Viewport (640, 480);
        Game game = new BackgroundTest (gp, bg, vp);
        Loop loop = new Loop (game, 60);
        
        JFrame frame = gp.openInWindow("Background Test");
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                loop.start();
            }
            
            @Override
            public void windowClosing(WindowEvent e)
            {
                loop.quit();
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
    
    private final GamePanel gp;
    private final Background bg;
    private final Viewport vp;
    
    private BackgroundTest (GamePanel gp, Background bg, Viewport vp)
    {
        this.bg = bg;
        this.gp = gp;
        this.vp = vp;
    }

    @Override
    public void start()
    {
    }

    @Override
    public void tick(long elapsedMilliseonds)
    {
        Graphics g = gp.getDrawGraphics();
        bg.draw(g, vp);
        gp.present();
    }

    @Override
    public void stop()
    {
    }
}
