/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if ((x - that.x) == 0) {
            if ((y - that.y) == 0) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;

        }
        if (this.y == that.y)
            return 0.0;
        return (double) (y - that.y) / (double) (x - that.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        int ydiff = (y - that.y);
        if (ydiff == 0) {
            return (x - that.x);
        }
        return ydiff;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (p1, p2) -> {
            double slopeWithP1 = slopeTo(p1);
            double slopeWithP2 = slopeTo(p2);
            if (slopeWithP1 > slopeWithP2) return 1;
            if (slopeWithP2 > slopeWithP1) return -1;
            return 0;
        };
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point[] points = new Point[10];
        points[1] = new Point(5, 5);
        points[0] = new Point(0, 0);
        points[2] = new Point(2, 2);
        points[3] = new Point(1, 9);
        points[4] = new Point(1, 5);
        points[5] = new Point(6, 3);
        points[6] = new Point(6, 3);
        points[7] = new Point(91, 156);
        points[8] = new Point(7, 1);
        points[9] = new Point(15, 12);

        for (int i = 1; i < points.length - 1; i++) {
            double slope = points[0].slopeTo(points[i]);
            StdOut.println("slope from (0,0) to " + points[i] + " = " + slope);
        }


        StdOut.println();
        StdOut.println("now sorting array and printing");
        StdOut.println();

        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            StdOut.println("#i : " + points[i]);
        }


    }
}
