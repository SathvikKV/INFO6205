package edu.neu.coe.info6205.threesum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.neu.coe.info6205.util.Stopwatch;

/**
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each sub-space corresponds to a fixed value for the middle index of the three values.
 * Each sub-space is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each sub-space can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * NOTE: The array provided in the constructor MUST be ordered.
 */
public class ThreeSumQuadratic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     *
     * @param a a sorted array.
     */
    public ThreeSumQuadratic(int[] a) {
        this.a = a;
        length = a.length;
    }

    public Triple[] getTriples() {
        try(Stopwatch sw = new Stopwatch()) {

            List<Triple> triples = new ArrayList<>();
            for (int i = 0; i < length; i++) triples.addAll(getTriples(i));
            Collections.sort(triples);

            long timeTaken = sw.lap();
            System.out.println("Time taken in 3SumQuadratic: " + timeTaken+ "ms");

            return triples.stream().distinct().toArray(Triple[]::new);




        }


    }

    /**
     * Get a list of Triples such that the middle index is the given value j.
     *
     * @param j the index of the middle value.
     * @return a Triple such that
     */
    public List<Triple> getTriples(int j) {
        List<Triple> triples = new ArrayList<>();
        // Initialize i an k pointers
        int i = 0;
        int k = length-1;


        //Loop while i is less than j and k is greater than j
        while (i<j && k>j){
            int sum = a[i] + a[j] + a[k]; //Sum up the elements at the indices of i,j,k

            if(sum == 0){
                triples.add(new Triple(a[i], a[j], a[k]));  //Append the valid triple to the list
                i++;
                k--;

            }
            else if(sum<0){
                i++;   // If sum is less than 0 then increment i to increase the sum

            }
            else{
                k--;  // If sum is greater than 0 then decrement k to decrease the sum
            }

        }
        return triples; //return the list of triples

    }





    private final int[] a;
    private final int length;
}