package spgf.platform;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import spgf.core.Game;
import spgf.core.GamePanel;
import spgf.core.Loop;


public class StageTest implements Game
{
    public static void main(String[] args)
    {
        GamePanel panel = new GamePanel(640, 480);
        StageTest game = new StageTest(panel);
        Loop loop = new Loop(game, 60);
        JFrame frame = panel.openInWindow("Stage Test");
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowOpened(WindowEvent e)
            {
                frame.requestFocus();
                panel.requestFocusInWindow();
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
    
    private final GamePanel panel;
    private final Viewport view;
    private final Stage stage;
    
    private StageTest (GamePanel panel)
    {
        this.panel = panel;
        this.view = new Viewport(panel.getWidth(), panel.getHeight());
        try
        {
            IniParser parser = new IniParser("/test01.ini");
            this.stage = parser.getStage();
        }
        catch (IOException ex)
        {
            throw new RuntimeException (ex);
        }
    }

    @Override
    public void start()
    {
    }

    @Override
    public void tick(long elapsedMilliseonds)
    {
        stage.update(elapsedMilliseonds, view);
        
        Graphics g = panel.getDrawGraphics();
        stage.draw(g, view);
        panel.present();
    }

    @Override
    public void stop()
    {
    }
}
