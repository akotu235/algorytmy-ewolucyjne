package io.github.akotu235.tsp.gui;

import io.github.akotu235.tsp.initialization.ProblemSetup;
import io.github.akotu235.tsp.model.DataModel;
import io.github.akotu235.tsp.utils.SerializeDataModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class DataModelPanel extends JPanel {
    private final JTextArea cityInputArea;
    private final JTextField fileNameField;
    private final JTextArea locationTextArea;
    private File selectedDirectory;

    public DataModelPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Utwórz model danych"));

        selectedDirectory = new File(System.getProperty("user.dir") + "/data");
        if (!selectedDirectory.exists()) {
            selectedDirectory.mkdirs();
        }

        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filePanel.setBorder(BorderFactory.createTitledBorder("Plik"));
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));

        JPanel locationPanel = new JPanel();
        locationPanel.setBorder(BorderFactory.createTitledBorder("Lokalizacja"));
        locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));
        locationTextArea = new JTextArea(selectedDirectory.getAbsolutePath());
        locationTextArea.setLineWrap(true);
        locationTextArea.setEditable(false);
        locationTextArea.setOpaque(false);
        locationTextArea.setBorder(null);
        JScrollPane location = new JScrollPane(locationTextArea);
        location.setBorder(null);
        locationPanel.add(location);
        JButton chooseDirectoryButton = new JButton("Zmień lokalizację");
        chooseDirectoryButton.addActionListener(e -> chooseDirectory());
        locationPanel.add(chooseDirectoryButton);
        filePanel.add(locationPanel);


        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(1, 1));
        namePanel.setBorder(BorderFactory.createTitledBorder("Nazwa"));
        fileNameField = new JTextField("nowy_model_danych", 25);
        namePanel.add(fileNameField);
        filePanel.add(namePanel);

        cityInputArea = new JTextArea();
        cityInputArea.setRows(7);
        cityInputArea.setLineWrap(true);
        cityInputArea.setWrapStyleWord(true);
        JScrollPane cityScrollPane = new JScrollPane(cityInputArea);
        cityScrollPane.setBorder(BorderFactory.createTitledBorder("Miasta"));

        JButton createDataModelButton = new JButton("Utwórz dane");
        createDataModelButton.addActionListener(e -> createDataModel());

        add(filePanel);
        add(cityScrollPane);
        add(createDataModelButton);
    }

    private void chooseDirectory() {
        JFileChooser directoryChooser = new JFileChooser(selectedDirectory);
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = directoryChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedDirectory = directoryChooser.getSelectedFile();
            locationTextArea.setText(selectedDirectory.getAbsolutePath());
        }
    }

    private void createDataModel() {
        String cityInput = cityInputArea.getText().trim();
        String fileName = fileNameField.getText().trim();

        if (cityInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Proszę podać przynajmniej jedno miasto.");
            return;
        }

        if (selectedDirectory == null || fileName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Proszę wybrać katalog oraz podać nazwę pliku.");
            return;
        }

        File dataModelFile = new File(selectedDirectory, fileName);
        List<String> cities = List.of(cityInput.replaceAll("[\\n;]", ",").split(",\\s*"));

        ProblemSetup setup = new ProblemSetup(cities);
        DataModel dataModel = setup.getDataModel();
        SerializeDataModel.saveDataModelToFile(dataModel, dataModelFile.getAbsolutePath());

        JOptionPane.showMessageDialog(this, "Model danych zapisano w: " + dataModelFile.getAbsolutePath());
    }
}