package com.hse.vns.algo;

import com.hse.vns.entity.*;
import org.apache.commons.math3.util.MathUtils;

import java.util.Random;

/**
 *  Generate a new Solution via cluster split/union or
 *  moving cluster borders without number of clusters change
 *
 *  While termination condition is not met:
 *      choose solution from neighbourhood;
 *      use VND. improve solution
 *      if solution is better - use it
 *      otherwise - use next from neighbourhood
 */
public class VNS {
    private static final boolean DEBUG_MODE = false;
    private static final Random rand = new Random();
    private static final int NUMBER_OF_ITERATIONS = 100000;

    private static boolean isTerminationCondition(int i) {
        return i >= NUMBER_OF_ITERATIONS;
    }

    public static Solution execute(Solution s) {
        s.evaluate();

        Solution best = new Solution(s);
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {

            VND.apply(s);

            if (best.GE < s.GE) {
                best.copyFrom(s);
                System.out.println(best.GE);

                if (MathUtils.equals(1.0, s.GE)) {
                    break;
                }
            }

            for (int k = 0; k < NUMBER_OF_ITERATIONS / 100; k++) {
                if (rand.nextBoolean()) {
                    join(s);
                } else {
                    split(s);
                }
                if (best.GE < s.GE) {
                    best.copyFrom(s);
                }
            }
        }
        return best;
    }

    private static void split(Solution s) {
        int clusterId = rand.nextInt(s.clusters.size());
        Cluster current = s.clusters.get(clusterId);

        int diff = current.x2 - current.x1;
        int diff2 = current.y2 - current.y1;

        if (diff > 1 && diff == diff2) {
            int split = rand.nextInt(diff) + current.x1 + 1;

            if (DEBUG_MODE) {
                System.out.println(String.format("Diff: %1$s; Split: %2$s;", diff, split));
            }

            if (split != current.x2 && split != current.y2 && split != current.x1 && split != current.y1) {
                Cluster before = new Cluster(current.x1, current.y1, split, split);
                Cluster after = new Cluster(split, split, current.x2, current.y2);

                if (DEBUG_MODE) {
                    System.out.println(String.format("Current cluster: %1$s", current));
                    System.out.println(String.format("Previous cluster: %1$s", before));
                    System.out.println(String.format("Next cluster: %1$s", after));
                }

                s.clusters.remove(clusterId);
                s.clusters.add(clusterId, before);
                s.clusters.add(clusterId + 1, after);
            }

            if (DEBUG_MODE) {
                System.out.println(String.format("Clusters: %1$s", s.clusters));
            }
        }

        if (DEBUG_MODE) System.out.println(String.format("Before: %1$s", s.clusters));
        s.clusters.removeIf(c -> c.x1 == c.x2 && c.y1 == c.y2);
        if (DEBUG_MODE) System.out.println(String.format("After: %1$s", s.clusters));
        s.evaluate();
    }

    private static void join(Solution s) {
        if (s.clusters.size() <= 1) {
            //System.out.println("Join cannot be applied. cluster size is <= 1");
            return;
        }
        int clusterId = rand.nextInt(s.clusters.size() - 1);
        Cluster current = s.clusters.get(clusterId);
        Cluster next = s.clusters.get(clusterId + 1);

        Cluster joined = new Cluster(current.x1, current.y1, next.x2, next.y2);

        if (DEBUG_MODE) {
            System.out.println(String.format("Current cluster: %1$s", current));
            System.out.println(String.format("Next cluster: %1$s", next));
            System.out.println(String.format("Joined cluster: %1$s", joined));
        }

        s.clusters.remove(clusterId);
        s.clusters.add(clusterId, joined);
        s.clusters.remove(clusterId + 1);
    }
}
