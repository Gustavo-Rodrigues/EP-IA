import java.util.ArrayList;
import java.util.Collections;

public interface SimulatedAnnealing{
    public double acceptanceProbability(int energy, int newEnergy, double temperature);
    public void simulatedAnnealing(Set set);
}
