

/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.Knuth;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private static final double CONFIDENCE_95 = 1.96;
    private int gridSize, numTrials;
    private double[] data;


    public PercolationStats(int n, int trials) {
        gridSize = n; // gridSize
        numTrials = trials; // num trials

        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("argument less than 1");
        }

        data = new double[numTrials];

        Integer[] deck = new Integer[gridSize * gridSize];
        for (int i = 0; i < deck.length; i++) {
            deck[i] = i;
        }


        for (int i = 0; i < numTrials; i++) {
            Percolation p = new Percolation(gridSize);
            int j = 0;
            Knuth.shuffle(deck);
            while (!p.percolates()) {
                int row = (deck[j] / gridSize) + 1;
                int col = (deck[j] % gridSize) + 1;
                p.open(row, col);
                j++;
            }
            data[i] = (double) j / (gridSize * gridSize);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(data);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(data);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (stddev() * CONFIDENCE_95) / Math.sqrt(numTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (stddev() * CONFIDENCE_95) / Math.sqrt(numTrials);
    }

    // test client (see below)
    public static void main(String[] args) {

        PercolationStats myPerc = new PercolationStats(Integer.parseInt(args[0]),
                                                       Integer.parseInt(args[1]));
        StdOut.println("mean                    = " + myPerc.mean());
        StdOut.println("stddev                  = " + myPerc.stddev());
        StdOut.println(
                "95% confidence interval = [" + myPerc.confidenceLo() + ", " + myPerc.confidenceHi()
                        + "]");
    }

}
