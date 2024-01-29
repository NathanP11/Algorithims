/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int size, capacity;

    // construct an empty deque
    public RandomizedQueue() {
        capacity = 10;
        size = 0;
        s = (Item[]) new Object[capacity];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item handed to Deque");
        }
        s[size] = item;
        size++;
        if (size > capacity / 2) {
            capacity = capacity * 2;
            Item[] a = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                a[i] = s[i];
            }
            s = a;
        }
    }

    // remove and return the item from the front
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot removeFirst from empty list");
        }
        // choose a random item in the list, overwrite with the last item in the list, and decrement
        int r = StdRandom.uniformInt(size);
        Item item = s[r];
        s[r] = s[size - 1]; // pop the last item over
        s[size - 1] = null;
        size--;

        if (size < capacity / 4) {
            capacity = capacity / 2;
            Item[] a = (Item[]) new Object[capacity];
            for (int i = 0; i < size; i++) {
                a[i] = s[i];
            }
            s = a;
        }

        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot sample empty list");
        }
        int r = StdRandom.uniformInt(size);
        Item item = s[r];
        return item;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        int[] myIterator = new int[size];
        int tracker = 0;

        ListIterator() {
            for (int i = 0; i < size; i++) {
                myIterator[i] = i;
            }
            for (int i = 0; i < size; i++) {
                int r = StdRandom.uniformInt(i + 1);
                int a = myIterator[r];
                int b = myIterator[i];
                myIterator[i] = a;
                myIterator[r] = b;
            }
        }

        public boolean hasNext() {
            return tracker < size;
        }

        public void remove() { /* not supported */
            throw new UnsupportedOperationException("remove is an unsupported iterable function");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("iterator called next on empty iterable");
            }
            Item item = s[myIterator[tracker]];
            tracker++;
            return item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> d = new RandomizedQueue<Integer>();
        if (d.isEmpty()) {
            System.out.println("empty list ok 1");
        }
        d.enqueue(3);
        d.enqueue(2);
        d.enqueue(1);
        d.enqueue(4);
        System.out.println("Size of deque: " + d.size());
        for (int i = 0; i < 10; i++) {
            System.out.println("random sampling: " + d.sample());
        }
        for (int i : d) {
            System.out.println("random sample entire list: " + i);
        }
        System.out.println("Removing from list: " + d.dequeue());
        System.out.println("Removing from list: " + d.dequeue());

        for (int i = 0; i < 10; i++) {
            System.out.println("random sampling: " + d.sample());
        }

    }
}
