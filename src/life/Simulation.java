package life;

import java.util.Random;

/**
 * This class creates the two dimensional boolean array. It has a number of methods to interact with
 * array.
 * populate():
 * This method loops through the array and assigns a random boolean, thus creating the starting point
 * of the universe
 *
 * countAlive():
 * This method loops through the array and counts the alive cells and returns an integer
 *
 * resetUniverse():
 * This method assigns a new 2 dimensional boolean to the existing one, thus resetting the universe
 */

public class Simulation {
    boolean[][] universe;
    int number;


    public Simulation(int size) {
        this.number = size;
        this.universe = new boolean[size][size];
    }

    public void populate() {
        Random random = new Random();
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                universe[i][j] = random.nextBoolean();
            }
        }
    }

    public int countAlive() {
        int alive = 0;

        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                if (universe[i][j]) {
                    alive++;
                }
            }
        }

        return alive;
    }

    public void resetUniverse() {
        universe = new boolean[number][number];
    }
}
