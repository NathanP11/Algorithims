/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public final class Solver {


    private int moves;
    private SearchNode solution;
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("null board passed into solver");

        MinPQ<SearchNode> myPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> myTwinPQ = new MinPQ<SearchNode>();

        // A* search  finds a solution with initial or twin as the root
        SearchNode currentNode = new SearchNode(initial);
        myPQ.insert(currentNode);
        myPQ.insert(new SearchNode(currentNode.data.twin()));

        while (!myPQ.min().data.isGoal()) {
            currentNode = myPQ.delMin();
            for (Board b : currentNode.data.neighbors()) {
                if (currentNode.parent == null || !b.equals(currentNode.parent.data)) {
                    myPQ.insert(new SearchNode(currentNode, b));
                }
            }
        }
        currentNode = myPQ.min();
        solution = currentNode;
        // find parent and see if was the twin node, if twin node was solved, it means original board is unsolvable
        while (!(currentNode.parent == null)) {
            currentNode = currentNode.parent;
        }
        if (!currentNode.data.equals(initial)) {
            moves = -1;
            solution = null;
            isSolvable = false;
        }
        else {
            moves = solution.moves;
            isSolvable = true;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution == null) return null;
        Stack<Board> mySolution = new Stack<Board>();
        SearchNode node = solution;
        while (node.parent != null) {
            mySolution.push(node.data);
            node = node.parent;
        }
        mySolution.push(node.data);
        return mySolution;
    }

    public static void main(String[] args) {
        int[][] array1 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board board1 = new Board(array1);
        System.out.println(board1);
        System.out.println();
        // SearchNode node = new SearchNode(board1);
        // Board board2 = node.data.twin();
        // System.out.println(board2);
    }

    // used for building the game tree
    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode parent;
        private int moves;
        private Board data;

        public SearchNode(Board b) {
            parent = null;
            moves = 0;
            data = b;
        }

        public SearchNode(SearchNode p, Board b) {
            parent = p;
            moves = p.moves + 1;
            data = b;
        }

        // priority function
        public int compareTo(SearchNode that) {
            int priorityDiff = (this.data.manhattan() + this.moves) - (that.data.manhattan()
                    + that.moves);
            return priorityDiff == 0 ? this.data.manhattan() - that.data.manhattan() : priorityDiff;

        }
    }

}
