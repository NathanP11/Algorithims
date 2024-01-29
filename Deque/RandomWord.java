/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        // read in the data
        int i = 0;
        String a = "";


        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            i++;
            if (StdRandom.bernoulli((double) 1 / i)) {
                a = s;
            }

        }

        // String[] a = StdIn.readAllStrings();

        // shuffle the array
        // Knuth.shuffle(a);

        // print results.

        StdOut.println(a);

        // while( !StdIn.isEmpty() ) {		}
    }
}
