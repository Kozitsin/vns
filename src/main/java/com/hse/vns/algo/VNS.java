package com.hse.vns.algo;

import com.hse.vns.entity.*;
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

            for (int k = 0; k < 10; k++){
                change(s);
                if (best.GE < s.GE) {
                    best.copyFrom(s);
                }

            }

        }

        return s;
    }

    private static void change(Solution s) {
        int clusterId = rand.nextInt(s.clusters.size());
        MoveAction action = factory.buildEligibleAction(s, clusterId);
        shift(s, action, clusterId);
    }

    private static void shift(Solution s, MoveAction moveAction, int clusterId) {
        double best = s.GE;

        Cluster current;
        Cluster next;

        // if last cluster, we will take previous
        if (clusterId == s.clusters.size() - 1) {
            current = s.clusters.get(clusterId - 1);
            next = s.clusters.get(clusterId);
        } else {
            current = s.clusters.get(clusterId);
            next = s.clusters.get(clusterId + 1);
        }

        if (moveAction.horizontalShift == HorizontalShiftType.LEFT &&
                moveAction.verticalShift == VerticalShiftType.UP) {
            System.out.println(String.format("ID: %1$s, HST: %2$s, VSH:%3$s", clusterId, moveAction.horizontalShift, moveAction.verticalShift));
            System.out.println(String.format("current x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", current.x1, current.x2, current.y1, current.y2));
            System.out.println(String.format("next    x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", next.x1, next.x2, next.y1, next.y2));
            shiftLeft(current, next);
            shiftUp(current, next);
            if (s.evaluate() < best) {
                shiftRight(current, next);
                shiftDown(current, next);
            }
        } else if (moveAction.horizontalShift == HorizontalShiftType.LEFT &&
                moveAction.verticalShift == VerticalShiftType.DOWN) {
            System.out.println(String.format("ID: %1$s, HST: %2$s, VSH:%3$s", clusterId, moveAction.horizontalShift, moveAction.verticalShift));
            System.out.println(String.format("current x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", current.x1, current.x2, current.y1, current.y2));
            System.out.println(String.format("next    x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", next.x1, next.x2, next.y1, next.y2));
            shiftLeft(current, next);
            shiftDown(current, next);
            if (s.evaluate() < best) {
                shiftRight(current, next);
                shiftUp(current, next);
            }
        } else if (moveAction.horizontalShift == HorizontalShiftType.RIGHT &&
                moveAction.verticalShift == VerticalShiftType.UP) {
            System.out.println(String.format("ID: %1$s, HST: %2$s, VSH:%3$s", clusterId, moveAction.horizontalShift, moveAction.verticalShift));
            System.out.println(String.format("current x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", current.x1, current.x2, current.y1, current.y2));
            System.out.println(String.format("next    x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", next.x1, next.x2, next.y1, next.y2));
            shiftRight(current, next);
            shiftUp(current, next);
            if (s.evaluate() < best) {
                shiftLeft(current, next);
                shiftDown(current, next);
            }
        } else  {
            System.out.println(String.format("ID: %1$s, HST: %2$s, VSH:%3$s", clusterId, moveAction.horizontalShift, moveAction.verticalShift));
            System.out.println(String.format("current x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", current.x1, current.x2, current.y1, current.y2));
            System.out.println(String.format("next    x1: %1$s, x2: %2$s, y1: %3$s, y2: %4$s", next.x1, next.x2, next.y1, next.y2));
            shiftRight(current, next);
            shiftDown(current, next);
            if (s.evaluate() < best) {
                shiftLeft(current, next);
                shiftUp(current, next);
            }
        }
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
