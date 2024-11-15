package io.github.akotu235.tsp.model;

import java.time.Duration;

public record Result(Route route, Duration duration, long generation) {
}