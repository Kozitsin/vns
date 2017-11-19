package com.hse.vns.utils;

import com.hse.vns.entity.*;

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

    private boolean isEligible(Solution s, MoveAction moveAction, int clusterId) {
        Cluster cluster = s.clusters.get(clusterId);

        if ((cluster.x1 == 0 || cluster.x1 > cluster.x2 -1 )&& moveAction.verticalShift == VerticalShiftType.UP) {
            return false;
        }

        if (cluster.x2 == s.m  && moveAction.verticalShift == VerticalShiftType.DOWN) {
            return false;
        }

        if ((cluster.y1 == 0 || cluster.y1 > cluster.y2 - 1) && moveAction.horizontalShift == HorizontalShiftType.LEFT) {
            return false;
        }

        if (cluster.y2 == s.p  && moveAction.horizontalShift == HorizontalShiftType.RIGHT) {
            return false;
        }

        return true;
    }

    public MoveAction buildEligibleAction(Solution s, int clusterId) {
        MoveAction action = build();
        while (!isEligible(s, action, clusterId)) {
            action = build();
        }
        return action;
    }
}