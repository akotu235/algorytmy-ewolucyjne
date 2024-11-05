package io.github.akotu235.tsp.configuration;

public class GeneticAlgorithmConfig {
    private int populationSize = 100_000;
    private int generationLimit = 1_000;
    private int steadyFitnessGenerationLimit = 50;
    private double mutationProbability = 0.3;
    private double crossoverProbability = 0.3;

    public GeneticAlgorithmConfig() {
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getGenerationLimit() {
        return generationLimit;
    }

    public void setGenerationLimit(int generationLimit) {
        this.generationLimit = generationLimit;
    }

    public int getSteadyFitnessGenerationLimit() {
        return steadyFitnessGenerationLimit;
    }

    public void setSteadyFitnessGenerationLimit(int steadyFitnessGenerationLimit) {
        this.steadyFitnessGenerationLimit = steadyFitnessGenerationLimit;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }
}