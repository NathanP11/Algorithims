/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item handed to Deque");
        }
        Node myNode = new Node(item);
        if (head == null) {
            head = myNode;
            tail = myNode;
        }
        else {
            head.next = myNode;
            myNode.prev = head;
            head = myNode;
        }

        size++;
        return;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item handed to Deque");
        }
        Node myNode = new Node(item);
        if (head == null) {
            head = myNode;
            tail = myNode;
        }
        else {
            tail.prev = myNode;
            myNode.next = tail;
            tail = myNode;
        }
        size++;
        return;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("cannot removeFirst when head is null");
        }
        Item item = head.data;
        head.prev = head;
        head.next = null;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("cannot removeLast when tail is null");
        }
        Item item = tail.data;
        tail.next = tail;
        head.prev = null;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() { /* not supported */
            throw new UnsupportedOperationException("remove is an unsupported iterable function");
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("iterator called next on empty iterable");
            }
            Item item = current.data;
            current = current.prev;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        if (d.isEmpty()) {
            System.out.println("empty list ok 1");
        }
        d.addFirst(3);
        d.addFirst(2);
        d.addFirst(1);
        d.addLast(4);
        System.out.println("Size of deque: " + d.size());
        for (int i : d) {
            System.out.println(i);
        }
        System.out.println("Removing from front of deque: " + d.removeFirst());
        System.out.println("Removing from back of deque: " + d.removeLast());
    }

    private class Node {

        Item data;
        Node next;
        Node prev;

        private Node(Item a) {
            data = a;
            next = null;
            prev = null;
        }

    }

}
