package io.github.akotu235.tsp.chart;

import java.util.ArrayList;
import java.util.List;

public class ChartManager {
    private List<Chart> charts;

    public ChartManager() {
        this.charts = new ArrayList<>();
    }

    public void add(Chart chart) {
        this.charts.add(chart);
    }

    public void closeAll() {
        if (!charts.isEmpty()) {
            charts.forEach(Chart::close);
        }
        charts = new ArrayList<>();
    }
}
