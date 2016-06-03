public interface SA{
    public double acceptanceProbability(int energy, int newEnergy, double temperature);
    public void simulatedAnnealing(Set set, double temperature, double coolingRate, int maxIter);
    public Route twoOpt(Route r);
    public Set twoOpt(Set set);
}
