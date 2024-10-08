package edu.neu.coe.info6205.sort.elementary;
import edu.neu.coe.info6205.sort.elementary.InsertionSortBasic;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class InsertionSortBenchmark {

    public static void main(String[] args) {
        int[] sizes = {1000, 2000, 4000, 8000, 16000};

        Random r = new Random();

        InsertionSortBasic<Integer> insertionSort = InsertionSortBasic.create();

        for (int size : sizes) {
            System.out.println("Array Size: " + size);

            Supplier<Integer[]> randomArraySupplier = () -> randomArray(size, r);
            double randTime = runBenchmark("Random Array", insertionSort, randomArraySupplier);
            System.out.println("Time taken to sort Random Array: " + randTime + " ms");

            Supplier<Integer[]> orderedArraySupplier = () -> orderedArray(size);
            double ordTime = runBenchmark("Ordered Array", insertionSort, orderedArraySupplier);
            System.out.println("Time taken to sort Ordered Array: " + ordTime + " ms");

            Supplier<Integer[]> partiallyOrderedArraySupplier = () -> partiallyOrderedArray(size, r);
            double pordTime = runBenchmark("Partially Ordered Array", insertionSort, partiallyOrderedArraySupplier);
            System.out.println("Time taken to sort Partially Ordered Array: " + pordTime + " ms");

            Supplier<Integer[]> reverseOrderedArraySupplier = () -> reverseOrderedArray(size);
            double revordTime = runBenchmark("Reverse Ordered Array", insertionSort, reverseOrderedArraySupplier);
            System.out.println("Time taken to sort Reverse Ordered Array: " + revordTime + " ms");

            System.out.println();
        }
    }

    private static double runBenchmark(String description, InsertionSortBasic<Integer> insertionSort, Supplier<Integer[]> arraySupplier) {
        Benchmark_Timer<Integer[]> benchmark = new Benchmark_Timer<>(description, insertionSort::sort);
        return benchmark.runFromSupplier(arraySupplier, 10);
    }

    private static Integer[] randomArray(int n, Random random) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(n);
        }
        return array;
    }

    private static Integer[] orderedArray(int n) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;  // Generates ordered array
        }
        return array;
    }

    private static Integer[] partiallyOrderedArray(int n, Random random) {
        Integer[] array = orderedArray(n);

        // Shuffle half of the array
        for (int i = 0; i < n / 2; i++) {
            int index1 = random.nextInt(n);
            int index2 = random.nextInt(n);
            int temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
        return array;
    }

    private static Integer[] reverseOrderedArray(int n) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = n - i;  // Reverse order
        }
        return array;
    }
}


