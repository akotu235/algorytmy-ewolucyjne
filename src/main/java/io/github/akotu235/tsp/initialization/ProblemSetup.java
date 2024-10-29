package io.github.akotu235.tsp.initialization;

import io.github.akotu235.tsp.model.DataModel;
import io.github.akotu235.tsp.model.geo.City;
import io.github.akotu235.tsp.service.DistanceService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemSetup {
    private final DataModel dataModel;
    private final DistanceService distanceService;

    public ProblemSetup(List<String> cities) {
        this.distanceService = new DistanceService();
        this.dataModel = new DataModel(cities.size(), "km");

        List<City> cityNodes = createCityNodes(cities);
        cityNodes.forEach(dataModel::addNode);
        populateCostMatrix(cityNodes);
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    private void populateCostMatrix(List<City> cityNodes) {
        int size = cityNodes.size();
        IntStream.range(0, size).parallel().forEach(i -> {
            for (int j = i + 1; j < size; j++) {
                double distance = distanceService.getDistance(cityNodes.get(i).getCoordinates(), cityNodes.get(j).getCoordinates());
                dataModel.setCost(i, j, distance);
                dataModel.setCost(j, i, distance);
            }
        });
    }

    private static List<City> createCityNodes(List<String> cities) {
        return IntStream.range(0, cities.size())
                .mapToObj(i -> new City(i, cities.get(i)))
                .collect(Collectors.toList());
    }
}
