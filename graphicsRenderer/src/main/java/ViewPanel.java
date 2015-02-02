import model.Model;
import model.Model.Face;
import model.Model.Vertex;

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
        Map<Integer, Vertex> modelVertices = model.getVertices();

        for(Face f : model.getFaces())
        {
            int[] verticies = f.getVertices();
            for(int i = 0; i < 3; i++)
            {
                Vertex v0 = modelVertices.get(verticies[i]);
                Vertex v1 = modelVertices.get(verticies[(i + 1) % 3]);
                int x0 = (int) ((v0.getX() + 1) * size.getWidth() / 2);
                int y0 = (int) ((v0.getY() + 1) * size.getHeight() / 2);
                int x1 = (int) ((v1.getX() + 1) * size.getWidth() / 2);
                int y1 = (int) ((v1.getY() + 1) * size.getHeight() / 2);
                GUtils.drawLine(bufferedImage, x0, y0, x1, y1, GUtils.BLACK);
            }
        }

        this.repaint();
    }

    private void test(BufferedImage img)
    {
        GUtils.drawLine(img, 100, 100, 150, 150,GUtils.RED);
        GUtils.drawLine(img, 100, 100, 150,  50,GUtils.RED);
        GUtils.drawLine(img, 100, 100,  50,  50,GUtils.RED);
        GUtils.drawLine(img, 100, 100,  50, 150,GUtils.RED);
        GUtils.drawLine(img, 100, 100, 100, 200,GUtils.GREEN);
        GUtils.drawLine(img, 100, 100, 100,   0,GUtils.GREEN);
        GUtils.drawLine(img, 100, 100,   0, 100,GUtils.BLUE);
        GUtils.drawLine(img, 100, 100, 200, 100,GUtils.BLUE);

        int cx = 500, cy = 500, R = 50;
        double alpha = 0, da = Math.PI / 32;
        for(int i = 0; i < 64; i++)
        {
            GUtils.drawLine(img, cx, cy, (int) (cx + R * Math.sin(alpha)), (int) (cx + R * Math.cos(alpha)),GUtils.GREEN);
            alpha += da;
        }
    }
}
