class EP{
    public static void main(String args[]){
        Stopwatch s = new Stopwatch();
		s.start();
        //reads the file and get some information
        Init initialize = new Init("/home/gustavo/Documents/EP-IA/P-n16-k8.vrp");
        initialize.init_distance();
        Node nodes[];
        nodes = initialize.getNodes();
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
        //set.createSet();
        set.clarkeWright();
        System.out.println(set);
        SimulatedAnnealing sa1 = new SimulatedAnnealing();
        sa1.simulatedAnnealing(set,100,0.01,2500);
        s.stop();
		System.out.println(s.toString());
    }
}
