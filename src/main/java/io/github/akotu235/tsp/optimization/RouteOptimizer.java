package io.github.akotu235.tsp.optimization;

import io.github.akotu235.tsp.chart.Chart;
import io.github.akotu235.tsp.configuration.GeneticAlgorithmConfig;
import io.github.akotu235.tsp.gui.ResultFrame;
import io.github.akotu235.tsp.model.CostMatrix;
import io.github.akotu235.tsp.model.DataModel;
import io.github.akotu235.tsp.model.Node;
import io.github.akotu235.tsp.model.Route;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.function.Function;


public class RouteOptimizer extends Thread {
    private final DataModel dataModel;
    private final GeneticAlgorithmConfig config;
    private final Chart chart;

    public RouteOptimizer(DataModel dataModel) {
        this.dataModel = dataModel;
        this.config = new GeneticAlgorithmConfig();
        this.chart = new Chart(this);
    }

    @Override
    public void run() {
        // Definicja fabryki chromosomów (ścieżek)
        Genotype<EnumGene<Integer>> genotypeFactory = Genotype.of(PermutationChromosome.ofInteger(dataModel.getNodeCount()));

        // Funkcja oceny fitness (minimalizacja długości trasy)
        Function<Genotype<EnumGene<Integer>>, Double> fitnessFunction = genotype -> calculateRouteLength(genotype, dataModel.getCostMatrix());

        // Konfiguracja silnika genetycznego
        Engine<EnumGene<Integer>, Double> engine = Engine.builder(fitnessFunction, genotypeFactory)
                .populationSize(config.getPopulationSize())
                .optimize(Optimize.MINIMUM)
                .alterers(
                        new SwapMutator<>(config.getMutationProbability()),
                        new PartiallyMatchedCrossover<>(config.getCrossoverProbability())
                )
                .build();

        try {
            // Uruchomienie ewolucji
            Phenotype<EnumGene<Integer>, Double> result = engine.stream()
                    .limit(Limits.bySteadyFitness(config.getSteadyFitnessGenerationLimit()))
                    .limit(config.getGenerationLimit())
                    .peek(this::printBestOfGeneration)
                    .peek(this::updateChart)
                    .collect(EvolutionResult.toBestPhenotype());

            // Wyświetlenie najlepszego rozwiązania
            System.out.println(convertPhenotypeToRoute(result));
            ResultFrame.showResult(convertPhenotypeToRoute(result).toString());

            //Zapis wykresu do pliku
            chart.saveToFile();
        } catch (CancellationException e) {
            System.out.println("Evolution was cancelled.");
        }
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

    private void updateChart(EvolutionResult<EnumGene<Integer>, Double> result) {
        double averageFitness = result.population().stream()
                .mapToDouble(Phenotype::fitness)
                .average().
                orElseThrow();
        SwingUtilities.invokeLater(() -> {
            chart.updateChart(result.generation(), result.bestFitness(), averageFitness, result.worstFitness());
        });
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

    public GeneticAlgorithmConfig getConfig() {
        return config;
    }
}