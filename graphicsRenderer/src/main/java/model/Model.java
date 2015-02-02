package model;

import geometry.Point3D;

import java.util.List;
import java.util.Map;

/**
 * @author Andrey Kokorev
 *         Created on 31.01.2015.
 */
public class Model {
    private Map<Integer, Point3D> vertex;
    private List<Face> face;

    public Model(Map<Integer, Point3D> v, List<Face> f)
    {
        this.face = f;
        this.vertex = v;
    }

    public Map<Integer, Point3D> getVertices() {
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
}
