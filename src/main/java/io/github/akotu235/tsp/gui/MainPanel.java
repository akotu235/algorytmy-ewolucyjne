package io.github.akotu235.tsp.gui;

import io.github.akotu235.tsp.chart.Chart;
import io.github.akotu235.tsp.configuration.GeneticAlgorithmConfig;
import io.github.akotu235.tsp.model.DataModel;
import io.github.akotu235.tsp.model.Results;
import io.github.akotu235.tsp.optimization.RouteOptimizer;
import io.github.akotu235.tsp.utils.DeserializeDataModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainPanel extends JPanel {
    private final JTextField executionCountField;
    private final JTextField threadPoolSizeField;
    private final JTextField populationSizeField;
    private final JTextField generationLimitField;
    private final JTextField steadyFitnessLimitField;
    private final JTextField mutationProbabilityField;
    private final JTextField crossoverProbabilityField;
    private final JLabel fileLabel;
    private final JCheckBox closeChartsCheckbox;
    private DataModel dataModel;
    private Results results;
    private ExecutorService executor;
    private ArrayList<Chart> charts;

    public MainPanel() {
        setLayout(new BorderLayout());

        charts = new ArrayList<>();

        executionCountField = new JTextField("10");
        threadPoolSizeField = new JTextField("3");
        populationSizeField = new JTextField("10000");
        generationLimitField = new JTextField("1000");
        steadyFitnessLimitField = new JTextField("100");
        mutationProbabilityField = new JTextField("0.3");
        crossoverProbabilityField = new JTextField("0.3");

        DataModelFrame dataModelFrame = new DataModelFrame();

        JButton newDataModelButton = new JButton("Utwórz model danych");
        newDataModelButton.addActionListener(e -> showDataModelFrame(dataModelFrame));

        JButton chooseFileButton = new JButton("Wybierz plik z modelem danych");
        chooseFileButton.addActionListener(e -> chooseFile());

        fileLabel = new JLabel("Nie wybrano pliku");

        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
        filePanel.setBorder(BorderFactory.createTitledBorder("Model Danych"));

        JPanel newDataModelPanel = new JPanel();
        newDataModelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        newDataModelPanel.add(newDataModelButton);
        filePanel.add(newDataModelPanel);

        JPanel chosenFilePanel = new JPanel();
        chosenFilePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        chosenFilePanel.add(chooseFileButton);
        chosenFilePanel.add(fileLabel);
        filePanel.add(chosenFilePanel);

        filePanel.setPreferredSize(new Dimension(600, filePanel.getPreferredSize().height));

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBorder(BorderFactory.createTitledBorder("Ustawienia"));
        settingsPanel.add(new JLabel("Ilość uruchomień:"));
        settingsPanel.add(executionCountField);
        settingsPanel.add(new JLabel("Ilość wątków:"));
        settingsPanel.add(threadPoolSizeField);
        closeChartsCheckbox = new JCheckBox("Zamykaj wykresy postępu po zakończeniu algorytmu");
        closeChartsCheckbox.setSelected(true);
        settingsPanel.add(closeChartsCheckbox);

        JPanel paramPanel = new JPanel();
        paramPanel.setLayout(new BoxLayout(paramPanel, BoxLayout.Y_AXIS));
        paramPanel.setBorder(BorderFactory.createTitledBorder("Parametry Algorytmu"));
        paramPanel.add(new JLabel("Wielkość populacji:"));
        paramPanel.add(populationSizeField);
        paramPanel.add(new JLabel("Limit pokoleń:"));
        paramPanel.add(generationLimitField);
        paramPanel.add(new JLabel("Próg stagnacji:"));
        paramPanel.add(steadyFitnessLimitField);
        paramPanel.add(new JLabel("Prawdopodobieństwo mutacji:"));
        paramPanel.add(mutationProbabilityField);
        paramPanel.add(new JLabel("Prawdopodobieństwo krzyżowania:"));
        paramPanel.add(crossoverProbabilityField);

        JPanel configurationPanel = new JPanel();
        configurationPanel.setLayout(new BorderLayout());

        configurationPanel.add(filePanel, BorderLayout.NORTH);
        configurationPanel.add(settingsPanel, BorderLayout.CENTER);
        configurationPanel.add(paramPanel, BorderLayout.SOUTH);

        JButton runAlgorithmButton = new JButton("Uruchom Algorytm");
        runAlgorithmButton.addActionListener(e -> runAlgorithm());
        runAlgorithmButton.setPreferredSize(new Dimension(200, 50));

        add(configurationPanel, BorderLayout.CENTER);
        add(runAlgorithmButton, BorderLayout.SOUTH);
    }

    private void showDataModelFrame(DataModelFrame dataModelFrame) {
        dataModelFrame.setSize(400, 400);
        dataModelFrame.setVisible(true);
    }

    private void runAlgorithm() {
        if (dataModel == null) {
            JOptionPane.showMessageDialog(this, "Proszę wybrać plik z modelem danych.");
            return;
        }

        GeneticAlgorithmConfig config = readConfig();

        terminateExecutor();

        executor = Executors.newFixedThreadPool(config.threadPoolSize());

        FrameLocationManager frameLocationManager = new FrameLocationManager();

        charts = new ArrayList<>();

        for (int i = 0; i < config.executionCount(); i++) {
            RouteOptimizer routeOptimizer = new RouteOptimizer(dataModel, config, results, frameLocationManager);
            Future<?> routeOptimizerHandle = executor.submit(routeOptimizer);
            routeOptimizer.setRouteOptimizerHandle(routeOptimizerHandle);
            charts.add(routeOptimizer.getChart());
        }

        executor.shutdown();
    }

    private GeneticAlgorithmConfig readConfig() {
        int executionCount = Integer.parseInt(executionCountField.getText());
        int threadPoolSize = Integer.parseInt(threadPoolSizeField.getText());
        int populationSize = Integer.parseInt(populationSizeField.getText());
        int generationLimit = Integer.parseInt(generationLimitField.getText());
        int steadyFitnessLimit = Integer.parseInt(steadyFitnessLimitField.getText());
        double mutationProbability = Double.parseDouble(mutationProbabilityField.getText());
        double crossoverProbability = Double.parseDouble(crossoverProbabilityField.getText());
        boolean closeCharts = closeChartsCheckbox.isSelected();

        return new GeneticAlgorithmConfig(executionCount, threadPoolSize, populationSize, generationLimit, steadyFitnessLimit, mutationProbability, crossoverProbability, closeCharts);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Pliki modelu danych", "ser"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            terminateExecutor();
            File dataModelFile = fileChooser.getSelectedFile();
            dataModel = DeserializeDataModel.loadDataModelFromFile(dataModelFile.getAbsolutePath());
            fileLabel.setText("Wybrano plik: " + dataModelFile.getName());
            results = new Results(dataModelFile.getName(), dataModel.getCostUnit());
        }
    }

    private void terminateExecutor() {
        for (Chart chart : charts) {
            chart.close();
        }

        if (executor != null) {
            executor.shutdownNow();
        }
    }
}