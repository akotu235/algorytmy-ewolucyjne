package io.github.akotu235.tsp.gui;

import io.github.akotu235.tsp.configuration.GeneticAlgorithmConfig;
import io.github.akotu235.tsp.model.DataModel;
import io.github.akotu235.tsp.optimization.RouteOptimizer;
import io.github.akotu235.tsp.utils.DeserializeDataModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class MainPanel extends JPanel {

    private final JTextField populationSizeField;
    private final JTextField generationLimitField;
    private final JTextField steadyFitnessLimitField;
    private final JTextField mutationProbabilityField;
    private final JTextField crossoverProbabilityField;
    private final JLabel fileLabel;
    private File dataModelFile;

    public MainPanel() {
        setLayout(new BorderLayout());

        populationSizeField = new JTextField("100000");
        generationLimitField = new JTextField("1000");
        steadyFitnessLimitField = new JTextField("50");
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

        JButton runAlgorithmButton = new JButton("Uruchom Algorytm");
        runAlgorithmButton.addActionListener(e -> runAlgorithm());
        runAlgorithmButton.setPreferredSize(new Dimension(200, 50));

        add(filePanel, BorderLayout.NORTH);
        add(paramPanel, BorderLayout.CENTER);
        add(runAlgorithmButton, BorderLayout.SOUTH);
    }

    private void showDataModelFrame(DataModelFrame dataModelFrame) {
        dataModelFrame.setSize(400, 400);
        dataModelFrame.setVisible(true);
    }

    private void runAlgorithm() {
        if (dataModelFile == null) {
            JOptionPane.showMessageDialog(this, "Proszę wybrać plik z modelem danych.");
            return;
        }

        DataModel dataModel = DeserializeDataModel.loadDataModelFromFile(dataModelFile.getAbsolutePath());
        RouteOptimizer routeOptimizer = new RouteOptimizer(dataModel);

        GeneticAlgorithmConfig config = routeOptimizer.getConfig();
        config.setPopulationSize(Integer.parseInt(populationSizeField.getText()));
        config.setGenerationLimit(Integer.parseInt(generationLimitField.getText()));
        config.setSteadyFitnessGenerationLimit(Integer.parseInt(steadyFitnessLimitField.getText()));
        config.setMutationProbability(Double.parseDouble(mutationProbabilityField.getText()));
        config.setCrossoverProbability(Double.parseDouble(crossoverProbabilityField.getText()));

        routeOptimizer.start();
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/data"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Pliki modelu danych", "ser"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            dataModelFile = fileChooser.getSelectedFile();
            fileLabel.setText("Wybrano plik: " + dataModelFile.getName());
        }
    }
}