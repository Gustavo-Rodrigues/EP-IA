import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;
import java.util.Scanner;


class SimulatedAnnealing{
    public double acceptanceProbability(int energy, int newEnergy, double temperature){
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }

    public Route twoOpt(Route r){
        // Get tour size
        Route route = r.copyRoute();
        int size = r.routeSize();
        int minI = -1;
        int minJ = -1;
        int minChange = 0;
        for ( int i = 1; i < size - 3; i++ ){
            //if(i+1 > size) break;
            for ( int j = i + 2; j< size-1; j++) {
                //dist(i,j) + dist(i+1,j+1) - dist(i,i+1) - dist(j,j+1)
                // route 1: dist(i,j) + dist(i+1,j+1)
                // route 2: dist(i,i+1) - dist(j,j+1)
                //if(j+1 > size || i+1 > size) break;

                //Route route1 = new Route();
                //route1.addNode(r.getNode(i));
                //route1.addNode(r.getNode(j));
                //route1.addNode(r.getNode(i+1));
                //route1.addNode(r.getNode(j+1));

                //int distance1 = r.getNode(i).distanceTo(r.getNode(j).getId()-1) +
                //r.getNode(j).distanceTo(r.getNode(i+1).getId()-1) + r.getNode(i+1).distanceTo(r.getNode(j+1).getId()-1);
                int distance1 = r.getNode(i).distanceTo(r.getNode(j).getId()-1) + r.getNode(i+1).distanceTo(r.getNode(j+1).getId()-1);

                //Route route2 = new Route();
                //route2.addNode(r.getNode(i));
                //route2.addNode(r.getNode(i+1));
                //route2.addNode(r.getNode(j));
                //route2.addNode(r.getNode(j+1));

                //int distance2 = r.getNode(i).distanceTo(r.getNode(i+1).getId()-1) +
                //r.getNode(i+1).distanceTo(r.getNode(j).getId()-1) + r.getNode(j).distanceTo(r.getNode(j+1).getId()-1);
                int distance2 = r.getNode(i).distanceTo(r.getNode(i+1).getId()-1) + r.getNode(j).distanceTo(r.getNode(j+1).getId()-1);

                //int better = route1.getDistance() - route2.getDistance();
                int better = distance1 - distance2;
                if ( better < minChange){
                    minChange = better;
                    minI = i;
                    minJ = j;
                }
            }
        }
        if(minI != -1 && minJ != -1){
            route.addNode(minI+1,r.getNode(minJ));
            route.addNode(minI+1+1,r.getNode(minJ+1));
            route.removeNode(minJ+2);
            route.removeNode(minJ+2);
        }
        return route;
    }

    public Set twoOpt(Set set){
        Set newSet = set.copySet();
        int setSize = newSet.setSize();
        int minRoute1 = -1;
        int minRoute2 = -1;
        int minI = -1;
        int minJ = -1;
        int minChange = 0;
        for(int g = 0; g<setSize; g++ ){
            for(int h = g+1; h<setSize; h++){
                for ( int i = 1; i < set.getRoute(g).routeSize()-1; i++ ){
                    //if(i+1 > size) break;
                    for ( int j = 1; j<set.getRoute(h).routeSize()-1; j++) {
                        // g = k
                        // h = k'
                        // route 1: dist(i k,j+1 k') + dist(j k',i+1 k)
                        // route 2: dist(i k,i+1 k) - dist(j k',j+1 k')
                        //if(j+1 > size || i+1 > size) break;

                        int distance1 =
                        set.getRoute(g).getNode(i).distanceTo(set.getRoute(h).getNode(j+1).getId()-1) +
                        set.getRoute(h).getNode(j).distanceTo(set.getRoute(g).getNode(i+1).getId()-1);

                        int distance2 =
                        set.getRoute(g).getNode(i).distanceTo(set.getRoute(g).getNode(i+1).getId()-1) +
                        set.getRoute(h).getNode(j).distanceTo(set.getRoute(h).getNode(j+1).getId()-1);

                        int better = distance1 - distance2;
                        if ( better < minChange){
                            if(set.getRoute(g).getCapacity() - set.getRoute(g).getNode(i+1).getDemand() + set.getRoute(h).getNode(j+1).getDemand() <
                            set.getMaximumCapacity() &&
                            set.getRoute(h).getCapacity() - set.getRoute(h).getNode(j+1).getDemand() + set.getRoute(g).getNode(i+1).getDemand() <
                            set.getMaximumCapacity()){
                                //it's feasible UHUUU
                                minChange = better;
                                minRoute1 = g;
                                minRoute2 = h;
                                minI = i;
                                minJ = j;
                            }
                        }
                    }
                }
            }
        }
        if(minI != -1 && minJ != -1 && minRoute1 != -1 && minRoute2 != -1){
            Route route1 = newSet.getRoute(minRoute1);
            Route route2 = newSet.getRoute(minRoute2);
            route1.addNode(minI+1,route2.getNode(minJ+1));
            route2.addNode(minJ+1,route1.getNode(minI+1+1));
            route1.removeNode(minI+2);
            route2.removeNode(minJ+2);

            newSet.setRoute(minRoute1, route1);
            newSet.setRoute(minRoute2, route2);
        }
        return newSet;
    }

