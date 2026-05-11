package deque;

import jh61b.junit.In;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void addFirstTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            ad.addFirst(i);
        }

        for (int j = 0; j < 10; j++) {
            ad.removeFirst();
        }
    }

    @Test
    public void iteratorTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            ad.addFirst(i);
        }

        int[] array = new int[20];
        int index = 0;

        Iterator<Integer> it = ad.iterator();
        while (it.hasNext()) {
            int nextOne = it.next();
            array[index] = nextOne;
            index++;
        }
    }

}
