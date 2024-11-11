
package edu.neu.coe.info6205.sort.par;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;


/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */


class ParSort {

    public static int cutoff = 1000;
    private static final int MAX_RECURSION_DEPTH = (int) (Math.log(Runtime.getRuntime().availableProcessors()) / Math.log(2));
    // Example depth limit for parallel sorting

    public static void sort(int[] array, int from, int to) {
        sort(array, from, to, 0);  // Start with depth 0
    }

    private static void sort(int[] array, int from, int to, int depth) {
        if (to - from < cutoff || depth > MAX_RECURSION_DEPTH) {
            Arrays.sort(array, from, to);  // Use system sort
        } else {
            int mid = from + (to - from) / 2;

            // Sort each half in parallel
            CompletableFuture<int[]> parsort1 = parsort(array, from, mid, depth + 1);
            CompletableFuture<int[]> parsort2 = parsort(array, mid, to, depth + 1);

            // Combine the two sorted halves
            CompletableFuture<int[]> parsort = parsort1.thenCombine(parsort2, (xs1, xs2) -> {
                int[] result = new int[xs1.length + xs2.length];
                int i = 0, j = 0;
                for (int k = 0; k < result.length; k++) {
                    if (i >= xs1.length) result[k] = xs2[j++];
                    else if (j >= xs2.length) result[k] = xs1[i++];
                    else result[k] = (xs1[i] <= xs2[j]) ? xs1[i++] : xs2[j++];
                }
                return result;
            });

            // Copy result back into original array
            parsort.whenComplete((result, throwable) -> {
                if (throwable == null) {
                    System.arraycopy(result, 0, array, from, result.length);
                } else {
                    throwable.printStackTrace();
                }
            }).join();  // Wait for completion
        }
    }

    // Helper method to create a CompletableFuture for parallel sorting a subarray
    private static CompletableFuture<int[]> parsort(int[] array, int from, int to, int depth) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int[] result = new int[to - from];
                    System.arraycopy(array, from, result, 0, result.length);
                    sort(result, 0, result.length, depth);  // Recursive sort with depth control
                    return result;
                }
        );
    }
}
