package io.github.akotu235.tsp.optimization;

import io.github.akotu235.tsp.chart.Chart;
import io.github.akotu235.tsp.configuration.GeneticAlgorithmConfig;
import io.github.akotu235.tsp.gui.FrameLocationManager;
import io.github.akotu235.tsp.model.*;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;

import javax.swing.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.function.Function;


public class RouteOptimizer extends Thread {
    private final DataModel dataModel;
    private final GeneticAlgorithmConfig config;
    private final Chart chart;
    private final Results results;
    private Future<?> routeOptimizerHandle;

    public RouteOptimizer(DataModel dataModel, GeneticAlgorithmConfig config, Results results, FrameLocationManager frameLocationManager) {
        this.dataModel = dataModel;
        this.config = config;
        this.results = results;
        this.chart = new Chart(this, dataModel.getCostUnit(), frameLocationManager);
    }

    @Override
    public void run() {
        // Definicja fabryki chromosomów (ścieżek)
        Genotype<EnumGene<Integer>> genotypeFactory = Genotype.of(PermutationChromosome.ofInteger(dataModel.getNodeCount()));

        // Funkcja oceny fitness (minimalizacja długości trasy)
        Function<Genotype<EnumGene<Integer>>, Double> fitnessFunction = genotype -> calculateRouteLength(genotype, dataModel.getCostMatrix());

        // Konfiguracja silnika genetycznego
        Engine<EnumGene<Integer>, Double> engine = Engine.builder(fitnessFunction, genotypeFactory)
                .populationSize(config.populationSize())
                .optimize(Optimize.MINIMUM)
                .alterers(
                        new SwapMutator<>(config.mutationProbability()),
                        new PartiallyMatchedCrossover<>(config.crossoverProbability())
                )
                .build();

        //Włączenie wykresu
        chart.open();

        //Rozpoczęcie pomiaru czasu
        Instant start = Instant.now();

        try {
            // Uruchomienie ewolucji
            Phenotype<EnumGene<Integer>, Double> result = engine.stream()
                    .limit(Limits.bySteadyFitness(config.steadyFitnessGenerationLimit()))
                    .limit(config.generationLimit())
                    .peek(this::updateChart)
                    .collect(EvolutionResult.toBestPhenotype());

            //Zakończenie pomiaru czasu
            Instant end = Instant.now();

            //Dodanie rezultatu
            results.addResult(new Result(convertPhenotypeToRoute(result), Duration.between(start, end), result.generation()));

        } catch (CancellationException e) {
            chart.close();
        }

        if (config.closeCharts()) {
            chart.close();
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
        SwingUtilities.invokeLater(() -> chart.updateChart(result.generation(), result.bestFitness(), averageFitness, result.worstFitness()));
    }

    private Route convertPhenotypeToRoute(Phenotype<EnumGene<Integer>, Double> phenotype) {
        List<Node> nodes = new ArrayList<>();

        phenotype.genotype().chromosome().stream()
                .map(EnumGene::allele)
                .map(dataModel::getNodeById)
                .forEach(nodes::add);

        return new Route(nodes, phenotype.fitness(), dataModel.getCostUnit());
    }

    public void setRouteOptimizerHandle(Future<?> routeOptimizerHandle) {
        this.routeOptimizerHandle = routeOptimizerHandle;
    }

    public Chart getChart() {
        return chart;
    }

    @Override
    public void interrupt() {
        super.interrupt();
        routeOptimizerHandle.cancel(true);
    }
}