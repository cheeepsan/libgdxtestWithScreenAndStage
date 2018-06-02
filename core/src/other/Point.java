package other;

public class Point extends java.awt.Point implements Comparable<java.awt.Point> {

    /*
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    public Point(int x, int y) {
        super(x, y);
    }

    public Point(Point point) {
        super(point);
    }
    @Override
    public int compareTo(java.awt.Point other) {
        return Integer.compare(this.x, other.x);

    }
}
