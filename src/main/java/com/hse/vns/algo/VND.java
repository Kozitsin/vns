package com.hse.vns.algo;

import com.hse.vns.entity.Solution;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

/**
 * Variable neighborhood descent, which is applied to internal loop
 */
public class VND {

    private static final Random rand = new Random();

    public static void apply(Solution s) {

        if (rand.nextBoolean()) {
            moveRow(s);
        } else{
            moveColumn(s);
        }
    }

    private static void moveRow(Solution s) {
        double best = s.GE;
        int temp1 = 0;
        int temp2 = 0;
        int temp3 = 0;
        boolean improved = false;

        for (int i = 0; i < s.m; i++) {
            for (int j = i + 1; j < s.m; j++) {
                ArrayUtils.swap(s.matrix, i, j);
                double evaluated = s.evaluate();
                if (evaluated > best){
                    //System.out.println(String.format("Improved! Was: %1$s. Now: %2$s", best, s.GE));
                    temp1 = i;
                    temp2 = j;
                    best = s.GE;
                    improved = true;
                }
                ArrayUtils.swap(s.matrix, i, j);
            }
        }
        if (improved){
            ArrayUtils.swap(s.matrix, temp1, temp2);
            temp3 = s.machineMapping[temp1];
            s.machineMapping[temp1] = s.machineMapping[temp2];
            s.machineMapping[temp2] = temp3;
            s.evaluate();
        }

    }


    private static void moveColumn(Solution s) {
        double best = s.GE;
        int temp1 = 0;
        int temp2 = 0;
        int temp3 = 0;
        boolean improved = false;

        for (int i =0; i < s.p; i++) {
            for (int j = i + 1; j < s.p; j++) {
                swapColumn(s.matrix, i, j, s.m);
                double evaluated = s.evaluate();
                if (evaluated > best){
                    //System.out.println(String.format("Improved! Was: %1$s. Now: %2$s", best, s.GE));
                    temp1 = i;
                    temp2 = j;
                    best = s.GE;
                    improved = true;
                }
                swapColumn(s.matrix, i, j, s.m);
            }
        }
        if (improved) {
            swapColumn(s.matrix, temp1, temp2, s.m);
            temp3 = s.partsMapping[temp1];
            s.partsMapping[temp1] = s.partsMapping[temp2];
            s.partsMapping[temp2] = temp3;
            s.evaluate();
        }
    }

    private static void swapColumn(boolean[][] matr, int  i, int j, int len){
        for (int k = 0; k < len; k++){
            boolean temp = matr[k][i];
            matr[k][i] = matr[k][j];
            matr[k][j] = temp;
        }
    }

}
