package com.hse.vns.entity;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class Solution {
    /**
     * Holder for temporary solution
     */
    public boolean[][] matrix;

    public int eigens = 0;
    public int m = 0;
    public int p = 0;

    /**
     * List of Cluster objects in current solution
     */
    public List<Cluster> clusters;

    /**
     * Objective Function
     */
    public double GE;

    public Solution(Solution s) {
        this.GE = s.GE;
        this.eigens = 0;
        this.matrix = ArrayUtils.clone(s.matrix);
        this.m = s.m;
        this.p = s.p;
        // deep copy of clusters
    }

    /**
     * count eigens only one
     * for each cluster iterate over elements and increment eigens/zeroes in each cluster
     * @return eigens variable
     */

    private int countEigens() {
        for (int i = 0; i< matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                eigens += matrix[i][j] ? 1 : 0;
            }
        }
        return eigens;
    }

    public double evaluate() {
        GE = 0.0;

        int clustersEigens = 0;
        int clustersZeroes = 0;

        for (Cluster cluster : clusters) {
            for (int x = cluster.x1; x < cluster.x2; x++) {
                for (int y = cluster.y1; y < cluster.y2; y++ ) {
                    if (matrix[x][y]) {
                        clustersEigens++;
                    } else{
                        clustersZeroes++;
                    }
                }
            }
        }
        GE = clustersEigens / (double)(eigens + clustersZeroes);
        return GE;
    }

}
