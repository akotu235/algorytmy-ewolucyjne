package io.github.akotu235.tsp.utils;

import io.github.akotu235.tsp.model.DataModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeDataModel {
    public static void saveDataModelToFile(DataModel dataModel, String path) {
        if (!path.endsWith(".ser")) {
            path += ".ser";
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(dataModel);
            System.out.println("Obiekt z danymi został zapisany.");
        } catch (IOException e) {
            throw new RuntimeException("Wystąpił błąd podczas zapisywania obiektu: " + path, e);
        }
    }
}
