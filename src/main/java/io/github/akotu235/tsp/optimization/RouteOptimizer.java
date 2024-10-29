package io.github.akotu235.tsp.optimization;

import io.github.akotu235.tsp.configuration.GeneticAlgorithmConfig;
import io.github.akotu235.tsp.model.CostMatrix;
import io.github.akotu235.tsp.model.DataModel;
import io.github.akotu235.tsp.model.Node;
import io.github.akotu235.tsp.model.Route;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class RouteOptimizer {
    private final DataModel dataModel;

    public RouteOptimizer(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public Route findBestRoute() {
        // Definicja fabryki chromosomów (ścieżek)
        Genotype<EnumGene<Integer>> genotypeFactory = Genotype.of(PermutationChromosome.ofInteger(dataModel.getNodeCount()));

        // Funkcja oceny fitness (minimalizacja długości trasy)
        Function<Genotype<EnumGene<Integer>>, Double> fitnessFunction = genotype -> calculateRouteLength(genotype, dataModel.getCostMatrix());

        // Konfiguracja i uruchomienie silnika genetycznego
        Engine<EnumGene<Integer>, Double> engine = Engine.builder(fitnessFunction, genotypeFactory)
                .populationSize(GeneticAlgorithmConfig.POPULATION_SIZE)
                .optimize(Optimize.MINIMUM)
                .alterers(
                        new SwapMutator<>(GeneticAlgorithmConfig.MUTATION_PROBABILITY),
                        new PartiallyMatchedCrossover<>(GeneticAlgorithmConfig.PMX_CROSSOVER_PROBABILITY)
                )
                .build();

        // Uruchomienie ewolucji
        Phenotype<EnumGene<Integer>, Double> result = engine.stream()
                .limit(Limits.bySteadyFitness(GeneticAlgorithmConfig.STEADY_FITNESS_GENERATION_LIMIT))
                .limit(GeneticAlgorithmConfig.GENERATION_LIMIT)
                .peek(this::printBestOfGeneration)
                .collect(EvolutionResult.toBestPhenotype());

        // Zwrócenie najlepszego rozwiązania
        return convertPhenotypeToRoute(result);
    }

    //Funkcja fitness
    private static double calculateRouteLength(Genotype<EnumGene<Integer>> genotype, CostMatrix costMatrix) {
        int[] route = genotype.chromosome().stream()
                .mapToInt(EnumGene::allele)
                .toArray();

        double distance = 0;
        for (int i = 0; i < route.length - 1; i++) {
            distance += costMatrix.getCost(route[i], route[i + 1]);
        }
        distance += costMatrix.getCost(route[route.length - 1], route[0]); // powrót do miasta początkowego

        return distance;
    }

    private Route convertPhenotypeToRoute(Phenotype<EnumGene<Integer>, Double> phenotype) {
        List<Node> nodes = new ArrayList<>();

        phenotype.genotype().chromosome().stream()
                .map(EnumGene::allele)
                .map(dataModel::getNodeById)
                .forEach(nodes::add);

        return new Route(nodes, phenotype.fitness(), dataModel.getCostUnit());
    }

    private void printBestOfGeneration(EvolutionResult<EnumGene<Integer>, Double> result) {
        Route route = convertPhenotypeToRoute(result.bestPhenotype());
        System.out.printf("Generation: %d\n%s\n", result.generation(), route);
    }
}