package com.hse.vns.entity;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Solution {
    /**
     * Holder for temporary solution
     */
    public boolean[][] matrix;

    public static int eigens = 0;
    public int m = 0;
    public int p = 0;

    public int[] partsMapping;
    public int[] machineMapping;

    /**
     * List of Cluster objects in current solution
     */
    public List<Cluster> clusters;

    /**
     * Objective Function
     */
    public double GE;


    public Solution(int m, int p, boolean[][] matrix){
        this.m = m;
        this.p = p;
        this.matrix = matrix;
        this.clusters = initPartition();
        this.machineMapping = new int[m];
        this.partsMapping = new int[p];
        fillMappings(this.machineMapping);
        fillMappings(this.partsMapping);
    }

    public Solution(Solution s) {
        this.GE = s.GE;
        this.m = s.m;
        this.p = s.p;
        this.matrix = new boolean[m][p];
        for (int  i = 0 ; i  < m; i++) {
            this.matrix[i] = ArrayUtils.clone(s.matrix[i]);
        }
        this.machineMapping = ArrayUtils.clone(s.machineMapping);
        this.partsMapping = ArrayUtils.clone(s.partsMapping);

        this.clusters = s.clusters.stream()
                .map(c -> new Cluster(c.x1, c.y1, c.x2, c.y2))
                .collect(toList());
    }

    public void copyFrom(Solution s) {
        this.GE = s.GE;
        this.m = s.m;
        this.p = s.p;
        this.matrix = new boolean[m][p];
        for (int  i = 0 ; i  < m; i++) {
            this.matrix[i] = ArrayUtils.clone(s.matrix[i]);
        }
        this.machineMapping = ArrayUtils.clone(s.machineMapping);
        this.partsMapping = ArrayUtils.clone(s.partsMapping);
        this.clusters = s.clusters.stream()
                .map(c -> new Cluster(c.x1, c.y1, c.x2, c.y2))
                .collect(toList());
    }

    private List<Cluster> initPartition() {
        List<Cluster> clusters = new ArrayList<>(m);
        int x = 0;
        int y = 0;
        for (; x < m - 1; x++) {
            clusters.add(new Cluster(x, x, x+1, x+1));
        }
        clusters.add(new Cluster(x, x, x+1, p));
        return clusters;
    }

    private void fillMappings(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
    }

    public String printMachine() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clusters.size(); i++) {
            Cluster c = clusters.get(i);
            for(int j = c.x1; j <c.x2; j++){
                sb.append(machineMapping[j]+1).append("_").append(i + 1).append(" ");
            }
        }
        return sb.toString();
    }

    public String printParts() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clusters.size(); i++) {
            Cluster c = clusters.get(i);
            for(int j = c.y1; j <c.y2; j++){
                sb.append(partsMapping[j]+1).append("_").append(i + 1).append(" ");
            }
        }
        return sb.toString();
    }
    /**
    public String getMachinesLine() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < this.machineMapping.length; i++) {
            int originalMachineId = this.machineMapping[i];
            int clusterId = getClusterIdForMachine(i);
            sb.append(originalMachineId + 1).append("_").append(clusterId + 1).append(" ");
        }
        return sb.toString();
    }

    private int getClusterIdForMachine(int machineId) {
        for (int j = 0; j < clusters.size(); j++) {
            boolean[] line = matrix[machineId];
            Cluster c = clusters.get(j);
            for (int i = c.y1; i < c.y2; i++) {
                if (line[i]) {
                    return j;
                }
            }
        }
        return -1;
    }

    public String getPartsLine() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < this.partsMapping.length; i++) {
            int originalPartId = this.partsMapping[i];
            int clusterPId = getClusterIdForParts(i);
            sb.append(originalPartId + 1).append("_").append(clusterPId + 1).append(" ");
        }
        return sb.toString();
    }

    private int getClusterIdForParts(int partId) {
        for (int j = 0; j < clusters.size(); j++) {
            Cluster c = clusters.get(j);
            for (int i = c.x1; i < c.x2; i++) {
                if (matrix[i][partId]) {
                    return j;
                }
            }
        }
        return -1;
    }


     * count eigens only one
     * for each cluster iterate over elements and increment eigens/zeroes in each cluster
     * @return eigens variable
     */
    public int countEigens() {
        for (int i = 0; i< m; i++) {
            for (int j = 0; j < p; j++) {
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
                    //System.out.println(String.format("x: %1$s, y:%2$s", x, y));
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

    public String printMatrix() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            sb.append(StringUtils.join(convert(matrix[i]), ' ')).append("\n");
        }
        return sb.toString();
    }

    private int[] convert(boolean[] arr) {
        int[] intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = arr[i] ? 1 : 0;
        }
        return intArr;
    }
}
