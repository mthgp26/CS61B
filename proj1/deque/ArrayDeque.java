package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private int size;
    private int head;
    private int tail;
    private T[] array;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayDeque() {
        size = 0;
        head = 0;
        tail = 0;
        capacity = 8;
        array = (T[]) new Object[capacity];
    }

    @Override
    public void addFirst(T item) {
        if (size >= capacity * 3 / 4) {
            resize(2 * capacity);
        }
        head = (head - 1 + capacity) % capacity;
        array[head] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size >= capacity * 3 / 4) {
            resize(2 * capacity);
        }
        array[tail] = item;
        tail = (tail + 1) % capacity;
        size++;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size < capacity / 4 && capacity > 8) {
            resize(capacity / 2);
        }

        T item = array[head];
        head = (head + 1) % capacity;
        size--;

        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size < capacity / 4 && capacity > 8) {
            resize(capacity / 2);
        }

        tail = (tail - 1 + capacity) % capacity;
        T item = array[tail];
        size--;

        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return array[(head + index) % capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newArray[i] = array[(head + i) % capacity];
        }

        array = newArray;
        head = 0;
        tail = size;
        capacity = newCapacity;
    }

    @Override
    public void printDeque() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < size; i++) {
            output.append(array[(head + i) % capacity]).append(" ");
        }

        System.out.println(output);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<?> other = (Deque<?>) o;
        if (size != other.size()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            Object myItem = get(i);
            Object otherItem = other.get(i);
            if (myItem == null && otherItem == null) {
                continue;
            }
            if (myItem == null || !myItem.equals(otherItem)) {
                return false;
            }
        }

        return true;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int current;
        private int count;

        public ArrayDequeIterator() {
            current = head;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T item = array[current];
            current = (current + 1) % capacity;
            count++;
            return item;
        }
    }
}
