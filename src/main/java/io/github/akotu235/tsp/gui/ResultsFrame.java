package io.github.akotu235.tsp.gui;

import io.github.akotu235.tsp.chart.ResultsChart;
import io.github.akotu235.tsp.model.Results;
import io.github.akotu235.tsp.model.Route;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResultsFrame extends JFrame {
    private final Results results;

    public ResultsFrame(Results results) {
        setTitle("Result");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.results = results;
    }

    public void refresh() {
        if (!isActive()) {
            setFocusableWindowState(false);
        }
        getContentPane().removeAll();
        add(getPanel());
        repaint();
        revalidate();
        pack();
        setVisible(true);
        setFocusableWindowState(true);
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel chartPanel = new ResultsChart(results);
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.add(getBestResultComponent(), BorderLayout.SOUTH);
        panel.add(getResultsAndStatsComponent(), BorderLayout.EAST);
        return panel;
    }

    private JComponent getBestResultComponent() {
        JTextArea resultTextArea = new JTextArea(getShortestRoute());
        resultTextArea.setEditable(false);
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        resultTextArea.setBorder(BorderFactory.createTitledBorder("Najlepszy wynik"));
        return resultTextArea;
    }

    private JComponent getBestResultsComponent() {
        JTable table = getBestResultsTable();
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Wyniki"));
        scrollPane.setBackground(Color.WHITE);
        return scrollPane;
    }

    private JTable getBestResultsTable() {
        String[] columnNames = {"", "Koszt [" + results.getCostUnit() + "]", "Czas [s]", "Generacja"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (int i = 0; i < results.size(); i++) {
            String totalCost = String.format("%.0f", results.get(i).route().totalCost());
            String duration = String.format("%.2f", (double) results.get(i).duration().toMillis() / 1000);
            String generation = String.valueOf(results.get(i).generation());
            Object[] row = {i + 1, totalCost, duration, generation};
            model.addRow(row);
        }
        return new JTable(model);
    }

    private JComponent getStatsComponent() {
        JPanel statsPanel = new JPanel(new GridBagLayout());
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statystyki"));
        statsPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;

        statsPanel.add(new JLabel("Średnia długość trasy:"), gbc);
        gbc.gridx = 1;
        statsPanel.add(new JLabel(String.format("%.0f %s", results.getAvgTotalCost(), results.getCostUnit())), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        statsPanel.add(new JLabel("Odchylenie standardowe:"), gbc);
        gbc.gridx = 1;
        statsPanel.add(new JLabel(String.format("%.0f %s", results.getStdDev(), results.getCostUnit())), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        statsPanel.add(new JLabel("Średni czas obliczeń:"), gbc);
        gbc.gridx = 1;
        statsPanel.add(new JLabel(String.format("%.2f s", results.getAvgDurationInSeconds())), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        statsPanel.add(new JLabel("Średnia generacja optimum:"), gbc);
        gbc.gridx = 1;
        statsPanel.add(new JLabel(String.valueOf(results.getAvgGeneration())), gbc);

        return statsPanel;
    }

    private JComponent getResultsAndStatsComponent() {
        JPanel resultsAndStatsPanel = new JPanel(new BorderLayout());
        resultsAndStatsPanel.setPreferredSize(new Dimension(300, resultsAndStatsPanel.getPreferredSize().height));
        resultsAndStatsPanel.add(getBestResultsComponent(), BorderLayout.CENTER);
        resultsAndStatsPanel.add(getStatsComponent(), BorderLayout.SOUTH);

        return resultsAndStatsPanel;
    }

    private String getShortestRoute() {
        Route bestRoute = results.getBestResult().route();
        if (bestRoute == null) {
            return "";
        }
        return results.getBestResult().route().toString();
    }
}