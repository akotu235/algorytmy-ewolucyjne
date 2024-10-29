package io.github.akotu235.tsp.model;

import java.io.Serial;
import java.io.Serializable;

public class Node implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int id;
    private final String name;

    public Node(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
