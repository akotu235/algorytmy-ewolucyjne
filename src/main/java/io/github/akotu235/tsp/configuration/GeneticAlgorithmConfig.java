package io.github.akotu235.tsp.configuration;

public record GeneticAlgorithmConfig(int executionCount, int threadPoolSize, int populationSize, int generationLimit,
                                     int steadyFitnessGenerationLimit, double mutationProbability,
                                     double crossoverProbability) {
}