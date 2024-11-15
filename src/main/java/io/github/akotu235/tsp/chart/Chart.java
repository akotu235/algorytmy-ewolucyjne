package io.github.akotu235.tsp.chart;

import io.github.akotu235.tsp.gui.FrameLocationManager;
import io.github.akotu235.tsp.optimization.RouteOptimizer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Chart extends JFrame {
    private final XYSeries bestSeries;
    private final XYSeries avgSeries;
    private final XYSeries worstSeries;
    private final JFreeChart chart;
    private final RouteOptimizer routeOptimizer;
    private final FrameLocationManager frameLocationManager;
    private int slot;
    private double minY = Double.MAX_VALUE;
    private double maxY = Double.MIN_VALUE;

    public Chart(RouteOptimizer routeOptimizer, String costUnit, FrameLocationManager frameLocationManager) {
        setTitle("Chart");
        this.routeOptimizer = routeOptimizer;
        this.frameLocationManager = frameLocationManager;
        bestSeries = new XYSeries("Najlepszy Wynik");
        avgSeries = new XYSeries("Średni Wynik");
        worstSeries = new XYSeries("Najgorszy Wynik");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(bestSeries);
        dataset.addSeries(avgSeries);
        dataset.addSeries(worstSeries);

        chart = ChartFactory.createXYLineChart(
                "Postęp w Optymalizacji",
                "Generacja",
                "Wartość Fitness [" + costUnit + "]",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));
        renderer.setSeriesStroke(2, new BasicStroke(2.0f));
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesShapesVisible(2, false);
        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

        setVisible(false);
    }

    public void updateChart(long generation, double bestFitness, double averageFitness, double worstFitness) {
        bestSeries.add(generation, bestFitness);
        avgSeries.add(generation, averageFitness);
        worstSeries.add(generation, worstFitness);

        if (bestFitness < minY) minY = bestFitness;
        if (worstFitness > maxY) maxY = worstFitness;

        double range = maxY - minY;
        double margin = range * 0.1;

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRangeAxis().setRange(minY - margin, maxY + margin);

        chart.fireChartChanged();
    }

    public void close() {
        onClose();
        SwingUtilities.invokeLater(this::dispose);
    }

    private void onClose() {
        frameLocationManager.releaseSlot(slot);
        routeOptimizer.interrupt();
    }

    public void open() {
        slot = frameLocationManager.setChartFrameLocation(this);
        setFocusableWindowState(false);
        setVisible(true);
        setFocusableWindowState(true);
    }
}