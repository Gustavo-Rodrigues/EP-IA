class EP{
    public static void main(String args[]){
        Stopwatch s = new Stopwatch();
		s.start();
        //reads the file and get some information
        Init initialize = new Init("/home/gustavo/Documents/EP-IA/A-n32-k5.vrp");
        initialize.init_distance();
        //Node nodes[];
        //nodes = initialize.getNodes();
        /*
        System.out.println("DEMANDS:");
        for(int x = 0;x<initialize.getMaxNodes();x++){
            System.out.println(x + ": " + nodes[x].getDemand());
        }

        for(int i = 0; i< initialize.getMaxNodes(); i++){
            int m[] = nodes[i].getDistances();
            System.out.print(i +": ");
            for(int j = 0;j<initialize.getMaxNodes();j++){
                System.out.print(m[j] + " ");
            }
            System.out.println();
        }
        */

        Set set = new Set(initialize.getNodes(), initialize.getCapacity());
        set.clarkeWright();
        System.out.println("NEW METHOD: ");
        NewSimulatedAnnealing nsa = new NewSimulatedAnnealing();
            for(int r = 0; r<=50; r++){
            System.out.println(r);
            nsa.simulatedAnnealing(set,500,0.03,500);
        }
        /*
        System.out.println("OLD METHOD: ");
        SimulatedAnnealing sa1 = new SimulatedAnnealing();
        for(int r = 0; r<=50; r++){
            System.out.println(r);
            sa1.simulatedAnnealing(set,500,0.03,2500);
        }
        */
        //Centroid centroid = new Centroid(initialize.getNodes(), initialize.getCapacity());
        //centroid.construction();
        //centroid.adjustment(centroid.construction());
        //centroid.algorithm();
        s.stop();
		System.out.println(s.toString());
    }
}
