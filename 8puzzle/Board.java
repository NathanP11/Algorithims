/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class Board {
    private static final int empty = 0;
    private final int[][] myBoard;
    private final int n;
    private int manhattan;
    private int ham;
    private boolean isGoal;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // myBoard = tiles;

        myBoard = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            int[] aMatrix = tiles[i];
            int aLength = aMatrix.length;
            myBoard[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myBoard[i], 0, aLength);
        }

        n = myBoard.length;
        manhattan = manhattanCache();
        ham = hamCache();
        isGoal = isGoalCache();
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" " + myBoard[i][j] + " ");
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place

    public int hamming() {
        return ham;
    }

    private int hamCache() {
        int count = -1; // consider empty space as always wrong
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (myBoard[i][j] != (i * n + j + 1)) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    private int manhattanCache() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                count = count + distanceToGoal(i, j);
            }
        }

        return count;
    }

    private int distanceToGoal(int i, int j) {
        int a = myBoard[i][j];
        if (a == 0) return 0;
        int desiredRow = (a - 1) / n;
        int desiredCol = (a - 1) % n;
        // same as:   int desiredCol = n-desiredRow;
        int differenceRow = Math.abs(desiredRow - i);
        int differenceCol = Math.abs(desiredCol - j);
        /*  debug helper
        System.out.println(
                "distance to goal (manhattan) [" + "i" + "][" + j + "]" + " = " + differenceRow
                        + " + " + differenceCol);
        */
        return differenceRow + differenceCol;
    }

    // is this board the goal board?

    public boolean isGoal() {
        return isGoal;
    }

    private boolean isGoalCache() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (myBoard[i][j] != empty) if (myBoard[i][j] != (i * n + j + 1)) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board comp = (Board) y;
        if (comp.dimension() != dimension()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.myBoard[i][j] != comp.myBoard[i][j]) return false;
            }
        }
        return true;
    }

    private Board exch(int i1, int j1, int i2, int j2) {
        int[][] myExch = new int[n][n];
        // copy 2d array
        for (int i = 0; i < myBoard.length; i++) {
            int[] aMatrix = myBoard[i];
            int aLength = aMatrix.length;
            myExch[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myExch[i], 0, aLength);
        }
        // swap

        int temp = myExch[i1][j1];
        myExch[i1][j1] = myExch[i2][j2];
        myExch[i2][j2] = temp;

        return new Board(myExch);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();
        int iIndex = 0;
        int jIndex = 0;
        // find empty tile
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.myBoard[i][j] == empty) {
                    iIndex = i;
                    jIndex = j;
                }
            }
        }

        if (iIndex > 0) q.enqueue(exch(iIndex, jIndex, iIndex - 1, jIndex));       // look left
        if (iIndex < (n - 1)) q.enqueue(exch(iIndex, jIndex, iIndex + 1, jIndex));   // look right
        if (jIndex > 0) q.enqueue(exch(iIndex, jIndex, iIndex, jIndex - 1));       // look up
        if (jIndex < (n - 1)) q.enqueue(exch(iIndex, jIndex, iIndex, jIndex + 1));   // look down

        return q;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] myTwin = new int[n][n];
        for (int i = 0; i < myBoard.length; i++) {
            int[] aMatrix = myBoard[i];
            int aLength = aMatrix.length;
            myTwin[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myTwin[i], 0, aLength);
        }
        // swap
        for (int i = 0; i < myTwin.length; i++)
            if (!((myTwin[i][0] == 0) || (myTwin[i][1] == 0))) {
                int temp = myTwin[i][0];
                myTwin[i][0] = myTwin[i][1];
                myTwin[i][1] = temp;
            }
        return new Board(myTwin);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // int[][] array1 = new int[3][3];
        int[][] array1 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board board1 = new Board(array1);
        System.out.println(board1);
        System.out.println("dimension =  " + board1.dimension());
        System.out.println("ham =  " + board1.hamming());
        System.out.println("manhattan =  " + board1.manhattan());
        System.out.println("isGoal =  " + board1.isGoal());

        int[][] array2 = array1;
        Board board2 = new Board(array2);
        System.out.println("equals shoudl be true =  " + board1.equals(board2));

        int[][] array3 = { { 3, 2, 1 }, { 6, 5, 4 }, { 8, 7, 0 } };
        Board board3 = new Board(array3);
        System.out.println("equals shoudl be false =  " + board1.equals(board3));
        System.out.println("equals shoudl be false =  " + board1.equals(null));
        // System.out.println("equals shoudl be false =  " + board1.equals(array3));

        Board board4 = board1.twin();
        System.out.println("twin board1 print: ");
        System.out.println(board4);
        System.out.println();
        System.out.println("printing neightbors for board1: ");
        for (Board iterableBoards : board1.neighbors()) {
            System.out.println(iterableBoards);
        }

        System.out.println();
        System.out.println("testing board2 : ");
        System.out.println(board2);
        System.out.println("dimension =  " + board2.dimension());
        System.out.println("ham =  " + board2.hamming());
        System.out.println("manhattan =  " + board2.manhattan());
        System.out.println("isGoal =  " + board2.isGoal());

        System.out.println();
        System.out.println("testing board3 : ");
        System.out.println(board3);
        System.out.println("dimension =  " + board3.dimension());
        System.out.println("ham =  " + board3.hamming());
        System.out.println("manhattan =  " + board3.manhattan());
        System.out.println("isGoal =  " + board3.isGoal());

        System.out.println();
        System.out.println("testing board4 : ");
        System.out.println(board4);
        System.out.println("dimension =  " + board4.dimension());
        System.out.println("ham =  " + board4.hamming());
        System.out.println("manhattan =  " + board4.manhattan());
        System.out.println("isGoal =  " + board4.isGoal());

        System.out.println("testing board5 : ");
        int[][] array5 = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
        Board board5 = new Board(array5);
        System.out.println(board5);
        System.out.println("dimension =  " + board5.dimension());
        System.out.println("ham =  " + board5.hamming());
        System.out.println("manhattan =  " + board5.manhattan());
        System.out.println("isGoal =  " + board5.isGoal());

        System.out.println("testing board6 : ");
        int[][] array6 = { { 4, 2, 3, 1 }, { 8, 6, 7, 5 }, { 12, 10, 11, 9 }, { 0, 14, 15, 13 } };
        Board board6 = new Board(array6);
        System.out.println(board6);
        System.out.println("dimension =  " + board6.dimension());
        System.out.println("ham =  " + board6.hamming());
        System.out.println("manhattan =  " + board6.manhattan());
        System.out.println("isGoal =  " + board6.isGoal());

    }


}
