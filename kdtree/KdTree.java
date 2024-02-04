/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private static final double VERYSMALL = 0.003;
    private Node root;
    private int size;


    public KdTree() {                           // construct an empty set of points
    }

    public boolean isEmpty() {                  // is the set empty?
        return root == null;
    }

    public int size() {                        // number of points in the set
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validate(p);
        if (contains(p)) return;
        size++;
        if (isEmpty()) {
            root = new Node(p, false);
            return;
        }
        Node curr = root;
        while (true) {
            if (curr.even) { // look down
                if (p.y() < curr.val.y()) {
                    if (curr.left == null) {
                        curr.left = new Node(p, false);
                        return;
                    }
                    curr = curr.left;
                }
                else { // look up
                    if (curr.right == null) {
                        curr.right = new Node(p, false);
                        return;
                    }
                    curr = curr.right;
                }
            }
            else { // look left
                if (p.x() < curr.val.x()) {
                    if (curr.left == null) {
                        curr.left = new Node(p, true);
                        return;
                    }
                    curr = curr.left;
                }
                else { // look right
                    if (curr.right == null) {
                        curr.right = new Node(p, true);
                        return;
                    }
                    curr = curr.right;
                }
            }
        }
    }

    private Node get(Point2D curr) {
        validate(curr);
        if (root == null) return null;
        return get(curr, root);
    }

    private Node get(Point2D p, Node curr) {

        // System.out.println("get is searching Node : " + curr.val + " , " + curr.even);

        if (curr.even) {
            if ((p.y() == curr.val.y()) && (p.x() == curr.val.x())) return curr;
            if (p.y() >= curr.val.y()) { // look up
                if (curr.right == null) return null;
                return get(p, curr.right);
            }
            else { // if (p.y() <= curr.val.y()) { // look down
                // if ((p.y() == curr.val.y()) && (p.x() == curr.val.x())) return curr;
                if (curr.left == null) return null;
                return get(p, curr.left);
            }
        }
        else {
            if ((p.y() == curr.val.y()) && (p.x() == curr.val.x())) return curr;
            if (p.x() >= curr.val.x()) { // look right
                if (curr.right == null) return null;
                return get(p, curr.right);
            }
            else { // if (p.x() <= curr.val.x()) { // look left
                if (curr.left == null) return null;
                return get(p, curr.left);
            }
        }
    }

    public boolean contains(Point2D p) {           // does the set contain point p?
        validate(p);
        return get(p) != null;
    }

    public void draw() {                        // draw all points to standard draw
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);

        // p.drawTo(g);
        if (root == null) {
            return;
        }
        // StdDraw.line(root.val.x(), 0, root.val.x(), 1); // draw the root vertically
        RectHV rect = new RectHV(0, 0, 1, 1);
        draw(root, rect);

        StdDraw.show();
    }

    private void draw(Node curr, RectHV s) {
        // StdDraw.line(this.x, this.y, that.x, that.y);
        RectHV left;
        RectHV right;


        if (curr.even) { // left = down, right = up
            left = new RectHV(s.xmin(), s.ymin(), s.xmax(), curr.val.y());
            right = new RectHV(s.xmin(), curr.val.y(), s.xmax(), s.ymax());
            StdDraw.setPenRadius(VERYSMALL);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(s.xmin(), curr.val.y(), s.xmax(), curr.val.y());
        }
        else {
            left = new RectHV(s.xmin(), s.ymin(), curr.val.x(), s.ymax());
            right = new RectHV(curr.val.x(), s.ymin(), s.xmax(), s.ymax());
            StdDraw.setPenRadius(VERYSMALL);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(curr.val.x(), s.ymin(), curr.val.x(), s.ymax());
        }

        if (curr.left != null) draw(curr.left, left);
        if (curr.right != null) draw(curr.right, right);

        StdDraw.setPenRadius(VERYSMALL * 3);
        StdDraw.setPenColor(StdDraw.BLACK);
        curr.val.draw();

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validate(rect);
        ArrayList<Point2D> ret = new ArrayList<Point2D>();
        if (isEmpty()) return ret;
        if (rect.contains(root.val)) ret.add(root.val);
        RectHV left = new RectHV(0, 0, root.val.x(), 1);
        RectHV right = new RectHV(root.val.x(), 0, 1, 1);

        if (root.left != null && rect.intersects(left)) {
            ret.addAll(range(rect, root.left, left));
        }
        if (root.right != null && rect.intersects(right)) {
            ret.addAll(range(rect, root.right, right));
        }

        return ret;
    }

    private ArrayList<Point2D> range(RectHV rect, Node curr, RectHV s) { // s = search boundary
        ArrayList<Point2D> ret = new ArrayList<Point2D>();
        if (rect.contains(curr.val)) ret.add(curr.val);

        RectHV left;
        RectHV right;

        // RectHV(double xmin, double ymin, double xmax, double ymax)
        if (curr.even) { // left = down, right = up
            left = new RectHV(s.xmin(), s.ymin(), s.xmax(), curr.val.y());
            right = new RectHV(s.xmin(), curr.val.y(), s.xmax(), s.ymax());
        }
        else {
            left = new RectHV(s.xmin(), s.ymin(), curr.val.x(), s.ymax());
            right = new RectHV(curr.val.x(), s.ymin(), s.xmax(), s.ymax());
        }

        if (curr.left != null && rect.intersects(left)) {
            ret.addAll(range(rect, curr.left, left));
        }
        if (curr.right != null && rect.intersects(right)) {
            ret.addAll(range(rect, curr.right, right));
        }

        return ret;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validate(p);
        if (isEmpty()) return null;
        return nearest(p, root, root.val, new RectHV(0, 0, 1, 1));
    }

    private Point2D nearest(Point2D p, Node curr, Point2D champ, RectHV s) { // n = nearest
        // System.out.println("visiting a node : " + curr.val);
        double dist = p.distanceSquaredTo(curr.val);
        double minDist;
        double axisDist;

        RectHV left;
        RectHV right;

        // update champion
        if (dist < p.distanceSquaredTo(champ)) champ = curr.val;


        if (curr.even) { // left = down, right = up
            left = new RectHV(s.xmin(), s.ymin(), s.xmax(), curr.val.y());
            right = new RectHV(s.xmin(), curr.val.y(), s.xmax(), s.ymax());
            axisDist = p.distanceSquaredTo(new Point2D(p.x(), curr.val.y()));
        }
        else {
            left = new RectHV(s.xmin(), s.ymin(), curr.val.x(), s.ymax());
            right = new RectHV(curr.val.x(), s.ymin(), s.xmax(), s.ymax());
            axisDist = p.distanceSquaredTo(new Point2D(curr.val.x(), p.y()));
        }

        // search branches for closer point
        if (left.contains(p)) {
            if (curr.left != null) champ = nearest(p, curr.left, champ, left);
            minDist = p.distanceSquaredTo(champ);
            if (minDist > axisDist) { // look right if there could be a point closer
                if (curr.right != null) champ = nearest(p, curr.right, champ, right);
            }
        }
        else {
            if (curr.right != null) champ = nearest(p, curr.right, champ, right);
            minDist = p.distanceSquaredTo(champ);
            if (minDist > axisDist) { // look left if there could be a point closer
                if (curr.left != null) champ = nearest(p, curr.left, champ, left);
            }
        }

        return champ;
    }

    private void validate(Object t) {
        if (t == null) throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        nearestUnitTest(args);

        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        System.out.println();
        System.out.println("searching...");
        Point2D p = new Point2D(1.0, 1.0);
        System.out.println("kdTree contains " + p + " = " + kdtree.contains(p));


    }

    private static void nearestUnitTest(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        // PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            // brute.insert(p);
        }
        kdtree.draw();
        Point2D p = new Point2D(0.81, 0.30);
        Point2D nearest = kdtree.nearest(p);

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


    private class Node {
        private Point2D val;         // associated data
        private Node left, right;  // links to left and right subtrees
        private boolean even;     // color of parent link

        public Node(Point2D val, boolean e) {
            this.val = val;
            this.even = e;
        }
    }

} // end kdTree class
