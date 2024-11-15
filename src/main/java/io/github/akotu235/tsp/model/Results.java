package io.github.akotu235.tsp.model;

import io.github.akotu235.tsp.gui.FrameLocationManager;
import io.github.akotu235.tsp.gui.ResultsFrame;

import java.util.ArrayList;
import java.util.Comparator;

public class Results extends ArrayList<Result> {
    private final String name;
    private final String costUnit;
    private final ResultsFrame resultsFrame;

    public Results(String name, String costUnit) {
        this.name = name;
        this.costUnit = costUnit;
        this.resultsFrame = new ResultsFrame(this);
        FrameLocationManager.setResultsFrameLocation(resultsFrame);
    }

    public synchronized void addResult(Result result) {
        add(result);
        resultsFrame.refresh();
    }

    public String getName() {
        return name;
    }

    public String getCostUnit() {
        return costUnit;
    }

    public Result getBestResult() {
        return stream()
                .min(Comparator.comparing(result -> result.route().totalCost()))
                .orElse(null);
    }

    public double getAvgTotalCost() {
        return stream().mapToDouble(result -> result.route().totalCost()).average().orElse(0.0);
    }

    public double getStdDev() {
        return Math.sqrt(stream().mapToDouble(result -> Math.pow(result.route().totalCost() - getAvgTotalCost(), 2)).average().orElse(0.0));
    }

    public double getBestCost() {
        return getBestResult().route().totalCost();
    }

    public String getAvgDurationInSeconds() {
        double avg = stream().mapToLong(result -> result.duration().getSeconds()).average().orElse(0.0);
        return String.format("%.0f", avg);
    }

    public String getAvgGeneration() {
        double avg = stream().mapToLong(Result::generation).average().orElse(0.0);
        return String.format("%.0f", avg);
    }
}