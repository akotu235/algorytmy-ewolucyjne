package io.github.akotu235.tsp.model;

import java.util.List;
import java.util.stream.Collectors;

public record Route(List<Node> nodes, double totalCost, String costUnit) {
    @Override
    public String toString() {
        String nodeNames = nodes.stream()
                .map(Node::getName)
                .collect(Collectors.joining(" -> "));

        return String.format("Koszt: %.0f %s\n%s\n", totalCost, costUnit, nodeNames);
    }
}