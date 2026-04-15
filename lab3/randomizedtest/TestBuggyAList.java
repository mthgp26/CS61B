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
        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
            } else if (operationNumber == 2 && L.size() > 0) {
                System.out.println("getLast(): " + L.getLast());
            } else if (operationNumber == 3 && L.size() > 0) {
                System.out.println("removeLast():" + L.removeLast());
            }
        }
    }
}
