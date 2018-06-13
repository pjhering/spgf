
import java.awt.Point;
import static java.lang.System.out;


public class Temp
{
    public static void main(String[] args)
    {
        Point p = new Point ();
        out.println (p);
        
        p.setLocation(5, 6);
        out.println (p);
        
        p.setLocation(1.49, 1.5);
        out.println (p);
    }
}
