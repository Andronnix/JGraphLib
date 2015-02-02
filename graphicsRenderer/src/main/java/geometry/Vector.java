package geometry;

/**
 * @author Andrey Kokorev
 *         Created on 02.02.2015.
 */
public class Vector
{
    private double x, y, z;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(Point3D from, Point3D to)
    {
        this.x = to.getX() - from.getX();
        this.y = to.getY() - from.getY();
        this.z = to.getZ() - from.getZ();
    }

    public Vector product(Vector v)
    {
        return new Vector(
                y * v.getZ() - z * v.getY(),
                z * v.getX() - x * v.getZ(),
                x * v.getY() - y * v.getX()
        );
    }

    public double dot(Vector v)
    {
        return x * v.getX() + y * v.getY() + z * v.getZ();
    }

    public void normalize()
    {
        double l = Math.sqrt(x*x + y*y + z*z);
        if(l == 0) return;
        x /= l;
        y /= l;
        z /= l;
    }
}
