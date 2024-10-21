package edu.neu.coe.info6205.pq;

import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class BenchmarkPriorityQueue {

    public static void main(String[] args) {
        int[] sizes = {4095, 5095, 6095, 7095};
        int n = 16000;
        int removeCount = 4000;

        Comparator<Integer> comparator = Comparator.naturalOrder();
        Random r = new Random();

        for (int m : sizes) {
            List<Integer> randomElements = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                randomElements.add(r.nextInt(100000));
            }

            System.out.println("Running benchmarks for M = " + m);
            runBenchmark(randomElements, removeCount, comparator, m);
        }
    }

    public static void runBenchmark(List<Integer> randomElements, int removeCount, Comparator<Integer> comparator, int m) {

        PriorityQueue<Integer> binaryHeap = new PriorityQueue<>(m, true, comparator, false);
        Benchmark_Timer<Integer[]> benchmarkAddBinary = new Benchmark_Timer<>(
                "Add to Basic Binary Heap",
                null,
                (array) -> {
                    for (Integer element : randomElements) {
                        binaryHeap.give(element);
                    }
                },
                null
        );
        Benchmark_Timer<Integer[]> benchmarkRemoveBinary = new Benchmark_Timer<>(
                "Remove from Basic Binary Heap",
                null,
                (array) -> {
                    try {
                        for (int i = 0; i < removeCount && !binaryHeap.isEmpty(); i++) {
                            binaryHeap.take();
                        }
                    } catch (PQException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                },
                null
        );

        PriorityQueue<Integer> binaryHeapFloyd = new PriorityQueue<>(m, true, comparator, true);
        Benchmark_Timer<Integer[]> benchmarkAddFloyd = new Benchmark_Timer<>(
                "Add to Binary Heap with Floyd's Trick",
                null,
                (array) -> {
                    for (Integer element : randomElements) {
                        binaryHeapFloyd.give(element);
                    }
                },
                null
        );
        Benchmark_Timer<Integer[]> benchmarkRemoveFloyd = new Benchmark_Timer<>(
                "Remove from Binary Heap with Floyd's Trick",
                null,
                (array) -> {
                    try {
                        for (int i = 0; i < removeCount && !binaryHeapFloyd.isEmpty(); i++) {
                            binaryHeapFloyd.take();
                        }
                    } catch (PQException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                },
                null
        );

        FourAryHeap<Integer> fourAryHeap = new FourAryHeap<>(m, true, comparator, false);
        Benchmark_Timer<Integer[]> benchmarkAddFourAry = new Benchmark_Timer<>(
                "Add to 4-ary Heap",
                null,
                (array) -> {
                    for (Integer element : randomElements) {
                        fourAryHeap.give(element);
                    }
                },
                null
        );
        Benchmark_Timer<Integer[]> benchmarkRemoveFourAry = new Benchmark_Timer<>(
                "Remove from 4-ary Heap",
                null,
                (array) -> {
                    try {
                        for (int i = 0; i < removeCount && !fourAryHeap.isEmpty(); i++) {
                            fourAryHeap.take();
                        }
                    } catch (PQException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                },
                null
        );

        FourAryHeap<Integer> fourAryHeapFloyd = new FourAryHeap<>(m, true, comparator, true);
        Benchmark_Timer<Integer[]> benchmarkAddFourAryFloyd = new Benchmark_Timer<>(
                "Add to 4-ary Heap with Floyd's Trick",
                null,
                (array) -> {
                    for (Integer element : randomElements) {
                        fourAryHeapFloyd.give(element);
                    }
                },
                null
        );
        Benchmark_Timer<Integer[]> benchmarkRemoveFourAryFloyd = new Benchmark_Timer<>(
                "Remove from 4-ary Heap with Floyd's Trick",
                null,
                (array) -> {
                    try {
                        for (int i = 0; i < removeCount && !fourAryHeapFloyd.isEmpty(); i++) {
                            fourAryHeapFloyd.take();
                        }
                    } catch (PQException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                },
                null
        );

        System.out.println("Benchmarking with M=" + m + " and input size: " + randomElements.size());

        double addBinaryHeapTime = benchmarkAddBinary.run(new Integer[]{}, 5);
        System.out.println("Addition to Basic Binary Heap time for M=" + m + ": " + addBinaryHeapTime + " seconds");
        double removeBinaryHeapTime = benchmarkRemoveBinary.run(new Integer[]{}, 5);
        System.out.println("Removal from Basic Binary Heap time for M=" + m + ": " + removeBinaryHeapTime + " seconds");

        double addFloydHeapTime = benchmarkAddFloyd.run(new Integer[]{}, 5);
        System.out.println("Addition to Binary Heap with Floyd's Trick time for M=" + m + ": " + addFloydHeapTime + " seconds");
        double removeFloydHeapTime = benchmarkRemoveFloyd.run(new Integer[]{}, 5);
        System.out.println("Removal from Binary Heap with Floyd's Trick time for M=" + m + ": " + removeFloydHeapTime + " seconds");

        double addFourAryHeapTime = benchmarkAddFourAry.run(new Integer[]{}, 5);
        System.out.println("Addition to 4-ary Heap time for M=" + m + ": " + addFourAryHeapTime + " seconds");
        double removeFourAryHeapTime = benchmarkRemoveFourAry.run(new Integer[]{}, 5);
        System.out.println("Removal from 4-ary Heap time for M=" + m + ": " + removeFourAryHeapTime + " seconds");

        double addFourAryFloydHeapTime = benchmarkAddFourAryFloyd.run(new Integer[]{}, 5);
        System.out.println("Addition to 4-ary Heap with Floyd's Trick time for M=" + m + ": " + addFourAryFloydHeapTime + " seconds");
        double removeFourAryFloydHeapTime = benchmarkRemoveFourAryFloyd.run(new Integer[]{}, 5);
        System.out.println("Removal from 4-ary Heap with Floyd's Trick time for M=" + m + ": " + removeFourAryFloydHeapTime + " seconds");

    }
}
