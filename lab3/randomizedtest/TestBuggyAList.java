package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE

    /** stupid implement*/
    @Test
    public void testThreeAddThreeRemove(){
        BuggyAList<Integer> testBuggyAList = new BuggyAList<Integer>();
        AListNoResizing<Integer> testAListNoResizing = new AListNoResizing<Integer>();

        for (int i = 4; i < 7; i++){
            testBuggyAList.addLast(i);
            testAListNoResizing.addLast(i);
        }

        boolean[] res = new boolean[]{false, false, false};
        for (int j = 0; j < 3; j++){
            if (testAListNoResizing.removeLast().equals(testBuggyAList.removeLast())) res[j] = true;
        }

        boolean[] expected = new boolean[]{true, true, true};
        assertArrayEquals(res, expected);
    }

    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                B.addLast(randVal);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int Bsize = B.size();
                System.out.println("size: " + size);
                System.out.println("Bsize:" + B.size());
                assertEquals(size, Bsize);
            } else if (operationNumber == 2 && L.size() > 0) {
                int expected = L.getLast();
                int output = B.getLast();
                System.out.println("L.getLast(): " + expected);
                System.out.println("B.getLast(): " + output);
                assertEquals(expected, output);
            } else if (operationNumber == 3 && L.size() > 0) {
                int expected = L.removeLast();
                int output = B.removeLast();
                System.out.println("L.removeLast():" + expected);
                System.out.println("B.removeLast():" + output);
                assertEquals(expected, output);
            }
        }
    }
}
