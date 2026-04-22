package deque;

import java.util.Iterator;

public class LinkedListDeque<T> {
    private Node sentinel;
    int size;

    /** regular Deque APIs*/
    public void addFirst(T item) {
        Node first = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = first;
        sentinel.next = first;
        // System.out.println("addFirst has done!");

        size++;
        // System.out.println("size: " + size);
    }

    public void addLast(T item) {
        Node last = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;

        size++;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        StringBuilder output = new StringBuilder();
        Node head = sentinel.next;
        while (head != sentinel) {
            output.append(head.item).append(" ");
            head = head.next;
        }

        System.out.println(output + "\n");
    }

    public T removeFirst() {
        Node first = sentinel.next;
        Node second = sentinel.next.next;
        sentinel.next = second;
        second.prev = sentinel;

        size--;

        return first.item;
    }

    public T removeLast() {
        Node last = sentinel.prev;
        Node penultimate = sentinel.prev.prev;
        sentinel.prev = penultimate;
        penultimate.next = sentinel;

        size--;

        return last.item;
    }

    public T get(int index) {
        Node checker = sentinel.next;
        for (int i = 0; i < index; i++) {
            if (checker == sentinel) return null;
            checker = checker.next;
        }
        return checker.item;
    }

    /** irregular Deque APIs*/


    /** additional APIs for LinkedListDeque*/


    public static void main(String[] strings) {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.printDeque();
        System.out.println("test the end line.");
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node();
    }

    public String toString() {
        StringBuilder output = new StringBuilder("[sentinel] <-> ");
        Node head = sentinel.next;
        while (head != sentinel) {
            output.append("[").append(head.item).append("] <-> ");
            head = head.next;
        }
        output.append("[sentinel]");
        return output.toString();
    }

    private class Node {
        T item;
        Node prev;
        Node next;

        public Node() {
            item = null;
            prev = this;
            next = this;
        }

        public Node(T item, Node prev, Node next){
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