    public void simulatedAnnealing(Set set, double temperature, double coolingRate, int maxIter){
        System.out.println("Starting");
        ArrayList<Integer> solutions = new ArrayList<Integer>();
        Set currentSolution = set.copySet();
        System.out.println("Initial solution distance: " + currentSolution.getDistance());

        // Set as current best
        Set best = currentSolution.copySet();
        System.out.println(best);
        int improvement = Integer.MAX_VALUE;

        // Loop until system has cooled
        while (temperature > 1) {
            //Create new neighbour set
            Set newSolution = currentSolution.copySet();
            //System.out.println("OUR BELOVED RANDOM:"+ (double) (1.0/3.0));
            double r;
            int iter = 0;
            while(iter < maxIter){
                r = Math.random();
                //if(newSolution.getDistance() < improvement && temperature != 1000) improvement = newSolution.getDistance();
                //System.out.println(newSolution.getDistance());
                //System.out.println(newSolution);
                if(r<=(double) (1.0/3.0)){
                    //System.out.println("--------------------------------------------------------------------->1");
                    //swap
                    boolean validSolution = false;
                    while(!validSolution){
                        //Get a random positions in the set
                        int setPos1 = (int) ( newSolution.setSize() * Math.random());
                        int setPos2 = (int) ( newSolution.setSize() * Math.random());
                        //Get random positions in the route
                        int routePos1 = (int) ( ((newSolution.getRoute(setPos1).routeSize()-1)) * Math.random())+1;
                        int routePos2 = (int) ( ((newSolution.getRoute(setPos2).routeSize()-1)) * Math.random())+1;
                        //get the routes
                        Route route1 = newSolution.getRoute(setPos1);
                        Route route2 = newSolution.getRoute(setPos2);
                        //get the nodes
                        Node node1 = route1.getNode(routePos1);
                        Node node2 = route2.getNode(routePos2);
                        // Swap them
                        if( (route1.getCapacity() - node1.getDemand() + node2.getDemand() <= currentSolution.getMaximumCapacity()) &&
                        (route2.getCapacity() - node2.getDemand() + node1.getDemand() <= currentSolution.getMaximumCapacity()) ){
                            //System.out.println("Node1: "+ node1.getId() + " " + "Node2: " + node2.getId());
                            if(setPos1 != setPos2){
                                route1.setNode(routePos1, node2);
                                route2.setNode(routePos2, node1);
                                newSolution.setRoute(setPos1,route1);
                                newSolution.setRoute(setPos2,route2);
                                validSolution = true;
                            }
                        }
                    }
                }

                else if((double) (1.0/3.0)>r && r<=(double) (2.0/3.0)){
                    //System.out.println("--------------------------------------------------------------------->2");
                    //random insertion
                    boolean validSolution = false;
                    while(!validSolution){
                        //Get a random positions in the set
                        int setPos1 = (int) ( newSolution.setSize() * Math.random());
                        int setPos2 = (int) ( newSolution.setSize() * Math.random());

                        //Get a random position in the route
                        int routePos1 = (int) ( ((newSolution.getRoute(setPos1).routeSize()-1)) * Math.random())+1;

                        //get the routes
                        Route route1 = newSolution.getRoute(setPos1);
                        Route route2 = newSolution.getRoute(setPos2);
                        //get the node

                        Node node1 = route1.getNode(routePos1);
                        //try do add in the other route
                        if(route2.getCapacity()+node1.getDemand() <= currentSolution.getMaximumCapacity() && setPos1 != setPos2){
                            if(route1.routeSize() > 2){
                                route1.removeNode(routePos1);
                                route2.addNode(node1);
                                newSolution.setRoute(setPos1,route1);
                                newSolution.setRoute(setPos2,route2);
                                validSolution = true;
                            }else if(route1.routeSize() == 2){
                                Set newSet = newSolution.copySet();
                                route2.addNode(node1);
                                newSet.setRoute(setPos2,route2);
                                newSet.removeRoute(setPos1);
                                newSolution = newSet.copySet();
                                validSolution = true;
                            }
                        }
                    }
                }
                else if((double) (2.0/3.0)>r && r<= (double) (1.0)){
                    //System.out.println("--------------------------------------------------------------------->3");
                    //random 2-opt
                    boolean validSolution = false;
                    while(!validSolution){
                        //Get a random positions in the set
                        int setPos1 = (int) ( newSolution.setSize() * Math.random());
                        //Get random positions in the route
                        int routePos1 = (int) ( ((newSolution.getRoute(setPos1).routeSize()-1)) * Math.random())+1;
                        int routePos2 = (int) ( ((newSolution.getRoute(setPos1).routeSize()-1)) * Math.random())+1;
                        //get the routes
                        Route route1 = newSolution.getRoute(setPos1);
                        //Route route2 = newSolution.getRoute(setPos2);
                        //get the nodes
                        Node node1 = route1.getNode(routePos1);
                        Node node2 = route1.getNode(routePos2);
                        // Swap them
                        if(routePos1 != routePos2){
                            Route newRoute = newSolution.getRoute(setPos1);
                            Route case1 = twoOpt(newRoute);
                            newRoute.setNode(routePos1, node2);
                            newRoute.setNode(routePos2, node1);
                            Route case2 = twoOpt(newRoute);
                            if(case1.getDistance() < case2.getDistance()){
                                newSolution.setRoute(setPos1,case1);
                            }else newSolution.setRoute(setPos1,case2);
                            validSolution = true;
                        }
                    }
                    /*
                    for(int i = 0; i<newSolution.setSize();i++){
                        Route routeX = newSolution.getRoute(i);
                        Route omegaRoute = twoOpt(routeX);
                        if(omegaRoute.getDistance() < routeX.getDistance()){
                            newSolution.setRoute(i,omegaRoute);
                        }
                    }
                    */
                }
                //System.out.println(newSolution);
                newSolution = twoOpt(newSolution);
                // Get energy of solutions
                int currentEnergy = currentSolution.getDistance();
                int neighbourEnergy = newSolution.getDistance();
                //System.out.println("CURRENT ENERGY: " +currentEnergy );
                //System.out.println("NEIGHBOUR ENERGY: " +neighbourEnergy );
                //solutions.add(neighbourEnergy);
                // Keep track of the best solution found
                if (currentSolution.getDistance() < best.getDistance()) {
                    best = currentSolution.copySet();
                    //System.out.println(best);
                    //System.out.println(best.getDistance());
                }
                // Decide if we should accept the neighbour
                if (acceptanceProbability(currentEnergy, neighbourEnergy, temperature) > Math.random()) {
                    currentSolution = newSolution.copySet();
                }
                iter++;
            }
            // Cool system
            temperature *= 1-coolingRate;
            //Perform Local search based on swap operation on X best
            //2-opt
            //currentSolution = twoOpt(best);
        }
    System.out.println(best);
    System.out.println(best.getDistance());
    //System.out.println(improvement);
    }
}
