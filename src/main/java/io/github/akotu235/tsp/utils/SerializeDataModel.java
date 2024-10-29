package io.github.akotu235.tsp.utils;

import io.github.akotu235.tsp.model.DataModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeDataModel {
    public static void saveDataModelToFile(DataModel dataModel, String filename) {
        filename += ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(dataModel);
        } catch (IOException e) {
            throw new RuntimeException("Wystąpił błąd podczas zapisywania obiektu do pliku: " + filename, e);
        }
    }
}
