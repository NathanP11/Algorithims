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
import java.util.Collections;
import java.util.HashMap;

public class FastCollinearPoints {
    private ArrayList<LineSegment> myLines;
    // private ArrayList<Integer> myMin, myMax;

    public FastCollinearPoints(Point[] points) {
        validate(points);
        myLines = new ArrayList<LineSegment>();
        // myMin = new ArrayList<Integer>();
        // myMax = new ArrayList<Integer>();
        // double[] mySlopes = new double[points.length];


        // for (int j = i + 1; j < points.length; j++) {                mySlopes[j] = points[i].slopeTo(points[j]);            }

        for (int a = 0; a < points.length - 3; a++) {

            Point[] myPoints = Arrays.copyOf(points, points.length);


            Arrays.sort(myPoints, myPoints[a].slopeOrder());

            int count = 0;
            ArrayList<Point> lineBuilder = new ArrayList<Point>();
            lineBuilder.add(myPoints[0]);

            /*// uncomment to print out each sorted array and slope relative to origin
            StdOut.println();
            StdOut.println("sorting with origin = " + myPoints[a]);
            for (int i = 0; i < myPoints.length - 1; i++) {
                StdOut.println(myPoints[0] + " -> " + myPoints[i + 1] + " : slope = "
                                       + myPoints[0].slopeTo(myPoints[i + 1]));
            }
            */

            for (int i = 0; i < myPoints.length - 1; i++) {

                if (myPoints[0].slopeTo(myPoints[i]) == myPoints[0].slopeTo(myPoints[i + 1])) {
                    if (!lineBuilder.contains(myPoints[i])) {
                        lineBuilder.add(myPoints[i]);
                    }
                    lineBuilder.add(myPoints[i + 1]);
                }
                else if (lineBuilder.size() > 3) {
                    addLine(lineBuilder);
                    lineBuilder.clear();
                    lineBuilder.add(myPoints[0]);

                }
                else {
                    lineBuilder.clear();
                    lineBuilder.add(myPoints[0]);
                }

            }
            if (lineBuilder.size() > 3) {
                addLine(lineBuilder);
                lineBuilder.clear();
                lineBuilder.add(myPoints[0]);
            }
        }


    }

    private void addLine(ArrayList<Point> lineBuilder) {
        Collections.sort(lineBuilder);
        Point min = lineBuilder.get(0);
        Point max = lineBuilder.get(lineBuilder.size() - 1);
        LineSegment myLine = new LineSegment(min, max);
        LineSegment myLine2 = new LineSegment(max, min);

        for (LineSegment line : myLines) {
            if (line.toString().equals(myLine.toString()) || line.toString()
                                                                 .equals(myLine2.toString())) {
                return;
            }
        }
        // myMax.add(max);
        // myMin.add(min);


        myLines.add(myLine);
        // StdOut.println("added line to List: " + myLine);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);

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

        for (int i = 0; i < points.length - 1; i++) {
            Point a = points[i];
            if (points[i + 1] == null) {
                throw new IllegalArgumentException("point[" + (i + 1) + " ] is null");
            }
            for (int j = i + 1; j < points.length; j++) {
                Point b = points[j];
                if (a.slopeTo(b) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("point " + i + " and " + j + " are repeat");
                }
            }
        }

    }

    public ArrayList<String> getFollows(String key) {
        ArrayList<String> ret = new ArrayList<String>();


        return ret;
    }

    private void buildMap (){
        myMap = new HashMap<String, ArrayList<String>>();

        for( int i = 0; i < myText.length()-N ; i++){
            try{
                String key = myText.substring(i,i+N);
                String next = myText.substring(i+N+1,i+N+2);
                if( myMap.containsKey(key) ){
                    myMap.get(key).add(next);
                }
                else{ //initialize key
                    ArrayList<String> values = new ArrayList<String>();
                    values.add(next);
                    myMap.put(key,values);
                }
            }
            catch( ) {}

            String[] myW = new String[myWords.length];

            for( int i=1; i< myW.length ; i++){
                myW[i-1]=myWords[i];
            }
            myW[myW.length-1] = myWords[myW.length-1];

        }

    }
}
