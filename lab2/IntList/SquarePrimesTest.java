package IntList;

import static org.junit.Assert.*;

import jh61b.junit.In;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testIsPrimesAllprime() {
        IntList lst = IntList.of(2, 3, 5, 7, 11);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 9 -> 25 -> 49 -> 121", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testIsPrimesAllNonprime() {
        IntList lst = IntList.of(1, 4, 9, 25, 121);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("1 -> 4 -> 9 -> 25 -> 121", lst.toString());
        assertFalse(changed);
    }

    @Test
    public void testIsPrimesFirstIsprime() {
        IntList lst = IntList.of(2, 4, 4, 4, 4);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 4 -> 4 -> 4 -> 4", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testIsPrimesOne() {
        IntList lst = IntList.of(1, 1, 1, 1, 1);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("1 -> 1 -> 1 -> 1 -> 1", lst.toString());
        assertFalse(changed);
    }
}
