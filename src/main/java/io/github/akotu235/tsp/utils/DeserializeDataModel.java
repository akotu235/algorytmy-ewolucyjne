package io.github.akotu235.tsp.utils;

import io.github.akotu235.tsp.model.DataModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeserializeDataModel {
    public static DataModel loadDataModelFromFile(String filename) {
        filename += ".ser";
        DataModel dataModel;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            dataModel = (DataModel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Wystąpił błąd podczas odczytywania obiektu z pliku: " + filename, e);
        }
        return dataModel;
    }
}
