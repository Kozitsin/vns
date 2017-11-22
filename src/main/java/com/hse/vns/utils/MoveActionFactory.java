package com.hse.vns.utils;

import com.hse.vns.entity.*;
import com.hse.vns.exceptions.InvalidClusterException;

import java.util.Random;

public class MoveActionFactory {
    private static final Random rand = new Random();
    private static final MoveAction[] actions = new MoveAction[] {
            new MoveAction(VerticalShiftType.UP, HorizontalShiftType.LEFT),
            new MoveAction(VerticalShiftType.DOWN, HorizontalShiftType.LEFT),
            new MoveAction(VerticalShiftType.UP, HorizontalShiftType.RIGHT),
            new MoveAction(VerticalShiftType.DOWN, HorizontalShiftType.RIGHT),
    };

    private MoveAction build() {
        return actions[rand.nextInt(4)];
    }

    private boolean isEligible(Solution s, MoveAction moveAction, int clusterId) throws InvalidClusterException {
        Cluster cluster = s.clusters.get(clusterId);

        if (cluster.x1 > cluster.x2 || cluster.y1 > cluster.y2) {
            throw new InvalidClusterException(cluster.toString());
        }

        if (cluster.x1 == 0 && moveAction.verticalShift == VerticalShiftType.UP) {
            return false;
        }

        if (cluster.x2 == s.m && moveAction.verticalShift == VerticalShiftType.DOWN) {
            return false;
        }

        if (cluster.y1 == 0 && moveAction.horizontalShift == HorizontalShiftType.LEFT) {
            return false;
        }

        if (cluster.y2 == s.p && moveAction.horizontalShift == HorizontalShiftType.RIGHT) {
            return false;
        }

        return true;
    }

    public MoveAction buildEligibleAction(Solution s, int clusterId) {
        MoveAction action = build();
        try {
            while (!isEligible(s, action, clusterId)) {
                action = build();
            }
        } catch (InvalidClusterException e) {
            System.out.println(s.clusters);
            System.out.println(e.toString());
            System.exit(-1);
            action = null;
        }
        return action;
    }
}
