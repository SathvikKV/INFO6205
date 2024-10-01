package edu.neu.coe.info6205.threesum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each subspace corresponds to a fixed value for the middle index of the three values.
 * Each subspace is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each subspace can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * The array provided in the constructor MUST be ordered.
 */
public class ThreeSumQuadraticWithCalipers implements ThreeSum {
    /**
     * Construct ints ThreeSumQuadratic on ints.
     *
     * @param ints a sorted array.
     */
    public ThreeSumQuadraticWithCalipers(int[] ints) {
        this.a = ints;
        length = ints.length;
    }

    /**
     * Get an array or Triple containing all of those triples for which sum is zero.
     *
     * @return a Triple[].
     */
    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        Collections.sort(triples); // ???
        for (int i = 0; i < length - 2; i++)
            triples.addAll(calipers(a, i, Triple::sum));
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    /**
     * Get a set of candidate Triples such that the first index is the given value i.
     * Any candidate triple is added to the result if it yields zero when passed into function.
     *
     * @param a        a sorted array of ints. This method is concerned only with the partition of a starting with index i+1.
     * @param i        the index of the first element of resulting triples.
     * @param function a function which takes a triple and returns a value which will be compared with zero.
     * @return a List of Triples.
     */
    public static List<Triple> calipers(int[] a, int i, Function<Triple, Integer> function) {
        List<Triple> triples = new ArrayList<>();


        // Initializing the pointers j and k
        int j = i + 1; //j starts after i
        int k = a.length - 1; // k starts from the end of the list

        // Iterating while j is less than k
        while(j<k){


            //Creating a Triple object with the current values of i,j,k
            Triple triple = new Triple(a[i],a[j],a[k]);

            //Applying function
            int sum = function.apply(triple);

            if(sum == 0){
                triples.add(triple); //Add valid triple to the list triples
                j++;
                k--;
            } else if(sum<0){
                j++; //If the sum is less than 0, increment j to increase the sum
            }
            else{
                k--; //If the sum is greater than 0, decrement k to decrease the sum
            }


        }

         return triples;  //return triples

    }

    private final int[] a;
    private final int length;
}