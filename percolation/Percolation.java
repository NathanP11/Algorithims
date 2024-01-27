

/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int gridSize, top, bot; // top and bot are virtual points
    private boolean[][] grid;
    private int numOpen;
    private WeightedQuickUnionUF myUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n less than 0");
        }
        numOpen = 0;
        gridSize = n;
        grid = new boolean[n][n];
        myUF = new WeightedQuickUnionUF((n * n) + 2);
        top = n * n;
        bot = n * n + 1;
    }

    public static void main(String[] args) {

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            numOpen++;
            int n = (row - 1) * gridSize + (col - 1);

            // look up
            if (row == 1) { // top row
                myUF.union(n, top);
            }
            else { // look at neighbor above
                if (isOpen(row - 1, col)) {
                    myUF.union(n, n - gridSize);
                }
            }

            // look down
            if (row == gridSize) { // top row
                myUF.union(n, bot);
            }
            else { // look at neighbor below
                if (isOpen(row + 1, col)) {
                    myUF.union(n, n + gridSize);
                }
            }

            // look left
            if (!(col == 1)) {
                if (isOpen(row, col - 1)) {
                    myUF.union(n, n - 1);
                }
            }

            // look right
            if (!(col == (gridSize))) {
                if (isOpen(row, col + 1)) {
                    myUF.union(n, n + 1);
                }
            }

        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int n = (row - 1) * gridSize + (col - 1);
        return myUF.find(top) == myUF.find(n);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {

        return myUF.find(top) == myUF.find(bot);
    }

    private boolean validate(int row, int col) {
        if ((row < 1) || (col < 1)) {
            throw new IllegalArgumentException("error in isOpen arg - row or col - less than 0");
        }
        if ((row > gridSize) || (col > gridSize)) {
            throw new IllegalArgumentException(
                    "error in isOpen arg - row or col - greater than gridsize");
        }
        return true;
    }


}
