import java.awt.image.BufferedImage;

/**
 * @author Andrey Kokorev
 *         Created on 31.01.2015.
 */
public class GUtils {
    public final static int RED   = 0xFFFF0000;
    public final static int GREEN = 0xFF00FF00;
    public final static int BLUE  = 0xFF0000FF;
    public final static int BLACK = 0xFF000000;

    public static void drawLine(BufferedImage img, int x0, int y0, int x1, int y1, int color)
    {
        int dx = Math.abs(x0 - x1);
        int dy = Math.abs(y0 - y1);
        boolean transposed = false;
        if(dx < dy)
        {
            int t = x0; x0 = y0; y0 = t;
            t = x1; x1 = y1; y1 = t;
            transposed = true;
        }
        if(x0 > x1) {
            int t = x0; x0 = x1; x1 = t;
            t = y0; y0 = y1; y1 = t;
        }

        int error = 0;
        int de = (transposed)? dx : dy;
        int dd = (transposed)? dy : dx;
        int y = y0;
        int dey = (y1 > y0)? 1 : -1;
        for(int x = x0; x < x1; x++)
        {
//            System.out.printf("%d %d\n", x, y);
            if(!transposed)
            {
                img.setRGB(x, y, color);
            }
            else
            {
                img.setRGB(y, x, color);
            }
            error += de;
            if(2 * error > dd)
            {
                y += dey;
                error -= dd;
            }
        }
    }
}
