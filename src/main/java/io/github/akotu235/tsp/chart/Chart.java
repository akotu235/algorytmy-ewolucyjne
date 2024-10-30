package io.github.akotu235.tsp.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Chart extends JFrame {
    private final XYSeries bestSeries;
    private final XYSeries avgSeries;
    private final XYSeries worstSeries;
    private final JFreeChart chart;
    private double minY = Double.MAX_VALUE;
    private double maxY = Double.MIN_VALUE;

    public Chart() {
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
                "Wartość Fitness",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
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

    public void saveToFile() {
        try {
            File file = new File("wykres.png");
            ChartUtilities.saveChartAsPNG(file, chart, 800, 600);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania wykresu: " + e.getMessage());
        }
    }
}