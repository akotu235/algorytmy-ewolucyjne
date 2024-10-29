package io.github.akotu235.tsp.model;

import java.io.Serial;
import java.io.Serializable;

public class CostMatrix implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final double[][] matrix;

    public CostMatrix(int nodeCount) {
        this.matrix = new double[nodeCount][nodeCount];
    }

    public void setCost(int nodeId1, int nodeId2, double cost) {
        matrix[nodeId1][nodeId2] = cost;
    }

    public double getCost(int nodeId1, int nodeId2) {
        return matrix[nodeId1][nodeId2];
    }
}