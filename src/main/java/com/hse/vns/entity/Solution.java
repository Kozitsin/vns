package com.hse.vns.entity;

import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class Solution {
    /**
     * Holder for temporary solution
     */
    public boolean[][] matrix;

    public int eigens = 0;

    /**
     *
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
        // deep copy of clusters
    }

    /**
     * count eigens only one
     * for each cluster iterate over elements and increment eigens/zeroes in each cluster
     * @return
     */
    private int countEigens() {
        for (int i = 0; i< matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {

            }
        }
    }

    public double evaluate() {
        GE = 0.0;
        for (int i = 0; i < clusters.size(); i++) {

        }
    }

}
