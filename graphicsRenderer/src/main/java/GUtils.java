import geometry.Vector3d;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Vector;

/**
 * @author Andrey Kokorev
 *         Created on 31.01.2015.
 */
public class GUtils {
    public final static int RED   = 0xFFFF0000;
    public final static int GREEN = 0xFF00FF00;
    public final static int BLUE  = 0xFF0000FF;
    public final static int BLACK = 0xFF000000;

    public static void drawTriangle(BufferedImage img, Vector3d p0, Vector3d p1, Vector3d p2, int color, double[][] zbuffer)
    {
        Vector3d[] pts = new Vector3d[]{p0, p1, p2};
        Arrays.sort(pts, (o1, o2) -> Double.compare(o1.getY(), o2.getY()));
        p0 = pts[0];
        p1 = pts[1];
        p2 = pts[2];

        double h = p2.getY() - p0.getY();
        if(h == 0)
        {
            return;
        }

        double h1 = p1.getY() - p0.getY();
        Vector3d p3 = p0.plus(p2.minus(p0).product(h1 / h));

        if(h1 != 0)
        {
            drawTrianglePart(img, p0, p1, p3, color, zbuffer);
        }

        if(h - h1 != 0)
        {
            drawTrianglePart(img, p1, p2, p3, color, zbuffer);
        }

    }

    private static void drawTrianglePart(BufferedImage img, Vector3d p0, Vector3d p1, Vector3d p2,
                                         int color, double[][] zbuffer)
    {
        int yl = (int) p0.getY();
        int yh = (int) p1.getY();
        boolean up = p2.getY() < p1.getY() - 1;
        int h = Math.abs(yh - yl);
        Vector3d v1 = p1.minus(p0);
        Vector3d v2 = (up)? p1.minus(p2) : p2.minus(p0);

        for (int y = yl; y <= yh; y ++)
        {
            double t = Math.abs(y - yl) * 1.0 / h;

            Vector3d l = p0.plus(v1.product(t));
            Vector3d r = (up)? p2.plus(v2.product(t)) : p0.plus(v2.product(t));
            int xl = (int)(l.getX());
            int xr = (int)(r.getX());

            if(xl > xr)
            {
                int z = xl; xl = xr; xr = z;
            }
            Vector3d lr = r.minus(l);
            for(int x = xl; x <= xr; x++)
            {
                Vector3d p = l.plus(lr.product((x - xl)/ (1.0 * (xr - xl))));
                if(p.getZ() > zbuffer[x][y])
                {
                    img.setRGB(x, y, color);
                    zbuffer[x][y] = p.getZ();
                }
            }
        }
    }

    public static void drawLine(BufferedImage img, Vector3d p0, Vector3d p1, int color)
    {
        int x0 = (int) Math.round(p0.getX());
        int y0 = (int) Math.round(p0.getY());
        int x1 = (int) Math.round(p1.getX());
        int y1 = (int) Math.round(p1.getY());

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
        for(int x = x0; x <= x1; x++)
        {
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
