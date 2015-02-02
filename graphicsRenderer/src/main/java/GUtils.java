import geometry.Point2D;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author Andrey Kokorev
 *         Created on 31.01.2015.
 */
public class GUtils {
    public final static int RED   = 0xFFFF0000;
    public final static int GREEN = 0xFF00FF00;
    public final static int BLUE  = 0xFF0000FF;
    public final static int BLACK = 0xFF000000;

    public static void drawTriangle(BufferedImage img, Point2D p0, Point2D p1, Point2D p2, int color)
    {
        Point2D[] pts = new Point2D[]{p0, p1, p2};
        Arrays.sort(pts, (o1, o2) -> Integer.compare(o1.getY(), o2.getY()));
        p0 = pts[0];
        p1 = pts[1];
        p2 = pts[2];

        int h  = p2.getY() - p0.getY();
        if(h == 0)
        {
            drawLine(img, p0, p1, color);
            drawLine(img, p0, p2, color);
            drawLine(img, p1, p2, color);
            return;
        }

        int h1 = p1.getY() - p0.getY();
        int h2 = p2.getY() - p1.getY();

        int w   = p2.getX() - p1.getX();
        int w1  = p1.getX() - p0.getX();
        int w2  = p2.getX() - p0.getX();
        int w02 = w2 * h1 / h;

        int x0 = p0.getX();
        int x1 = p1.getX();

        if(h1 == 0)
        {
            drawLine(img, p0, p1, color);
        } else
        {
            for (int y = p0.getY(), yh = p1.getY(); y <= yh; y++)
            {
                double t = (y - p0.getY()) * 1.0 / h1;
                drawLine(
                        img,
                        new Point2D((int) (x0 + w1 * t), y),
                        new Point2D((int) (x0 + w02 * t), y),
                        color
                );
            }
        }

        if(h2 == 0)
        {
            drawLine(img, p2, p1, color);
        } else
        {
            for (int y = p1.getY(), yh = p2.getY(); y <= yh; y++)
            {
                double t = (y - p1.getY()) * 1.0 / h2;
                drawLine(
                        img,
                        new Point2D((int) (x1 + w * t), y),
                        new Point2D((int) (x0 + w02 + (w2 - w02) * t), y),
                        color
                );
            }
        }

    }

    public static void drawLine(BufferedImage img, Point2D p0, Point2D p1, int color)
    {
        int x0 = p0.getX();
        int y0 = p0.getY();
        int x1 = p1.getX();
        int y1 = p1.getY();

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
