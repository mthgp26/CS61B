package deque;

import com.google.j2objc.annotations.ObjectiveCName;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Iterable<T> {
    private int size;
    private int head;
    private int tail;
    private T[] array;
    private int capacity;

    @SuppressWarnings("unchecked")
    public ArrayDeque() {
        size = 0;
        head = -1;
        tail = 0;
        capacity = 8;
        array = (T[]) new Object[capacity];
    }

    public void addFirst(T item) {
        if (size > capacity * 3/4) resize(2 * capacity);
        // 一旦超过3/4倍capacity，则数组resize为2倍capacity
        array[(head + capacity) % capacity] = item;
        head--;
        size++;
    }

    public void addLast(T item) {
        if (size > capacity * 3/4) resize(2 * capacity);

        array[(tail + capacity) % capacity] = item;
        tail++;
        size++;
    }

    public T removeFirst() {
        if (size < capacity / 4) resize(capacity / 2);

        T item = array[(head + capacity) % capacity];
        head++;
        size--;

        return item;
    }

    public T removeLast() {
        if (size < capacity / 4) resize(capacity / 2);

        T item = array[(tail + capacity) % capacity];
        tail--;
        size--;

        return item;
    }

    public T get(int index) {
        return array[(head + index + capacity) % capacity];
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newArray[i] = array[(head + i + 1 + capacity) % capacity];
        }

        array = newArray;
        head = -1;
        tail = size;
        capacity = newCapacity;
    }

    public void printDeque() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < size; i++) {
            output.append(array[(head + i + capacity) % capacity]).append(" ");
        }

        System.out.println(output + "\n");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque)) return false;
        if (size != ((LinkedListDeque<?>) o).size()) return false;

        for (int i = 0; i < size; i++) {
            if (!get(i).equals(((LinkedListDeque<?>) o).get(i))) return false;
        }

        return true;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        int it; // 只当it是真正的索引（0索引）

        public ArrayDequeIterator() {it = head + 1;}

        @Override
        public boolean hasNext() {
            return it + 1 < tail;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T item = array[(it + capacity) % capacity];
            it++;
            return item;
        }
    }

}
