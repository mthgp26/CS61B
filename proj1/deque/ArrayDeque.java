package deque;

import com.google.j2objc.annotations.ObjectiveCName;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Iterable<T> {
    private int size;
    private int head;
    private int tail;
    private T[] array;

    @SuppressWarnings("unchecked")
    public ArrayDeque() {
        size = 0;
        head = -1;
        tail = 0;
        array = (T[]) new Object[8];
    }

    public void addFirst(T item) {
        head++;
        tail++;
        array[head] = item;
        size++;
    }

    public void addLast() {

    }

    public T removeFirst() {
        return null;
    }

    public T removeLast() {
        return null;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void resize() {

    }

    public void printDeque() {

    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        @Override
        public boolean hasNext(){
            return false;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            return null;
        }
    }


}
