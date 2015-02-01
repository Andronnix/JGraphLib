package model;

import java.util.List;
import java.util.Map;

/**
 * @author Andrey Kokorev
 *         Created on 31.01.2015.
 */
public class Model {
    private Map<Integer, Vertex> vertex;
    private List<Face> face;

    public Model(Map<Integer, Vertex> v, List<Face> f)
    {
        this.face = f;
        this.vertex = v;
    }

    public Map<Integer, Vertex> getVertices() {
        return vertex;
    }

    public List<Face> getFaces() {
        return face;
    }

    public static class Face
    {
        private int[] vertex;
        public Face(int v1, int v2, int v3)
        {
            vertex = new int[3];
            vertex[0] = v1;
            vertex[1] = v2;
            vertex[2] = v3;
        }

        public int[] getVertices() {
            return vertex;
        }
    }

    public static class Vertex
    {
        private double x;
        private double y;
        private double z;

        public Vertex(double x, double y, double z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }
    }
}
