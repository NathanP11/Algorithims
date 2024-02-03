/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> pointSET;
    private final double smallPen = 0.01;

    public PointSET() {                           // construct an empty set of points
        pointSET = new SET<Point2D>();
    }

    public boolean isEmpty() {                  // is the set empty?
        return pointSET.isEmpty();
    }

    public int size() {                        // number of points in the set
        return pointSET.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validate(p);
        pointSET.add(p);
    }

    public boolean contains(Point2D p) {           // does the set contain point p?
        validate(p);
        return pointSET.contains(p);
    }

    public void draw() {                        // draw all points to standard draw
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        for (Point2D p : pointSET) {
            StdDraw.setPenRadius(smallPen);
            StdDraw.setPenColor(StdDraw.BLACK);
            p.draw();
        }
        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validate(rect);
        Stack<Point2D> ret = new Stack<Point2D>();
        for (Point2D p : pointSET) {
            if (rect.contains(p)) {
                ret.push(p);
            }
        }
        return ret;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validate(p);
        double minDist = Double.POSITIVE_INFINITY;
        Point2D ret = null;
        for (Point2D thisP : pointSET) {
            double dist = p.distanceSquaredTo(thisP);
            if (dist < minDist) {
                minDist = dist;
                ret = thisP;
            }

        }
        return ret;
    }

    private void validate(Object t) {
        if (t == null) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        // KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            // kdtree.insert(p);
            brute.insert(p);
        }
        brute.draw();
        Point2D p = new Point2D(0.75, 0.85);
        Point2D nearest = brute.nearest(p);

        System.out.println(
                "p = " + p + " ,  nearest = " + nearest + " ,  distante = " + p.distanceSquaredTo(
                        nearest));


        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);

        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.ORANGE);
        p.draw();

        StdDraw.setPenRadius(0.015);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        p.drawTo(nearest);
        StdDraw.show();
    }

}
