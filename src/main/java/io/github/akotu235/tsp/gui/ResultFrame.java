package io.github.akotu235.tsp.gui;

import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame {

    public ResultFrame(String resultText) {
        setTitle("Result");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JTextArea resultTextArea = new JTextArea(resultText);
        resultTextArea.setEditable(false);
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        resultTextArea.setOpaque(false);
        resultTextArea.setBorder(null);
        resultTextArea.setBorder(BorderFactory.createTitledBorder("Wynik"));

        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }

    public static void showResult(String result) {
        SwingUtilities.invokeLater(() -> new ResultFrame(result));
    }
}