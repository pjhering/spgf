package org.petehering.spgf;

import static java.awt.Color.WHITE;
import java.awt.Graphics;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import static java.lang.System.out;
import javax.swing.JFrame;
import spgf.core.Game;
import spgf.core.GamePanel;
import spgf.core.Keyboard;
import spgf.core.Loop;
import spgf.platform.IniParser;
import spgf.platform.TileLayer;
import spgf.platform.Viewport;

public class ViewportTest implements Game
{

    public static void main(String[] args) throws IOException
    {
        IniParser parser = new IniParser("/test01.ini");
        TileLayer layer = parser.getTileLayer();
        GamePanel panel = new GamePanel(640, 480);
        Viewport view = new Viewport(640, 480);
        ViewportTest game = new ViewportTest(panel, layer, view);
        Loop loop = new Loop(game, 60);

        JFrame frame = panel.openInWindow("Viewport Test");
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
        frame.requestFocus();
        panel.requestFocusInWindow();
    }

    private final GamePanel gp;
    private final TileLayer tl;
    private final Viewport vp;
    private float x;
    private float y;

    ViewportTest(GamePanel gp, TileLayer tl, Viewport vp)
    {
        this.gp = gp;
        this.tl = tl;
        this.vp = vp;
    }

    @Override
    public void start()
    {
        x = tl.width / 2f;
        y = tl.height / 2f;
    }

    @Override
    public void tick(long elapsedMilliseconds)
    {
        Keyboard kb = gp.getKeyboard();
        if (kb.isDown(VK_UP))
        {
            y -= elapsedMilliseconds * 0.0625f;
        }
        if (kb.isDown(VK_DOWN))
        {
            y += elapsedMilliseconds * 0.0625f;
        }
        if (kb.isDown(VK_LEFT))
        {
            x -= elapsedMilliseconds * 0.0625f;
        }
        if (kb.isDown(VK_RIGHT))
        {
            x += elapsedMilliseconds * 0.0625f;
        }
        
        kb.update();
        vp.update(x, y, tl);

        gp.clear();
        Graphics g = gp.getDrawGraphics();
        tl.draw(g, vp);
        g.setColor(WHITE);
        g.drawOval((int)x - 5, (int)y - 5, 10, 10);
        gp.present();
    }

    @Override
    public void stop()
    {
    }
}
