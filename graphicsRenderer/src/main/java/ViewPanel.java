import geometry.Point2D;
import geometry.Point3D;
import geometry.Vector;
import model.Model;
import model.Model.Face;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * @author Andrey Kokorev
 *         Created on 25.01.2015.
 */
public class ViewPanel extends JPanel
{
    private BufferedImage bufferedImage;
    private Dimension size;
    private Vector lightDirection = new Vector(0, 0, -1);

    @Override
    public void paint(Graphics g)
    {
        Dimension size = this.getSize();
        g.setColor(Color.ORANGE);
        g.clearRect(0, 0, size.width, size.height);
        if(bufferedImage != null)
        {
            //flip vertically
            g.drawImage(bufferedImage, 0, 0, size.width, size.height, 0, size.height,size.width, 0, null);
        }
    }

    public void drawModel(Model model)
    {
        if(bufferedImage == null)
        {
            size = this.getSize();

            bufferedImage = new BufferedImage(size.width * 2, size.height * 2, BufferedImage.TYPE_INT_ARGB);
        }

//        test(bufferedImage);

        Map<Integer, Point3D> modelVertices = model.getVertices();

        for(Face f : model.getFaces())
        {
            int[] vertices = f.getVertices();
            Point3D v0 = modelVertices.get(vertices[0]);
            Point3D v1 = modelVertices.get(vertices[1]);
            Point3D v2 = modelVertices.get(vertices[2]);

            Point2D p0 = new Point2D(
                    (int) ((v0.getX() + 1) * size.getWidth() / 2),
                    (int) ((v0.getY() + 1) * size.getHeight() / 2)
            );
            Point2D p1 = new Point2D(
                    (int) ((v1.getX() + 1) * size.getWidth() / 2),
                    (int) ((v1.getY() + 1) * size.getHeight() / 2)
            );
            Point2D p2 = new Point2D(
                    (int) ((v2.getX() + 1) * size.getWidth() / 2),
                    (int) ((v2.getY() + 1) * size.getHeight() / 2)
            );


            Vector n = new Vector(v0, v2).product(new Vector(v0, v1));
            n.normalize();
            int intensity = (int)(n.dot(lightDirection) * 255);
            
            if(intensity > 0)
            {
                int color = 0xFF000000 | (intensity << 16) | (intensity << 8) | (intensity);
                GUtils.drawTriangle(bufferedImage, p0, p1, p2, color);
            }

        }

        this.repaint();
    }

    private void test(BufferedImage img)
    {
        Point2D c = new Point2D(100, 100);
        Point2D e = new Point2D(0, 100);
        Point2D w = new Point2D(200, 100);
        Point2D n = new Point2D(100, 200);
        Point2D s = new Point2D(100, 0);
        Point2D ne = new Point2D(50, 150);
        Point2D nw = new Point2D(150, 150);
        Point2D se = new Point2D(50, 50);
        Point2D sw = new Point2D(150, 50);

        GUtils.drawLine(img, c, ne, GUtils.RED);
        GUtils.drawLine(img, c, nw, GUtils.RED);
        GUtils.drawLine(img, c, se, GUtils.RED);
        GUtils.drawLine(img, c, sw, GUtils.RED);
        GUtils.drawLine(img, c, e, GUtils.GREEN);
        GUtils.drawLine(img, c, w, GUtils.GREEN);
        GUtils.drawLine(img, c, n, GUtils.BLUE);
        GUtils.drawLine(img, c, s, GUtils.BLUE);

        int cx = 500, cy = 500, R = 50;
        Point2D c1 = new Point2D(cx, cy);
        double alpha = 0, da = Math.PI / 32;
        for(int i = 0; i < 64; i++)
        {
            Point2D p = new Point2D((int) (cx + R * Math.sin(alpha)), (int) (cx + R * Math.cos(alpha)));
            GUtils.drawLine(img, c1, p, GUtils.GREEN);
            alpha += da;
        }


        GUtils.drawLine(img, new Point2D(0, 400), new Point2D(10, 400), GUtils.BLACK);

        Point2D t0 = new Point2D(300, 120);
        Point2D t1 = new Point2D(350, 60);
        Point2D t2 = new Point2D(280, 280);

        GUtils.drawTriangle(img, t0, t1, t2, GUtils.RED);

        Point2D q0 = new Point2D(189, 477);
        Point2D q1 = new Point2D(609, 413);
        Point2D q2 = new Point2D(483, 137);

        GUtils.drawTriangle(img, q0, q1, q2, GUtils.GREEN);

        Point2D z0 = new Point2D(0, 150);
        Point2D z1 = new Point2D(0, 0);
        Point2D z2 = new Point2D(150, 0);

        GUtils.drawTriangle(img, z2, z0, z2, GUtils.BLACK);
    }
}
