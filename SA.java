public interface SA{
    public double acceptanceProbability(int energy, int newEnergy, double temperature);
    public Set simulatedAnnealing(Set set, double temperature, double coolingRate, int maxIter);
    public Set twoOpt(Set set);
}
