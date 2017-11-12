package com.hse.vns.algo;

import com.hse.vns.entity.Cluster;
import com.hse.vns.entity.Solution;

import java.util.List;
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

    public static void change(Solution s) {
        int num = rand.nextInt()%4;
        if (num == 0){
            shift(ShiftWType.LEFT, ShiftHType.UP, s);
        }
        else if(num == 1){
            shift(ShiftWType.LEFT, ShiftHType.DOWN, s);
        }
        else if(num == 2) {
            shift(ShiftWType.RIGHT, ShiftHType.DOWN, s);
        }
        else{
            shift(ShiftWType.RIGHT, ShiftHType.UP, s);
        }
    }


    enum ShiftWType {
        LEFT,
        RIGHT;
    }

    enum ShiftHType {
        UP,
        DOWN;
    }

    private static void shift(ShiftWType wType, ShiftHType hType, Solution s){
        List<Cluster> clusters = s.clusters;
        double best = s.GE;
        int temp1 = 0;
        int w = 0;

        for(int i = 0; i < s.clusters.size()-1; i++){
            Cluster current = s.clusters.get(i);
            Cluster next = s.clusters.get(i+1);

            if (wType == ShiftWType.LEFT && hType == ShiftHType.UP) {
                shiftLeft(current, next);
                shiftUp(current, next);
                if (s.evaluate() > best){
                    temp1 = i;
                    w = 0;
                    best = s.GE;
                }
                shiftRight(current, next);
                shiftDown(current, next);

            } else if (wType == ShiftWType.LEFT && hType == ShiftHType.DOWN) {
                shiftLeft(current, next);
                shiftDown(current, next);
                if (s.evaluate() > best){
                    temp1 = i;
                    w = 1;
                    best = s.GE;
                }
                shiftRight(current, next);
                shiftUp(current, next);

            } else if (wType == ShiftWType.RIGHT && hType == ShiftHType.UP) {
                shiftRight(current, next);
                shiftUp(current, next);
                if (s.evaluate() > best){
                    temp1 = i;
                    w = 2;
                    best = s.GE;
                }
                shiftLeft(current, next);
                shiftDown(current, next);

            } else  {//RIGHT DOWN
                shiftRight(current, next);
                shiftDown(current, next);
                if (s.evaluate() > best){
                    temp1 = i;
                    w = 3;
                    best = s.GE;
                }
                shiftLeft(current, next);
                shiftUp(current, next);
            }
        }

        switch (w){
            case 0: shiftLeft(s.clusters.get(temp1), s.clusters.get(temp1+1));
                    shiftUp(s.clusters.get(temp1), s.clusters.get(temp1+1));
                    break;
            case 1: shiftLeft(s.clusters.get(temp1), s.clusters.get(temp1+1));
                    shiftDown(s.clusters.get(temp1), s.clusters.get(temp1+1));
                    break;
            case 2: shiftRight(s.clusters.get(temp1), s.clusters.get(temp1+1));
                    shiftUp(s.clusters.get(temp1), s.clusters.get(temp1+1));
                    break;
            case 3: shiftRight(s.clusters.get(temp1), s.clusters.get(temp1+1));
                    shiftDown(s.clusters.get(temp1), s.clusters.get(temp1+1));
                    break;
            default: break;
        }
        s.evaluate();

    }

    private static void shiftLeft(Cluster current, Cluster next){
        current.x2 -= current.x2;
        next.x1 -= next.x1;
    }

    private static void shiftRight(Cluster current, Cluster next){
        current.x2 += current.x2;
        next.x1 += next.x1;
    }

    private static void shiftUp(Cluster current, Cluster next){
        current.y2 -= current.y2;
        next.y1 -= next.y1;
    }

    private static void shiftDown(Cluster current, Cluster next){
        current.y2 += current.y2;
        next.y1 += next.y1;
    }
}
