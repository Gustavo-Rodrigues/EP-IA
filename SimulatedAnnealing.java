class SimulatedAnnealing{

    public void simulatedAnnealing(){

    }

    public static void main(String args[]){
        //reads the file and get some information
        Init initialize = new Init("/home/gustavo/Documents/EP-IA/A-n32-k5.vrp");
        Set set = new Set(initialize.getNodes(), initialize.getCapacity());
        set.generateRoute();


    }
}
