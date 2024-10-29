package io.github.akotu235.tsp.model.geo;

import io.github.akotu235.tsp.model.Node;
import io.github.akotu235.tsp.service.LocationService;

import java.io.IOException;
import java.net.URISyntaxException;

public class City extends Node {
    private final Coordinates coordinates;

    public City(int id, String name) {
        super(id, name);
        try {
            this.coordinates = LocationService.getCoordinates(name);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Obliczanie współrzędnych dla miasta " + name + " nie powiodło się.");
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
