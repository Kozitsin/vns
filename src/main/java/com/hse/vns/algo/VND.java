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
        if (rand.nextBoolean()){
            moveRow(s);
         }
         else{
            moveColumn(s);
        }
    }

    private static void moveRow(Solution s) {
        double best = s.GE;
        int temp1 = 0;
        int temp2 = 0;

        for (int i =0; i < s.m; i++) {
            for (int j = i + 1; j < s.m; j++) {
                ArrayUtils.swap(s.matrix, i, j);
                if (s.evaluate() > best){
                    temp1 = i;
                    temp2 = j;
                    best = s.GE;
                }
                ArrayUtils.swap(s.matrix, i, j);
            }
        }
        ArrayUtils.swap(s.matrix, temp1, temp2);
        s.evaluate();
    }


    private static void moveColumn(Solution s) {
        double best = s.GE;
        int temp1 = 0;
        int temp2 = 0;

        for (int i =0; i < s.p; i++) {
            for (int j = i + 1; j < s.p; j++) {
                swapColumn(s.matrix, i, j, s.m);
                if (s.evaluate() > best){
                    temp1 = i;
                    temp2 = j;
                    best = s.GE;
                }
                swapColumn(s.matrix, i, j, s.m);
            }
        }
        swapColumn(s.matrix, temp1, temp2, s.m);
        s.evaluate();
    }

    private static void swapColumn(boolean[][]matr, int  i, int j, int len){
        for (int k = 0; k < len; k++){
            boolean temp = matr[k][i];
            matr[k][i] = matr[k][j];
            matr[k][j] = temp;
        }
    }

}
