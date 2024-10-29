package io.github.akotu235.tsp.model.geo;

import java.io.Serial;
import java.io.Serializable;

public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final double lat;
    private final double lon;

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return String.valueOf(lat).replace(",", ".");
    }

    public String getLon() {
        return String.valueOf(lon).replace(",", ".");
    }
}