import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

class SimulatedAnnealingCase1 implements SimulatedAnnealing{
    public double acceptanceProbability(int energy, int newEnergy, double temperature){
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
    public void simulatedAnnealing(Set set){
        int count = 0;
        ArrayList<Integer> solutions = new ArrayList<Integer>();
        // Set initial temp
        double temp = 10000;

        Set currentSolution = set.copySet();

        //Cooling rate
        double coolingRate = 0.003;

        System.out.println("Initial solution distance: " + currentSolution.getDistance());

        // Set as current best
        Set best = currentSolution.copySet();
        System.out.println(best);


        // Loop until system has cooled
        while (temp > 1) {
            //Create new neighbour set
            Set newSolution = currentSolution.copySet();

            //Get a random positions in the set
            int setPos1 = (int) (newSolution.setSize() * Math.random());
            int setPos2 = (int) (newSolution.setSize() * Math.random());

            //Get random positions in the route
            int routePos1 = (int) (newSolution.getRoute(setPos1).routeSize() * Math.random());
            int routePos2 = (int) (newSolution.getRoute(setPos2).routeSize() * Math.random());

            //get the routes
            Route route1 = newSolution.getRoute(setPos1);
            Route route2 = newSolution.getRoute(setPos2);

            //get the nodes
            Node node1 = route1.getNode(routePos1);
            Node node2 = route2.getNode(routePos2);

            // Swap them
            if( (route1.getCapacity() - node1.getDemand() + node2.getDemand() < currentSolution.getMaximumCapacity()) && (
route2.getCapacity() - node2.getDemand() + node1.getDemand() < currentSolution.getMaximumCapacity()) ){
                //System.out.println("Node1: "+ node1.getId() + " " + "Node2: " + node2.getId());
                if(setPos1 == setPos2){
                    Route route3 = newSolution.getRoute(setPos1);
                    route3.setNode(routePos1, node2);
                    route3.setNode(routePos2, node1);
                    newSolution.setRoute(setPos1,route3);
                }else{
                    route1.setNode(routePos1, node2);
                    route2.setNode(routePos2, node1);
                    newSolution.setRoute(setPos1,route1);
                    newSolution.setRoute(setPos2,route2);
                }

                //System.out.println("setPos1: " +setPos1+ " " + "setPos2: "+setPos2);
                //System.out.println("routePos1: " +routePos1+ " " + "routePos2: "+routePos2);
                //System.out.println("NEW SOLUTION:");
                //System.out.println(newSolution);
                //System.out.println();
                //System.out.println("CURRENT SOLUTION:");
                //System.out.println(currentSolution);
                //System.out.println();
                //if(count == 3) break;
            }else{
                continue;
            }

            count++;
            //System.out.println(newSolution);
            //System.out.println();
            // Get energy of solutions
            int currentEnergy = currentSolution.getDistance();
            int neighbourEnergy = newSolution.getDistance();
            //System.out.println("CURRENT ENERGY: " +currentEnergy );
            //System.out.println("NEIGHBOUR ENERGY: " +neighbourEnergy );
            //solutions.add(neighbourEnergy);

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = newSolution.copySet();
            }

            // Keep track of the best solution found
            if (currentSolution.getDistance() < best.getDistance()) {
                best = currentSolution.copySet();
                //System.out.println(best);
                //System.out.println(best.getDistance());
            }

            // Cool system
            temp *= 1-coolingRate;

        }
    System.out.println(best);
    System.out.println(best.getDistance());
    //System.out.println(solutions);
    }
}
