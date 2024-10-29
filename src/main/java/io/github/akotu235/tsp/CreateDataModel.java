package io.github.akotu235.tsp;

import io.github.akotu235.tsp.initialization.ProblemSetup;
import io.github.akotu235.tsp.model.DataModel;
import io.github.akotu235.tsp.utils.SerializeDataModel;

import java.util.List;

public class CreateDataModel {
    public static void main(String[] args) {
        String dataModelFilename = "EuroTrip";
        List<String> cities = List.of("Rzym", "Sofia", "Berlin", "Zagrzeb", "Madryt", "Budapeszt", "Warszawa", "Praga", "Ateny", "Wiedeń", "Wilno", "Bratysława", "Ryga", "Lublana", "Bukareszt", "Sztokholm", "Helsinki", "Bruksela", "Tallinn", "Amsterdam", "Dublin", "Nikozja", "Paryż", "Kopenhaga", "Lizbona", "Luksemburg", "Valletta");

        ProblemSetup setup = new ProblemSetup(cities);
        DataModel dataModel = setup.getDataModel();
        SerializeDataModel.saveDataModelToFile(dataModel, dataModelFilename);
    }
}