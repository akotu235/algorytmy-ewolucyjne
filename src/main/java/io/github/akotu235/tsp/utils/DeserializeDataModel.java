package io.github.akotu235.tsp.utils;

import io.github.akotu235.tsp.model.DataModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;

public class DeserializeDataModel {
    public static DataModel loadDataModelFromFile(String path) {
        String filePath = Paths.get(path).toString();
        DataModel dataModel;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            dataModel = (DataModel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Wystąpił błąd podczas odczytywania obiektu: " + filePath, e);
        }
        return dataModel;
    }
}
