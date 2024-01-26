/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> myLines;

    public BruteCollinearPoints(Point[] points) {
        validate(points);
        myLines = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length - 3; i++) {
            Point a = points[i];

            for (int j = i + 1; j < points.length - 2; j++) {
                Point b = points[j];
                for (int k = j + 1; k < points.length - 1; k++) {
                    Point c = points[k];
                    if (checkCollinearPoints(a, b, c)) {
                        for (int m = k + 1; m < points.length; m++) {
                            Point d = points[m];
                            if (checkCollinearPoints(a, b, c, d)) {
                                Point[] myPoints = new Point[4];
                                myPoints[0] = a;
                                myPoints[1] = b;
                                myPoints[2] = c;
                                myPoints[3] = d;
                                Arrays.sort(myPoints);
                                myLines.add(new LineSegment(myPoints[0], myPoints[3]));
                            }
                        }
                    }
                }
            }
        }

    }

    private boolean checkCollinearPoints(Point a, Point b, Point c, Point d) {
        if (a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(c) == a.slopeTo(d)) {
            return true;
        }
        return false;
    }

    private boolean checkCollinearPoints(Point a, Point b, Point c) {
        if (a.slopeTo(b) == a.slopeTo(c)) {
            return true;
        }
        return false;
    }

    public int numberOfSegments() {
        return myLines.size();
    }

    public LineSegment[] segments() {
        return myLines.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    private void validate(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("array is null");
        }
        if (points[0] == null) {
            throw new IllegalArgumentException("point[0] is null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point[" + (i) + " ] is null");
            }
        }

        for (int i = 0; i < points.length - 1; i++) {
            Point a = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point b = points[j];
                if (a.slopeTo(b) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("point " + i + " and " + j + " are repeat");
                }
            }
        }

    }

}
