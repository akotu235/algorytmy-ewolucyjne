package io.github.akotu235.tsp;

import io.github.akotu235.tsp.model.DataModel;
import io.github.akotu235.tsp.model.Route;
import io.github.akotu235.tsp.optimization.RouteOptimizer;
import io.github.akotu235.tsp.utils.DeserializeDataModel;

public class TravelingSalesman {
    public static void main(String[] args) {
        DataModel dataModel = DeserializeDataModel.loadDataModelFromFile("EuroTrip");
        RouteOptimizer routeOptimizer = new RouteOptimizer(dataModel);
        Route route = routeOptimizer.findBestRoute();
        System.out.println("Best route: " + route);
    }
}