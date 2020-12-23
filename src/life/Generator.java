package life;

/**
 * This class generates a new generation of cells depending on the prior generation.
 * A 2 dimensional boolean array represents the "universe" and contains cells that are either
 * alive (true) or dead (false).
 * To "birth" a cell exactly three neighbour cells must be alive. A cell dies if it has less than
 * two neighbours or more than three.
 */

public class Generator {

    boolean[][] currUniverse;
    int generation;

    public Generator(boolean[][] universe) {
        this.currUniverse = universe;
        this.generation = 0;
    }

    public boolean[][] nextGeneration() {

        boolean[][] nextUniverse = new boolean[currUniverse.length][currUniverse.length];
        int counter;
        int safeK;
        int safeH;
        generation++;
        for (int i = 0; i < currUniverse.length; i++) { // loops through array
            for (int j = 0; j < currUniverse.length; j++) {
                counter = 0;
                for (int k = i -1; k < i + 2; k++) { // checks neighbour cells

                    // prevents indexOutOfBounds for k
                    safeK = k;
                    safeK = k == currUniverse.length ? 0 : safeK;
                    safeK = k == -1 ? currUniverse.length -1 : safeK;

                    for (int h = j - 1; h < j + 2; h++) {

                        // prevents indexOutOfBounds for h
                        safeH = h;
                        safeH = h == currUniverse.length ? 0 : safeH;
                        safeH = h == -1 ? currUniverse.length -1 : safeH;

                        // checks for alive neighbour cell excludes itself
                        if (currUniverse[safeK][safeH] && (k != i || h != j)) {
                            counter++;
                        }
                    }
                }

                // Determines if the current cell dies or gets born
                if (currUniverse[i][j] && (counter == 2 || counter == 3)) {
                    nextUniverse[i][j] = true;

                } else {
                    nextUniverse[i][j] = false;
                }
                if (!currUniverse[i][j] && counter == 3) {
                    nextUniverse[i][j] = true;

                }
            }
        }
        currUniverse = nextUniverse;
        return nextUniverse;
    }
}