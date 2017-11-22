package com.hse.vns.algo;

import com.hse.vns.entity.*;
import com.hse.vns.exceptions.InvalidClusterException;
import com.hse.vns.utils.MoveActionFactory;

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
    private static final boolean DEBUG_MODE = true;
    private static final Random rand = new Random();
    private static final int NUMBER_OF_ITERATIONS = 1000;
    private static final MoveActionFactory factory = new MoveActionFactory();

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
            }

            for (int k = 0; k < NUMBER_OF_ITERATIONS / 100; k++) {
                if (rand.nextBoolean()) {
                    change(s);
                } else {
                    split(s);
                }
                if (best.GE < s.GE) {
                    best.copyFrom(s);
                }
            }
        }
        return s;
    }

    private static void split(Solution s) {
        int clusterId = rand.nextInt(s.clusters.size() - 1);
        Cluster current = s.clusters.get(clusterId);

        int diff = current.x2 - current.x1;
        int diff2 = current.y2 - current.y1;

        if (diff > 1 && diff == diff2) {
            int split = rand.nextInt(diff) + current.x1 + 1;

            System.out.println(String.format("Diff: %1$s; Split: %2$s;",
                    diff, split));

            if (split != current.x2 && split != current.y2 && split != current.x1 && split != current.y1) {
                Cluster before = new Cluster(current.x1, current.y1, split, split);
                Cluster after = new Cluster(split, split, current.x2, current.y2);

                System.out.println(String.format("Current cluster: %1$s", current));
                System.out.println(String.format("Previous cluster: %1$s", before));
                System.out.println(String.format("Next cluster: %1$s", after));

                s.clusters.remove(clusterId);
                s.clusters.add(clusterId, before);
                s.clusters.add(clusterId + 1, after);
            }

            System.out.println(String.format("Clusters: %1$s", s.clusters));
        }

        System.out.println(String.format("Before: %1$s", s.clusters));
        s.clusters.removeIf(c -> c.x1 == c.x2 && c.y1 == c.y2);
        System.out.println(String.format("After: %1$s", s.clusters));
        s.evaluate();
    }

    private static void change(Solution s) {
        if (s.clusters.size() - 1 > 0) {
            int clusterId = rand.nextInt(s.clusters.size() - 1);
            MoveAction action = factory.buildEligibleAction(s, clusterId);
            if (action != null) {
                shift(s, action, clusterId);
            }
        }
    }

    private static void shift(Solution s, MoveAction moveAction, int clusterId) {
        double best = s.GE;

        Cluster current;
        Cluster next;

        current = s.clusters.get(clusterId);
        next = s.clusters.get(clusterId + 1);

        if (DEBUG_MODE) {
            System.out.println(String.format("ID: %1$s, HST: %2$s, VSH:%3$s", clusterId, moveAction.horizontalShift, moveAction.verticalShift));
            System.out.println(String.format("current x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", current.x1, current.x2, current.y1, current.y2));
            System.out.println(String.format("next    x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", next.x1, next.x2, next.y1, next.y2));
        }

        if (moveAction.horizontalShift == HorizontalShiftType.LEFT &&
                moveAction.verticalShift == VerticalShiftType.UP) {
            shiftLeft(current, next);
            shiftUp(current, next);
//            if (s.evaluate() < best) {
//                shiftRight(current, next);
//                shiftDown(current, next);
//            }
        } else if (moveAction.horizontalShift == HorizontalShiftType.LEFT &&
                moveAction.verticalShift == VerticalShiftType.DOWN) {
            shiftLeft(current, next);
            shiftDown(current, next);
//            if (s.evaluate() < best) {
//                shiftRight(current, next);
//                shiftUp(current, next);
//            }
        } else if (moveAction.horizontalShift == HorizontalShiftType.RIGHT &&
                moveAction.verticalShift == VerticalShiftType.UP) {
            shiftRight(current, next);
            shiftUp(current, next);
//            if (s.evaluate() < best) {
//                shiftLeft(current, next);
//                shiftDown(current, next);
//            }
        } else  {
            shiftRight(current, next);
            shiftDown(current, next);
//            if (s.evaluate() < best) {
//                shiftLeft(current, next);
//                shiftUp(current, next);
//            }
        }

        System.out.println(String.format("Before: %1$s", s.clusters));
        s.clusters.removeIf(c -> c.x1 == c.x2 && c.y1 == c.y2);
        System.out.println(String.format("After: %1$s", s.clusters));

        s.evaluate();
    }

    private static void shiftLeft(Cluster current, Cluster next){
        current.y2--;
        next.y1--;
    }

    private static void shiftRight(Cluster current, Cluster next){
        current.y2++;
        next.y1++;
    }

    private static void shiftUp(Cluster current, Cluster next){
        current.x2--;
        next.x1--;
    }

    private static void shiftDown(Cluster current, Cluster next){
        current.x2++;
        next.x1++;
    }
}
