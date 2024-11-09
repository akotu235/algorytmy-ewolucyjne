package io.github.akotu235.tsp.model;

import io.github.akotu235.tsp.gui.ResultsFrame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Results {
    private final List<Route> results;
    private final String name;
    private final ResultsFrame resultsFrame;

    public Results(String name) {
        this.results = new ArrayList<>();
        this.name = name;
        this.resultsFrame = new ResultsFrame(this);
    }

    public synchronized void add(Route route) {
        this.results.add(route);
        resultsFrame.refresh();
    }

    public int size() {
        return this.results.size();
    }

    public List<Route> getResults() {
        return results;
    }

    public String getName() {
        return name;
    }

    public Route getShortestRoute() {
        return results.stream()
                .min(Comparator.comparing(Route::totalCost))
                .orElse(null);
    }

    public double getAverage(){
      return results.stream().mapToDouble(Route::totalCost).average().orElse(0.0);
    }

    public double getStdDev(){
        return Math.sqrt(results.stream().mapToDouble(r -> Math.pow(r.totalCost() - getAverage(), 2)).average().orElse(0.0));
    }

    public double getBestCost(){
        return getShortestRoute().totalCost();
    }

    @Override
    public String toString() {
        return IntStream.range(0, results.size())
                .mapToObj(i -> String.format("%d. %.0f %s", i + 1, results.get(i).totalCost(), results.get(i).costUnit()))
                .collect(Collectors.joining("\n"));
    }
}
