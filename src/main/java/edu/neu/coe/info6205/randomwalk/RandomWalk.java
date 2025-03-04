/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.Arrays;


public class RandomWalk {

    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        // TO BE IMPLEMENTED  do move
        x += dx;
        y += dy;

    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        // TO BE IMPLEMENTED

        for(int i = 0; i<m; i++){
            randomMove();
        }


    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        // TO BE IMPLEMENTED 
         return Math.sqrt(x*x + y*y);
        // END SOLUTION
    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }

    public static void main(String[] args) {
        if (args.length == 0)
            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");


        // m values to be tested for determining relationship between m and d
        List<Integer> mValues = Arrays.asList(100,200,300,400,500,600,700,800,900,1000);
        int n = 30;   // number of experiments set to 30

        // Create csv containing distances for each m value
        try(FileWriter csvWriter = new FileWriter("random_walk_results.csv")){
            csvWriter.append("m (Number of Steps), d (Mean Distance\n");

            //iterating through m values
            for(int m: mValues){
                for(int i = 1; i<= 10;i++){

                    double meanDistance = randomWalkMulti(m, n);
                    // Appending to csv writer
                    csvWriter.append(m+","+i+","+meanDistance+"\n"  );

                }
            }

            System.out.println("Results saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*int m = Integer.parseInt(args[0]);
        int n = 30;
        if (args.length > 1) n = Integer.parseInt(args[1]);
        double meanDistance = randomWalkMulti(m, n);*/
        //System.out.println(m + " steps: " + meanDistance + " over " + n + " experiments");
    }

}