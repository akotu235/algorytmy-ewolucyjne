package io.github.akotu235.tsp.chart;

import io.github.akotu235.tsp.model.Results;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import javax.swing.*;
import java.awt.*;

public class ResultsChart extends JPanel {

    public ResultsChart(Results results) {
        XYSeries series = new XYSeries("Koszt");
        for (int i = 0; i < results.size(); i++) {
            series.add(i + 1, results.get(i).route().totalCost());
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Najlepsze Wyniki Algorytmu w Kolejnych Uruchomieniach",
                "Numer Uruchomienia",
                "Najlepszy Wynik [" + results.getCostUnit() + "]",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);

        double average = results.getAvgTotalCost();
        double stdDev = results.getStdDev();
        double bestCost = results.getBestCost();

        if (bestCost != average) {
            ValueMarker meanMarker = new ValueMarker(average);
            meanMarker.setPaint(Color.BLUE);
            meanMarker.setStroke(new BasicStroke(1.5f));
            meanMarker.setLabel("Średnia");
            meanMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
            meanMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
            plot.addRangeMarker(meanMarker);

            ValueMarker stdDevAbove = new ValueMarker(average + stdDev);
            stdDevAbove.setPaint(Color.GREEN);
            stdDevAbove.setStroke(new BasicStroke(1.5f));
            stdDevAbove.setLabel("Średnia + SD");
            stdDevAbove.setLabelAnchor(RectangleAnchor.TOP_LEFT);
            stdDevAbove.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
            plot.addRangeMarker(stdDevAbove);

            ValueMarker stdDevBelow = new ValueMarker(average - stdDev);
            stdDevBelow.setPaint(Color.GREEN);
            stdDevBelow.setStroke(new BasicStroke(1.5f));
            stdDevBelow.setLabel("Średnia - SD");
            stdDevBelow.setLabelAnchor(RectangleAnchor.TOP_LEFT);
            stdDevBelow.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
            plot.addRangeMarker(stdDevBelow);
        }

        ValueMarker bestMarker = new ValueMarker(bestCost);
        bestMarker.setPaint(Color.RED);
        bestMarker.setStroke(new BasicStroke(2.0f));
        bestMarker.setLabel("Najlepszy");
        bestMarker.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        bestMarker.setLabelTextAnchor(TextAnchor.BOTTOM_LEFT);
        plot.addRangeMarker(bestMarker);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setDomainGridlineStroke(new BasicStroke(0.5f));

        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        if (results.size() < 30) {
            domainAxis.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(1));
        } else if (results.size() < 100) {
            domainAxis.setTickUnit(new org.jfree.chart.axis.NumberTickUnit(10));
        }
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 12));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        this.setLayout(new BorderLayout());
        this.add(chartPanel, BorderLayout.CENTER);
    }
}