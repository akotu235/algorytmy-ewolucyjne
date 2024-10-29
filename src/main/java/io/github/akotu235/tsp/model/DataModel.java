package io.github.akotu235.tsp.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DataModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Node> nodes;
    private final CostMatrix costMatrix;
    private final String costUnit;

    public DataModel(int nodeCount, String costUnit) {
        this.costUnit = costUnit;
        this.nodes = new ArrayList<>();
        this.costMatrix = new CostMatrix(nodeCount);
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void setCost(int nodeId1, int nodeId2, double cost) {
        costMatrix.setCost(nodeId1, nodeId2, cost);
    }

    public CostMatrix getCostMatrix() {
        return costMatrix;
    }

    public Node getNodeById(int id) {
        return nodes.stream()
                .filter(node -> node.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Węzeł o ID " + id + " nie został znaleziony."));
    }

    public int getNodeCount() {
        return nodes.size();
    }

    public String getCostUnit() {
        return costUnit;
    }
}