package com.hse.vns.entity;

/**
 * Each cluster is described with two pairs of coordinates;
 * In order to check feasibility of the solution we should
 * compare last pair of n-1 cluster coordinates and first pair of n cluster coordinates.
 */
public class Cluster {
    public int x1;
    public int y1;

    public int x2;
    public int y2;

//public int eigensCount = 0;
//public int zeroesCount = 0;

    public Cluster(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}
