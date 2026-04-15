package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE

        // initialize Ns, opCounts, times
        int[] numNs = new int[]{1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        AList<Integer> Ns = new AList<Integer>();
        AList<Integer> opCounts = new AList<Integer>();
        AList<Double> times = new AList<Double>();

        // for each N do the operation
        for (int i = 0; i < numNs.length; i++) {
            int N = numNs[i];
            Ns.addLast(N);
            opCounts.addLast(N);

            SLList<Integer> Test = new SLList<Integer>();

            // fill in the Test SLList
            for (int j = 0; j < N; j++){
                Test.addLast(j);
            }

            // recording time for getLast()
            Stopwatch sw = new Stopwatch();
            for (int k = 0; k < 10000; k++){
                Test.getLast();
            }
            double timeInMicroSec = sw.elapsedTime();
            times.addLast(timeInMicroSec);
        }

        // print the table
        printTimingTable(Ns, times, opCounts);
    }

}
