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
    private static final int MAX_ITER_WO_IMP_NUM = 3000000;

    public static Solution execute(Solution s) {
        s.evaluate();
        Solution best = new Solution(s);

        int iterationsWithoutImprovement = 0;

        while (iterationsWithoutImprovement < MAX_ITER_WO_IMP_NUM) {
            VND.apply(s);

            if (best.GE < s.GE) {
                iterationsWithoutImprovement = 0;
                best.copyFrom(s);
                System.out.println(best.GE);
                if (MathUtils.equals(1.0, s.GE)) {
                    break;
                }
            } else {
                iterationsWithoutImprovement++;
            }

            if (rand.nextBoolean()) {
                join(s);
            } else {
                split(s);
            }

            if (best.GE < s.GE) {
                iterationsWithoutImprovement = 0;
                best.copyFrom(s);
            }

//            for (int k = 0; k < MAX_ITER_NUM; k++) {
//                if (rand.nextBoolean()) {
//                    join(s);
//                } else {
//                    split(s);
//                }
//                if (best.GE < s.GE) {
//                    best.copyFrom(s);
//                }
//            }
        }
        return best;
    }

    private static void split(Solution s) {
        int clusterId = rand.nextInt(s.clusters.size());
        Cluster current = s.clusters.get(clusterId);

        int diff1 = current.x2 - current.x1 - 1;
        int diff2 = current.y2 - current.y1 - 1;

        if (diff1 > 0 && diff2 > 0) {
            int splitX = rand.nextInt(diff1) + current.x1 + 1;
            int splitY = rand.nextInt(diff2) + current.y1 + 1;

            if (DEBUG_MODE) {
                //System.out.println(String.format("Diff: %1$s; Split: %2$s;", diff, split));
            }

            if (splitX != current.x2 && splitY != current.y2 && splitX != current.x1 && splitY != current.y1) {
                Cluster before = new Cluster(current.x1, current.y1, splitX, splitY);
                Cluster after = new Cluster(splitX, splitY, current.x2, current.y2);

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
