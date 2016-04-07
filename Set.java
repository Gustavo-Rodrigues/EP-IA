import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class Set{

    //Set
    private ArrayList<Route> set = new ArrayList<Route>();
    //Cache
    private int totalDistance = 0;
    //nodes
    private ArrayList<Node> nodes;
    //capacity of each vehicle
    private int capacity;


    //contructs the nodes
    public Set(Node nodes[], int capacity){
        this.nodes = new ArrayList<Node>(Arrays.asList(nodes));
        this.capacity = capacity;
    }

    public ArrayList<Route> getSet(){
        return set;
    }

    public Route getRoute(int index){
        return set.get(index);
    }

    public void addRoute(Route route){
        set.add(route);
    }

    //create a set of routes in which the routes are created randomically accordingly to the limit of capacity
    public void createSet(){
        //get the copy of the nodes
        ArrayList<Node> cNodes = new ArrayList<Node>(nodes);
        Random random = new Random();
        Route route = new Route();
        int randomPosition = 1;
        //System.out.println(cNodes.size());
        while(cNodes.size() > 0){
            System.out.println(cNodes.size());
            //raffle the position
            randomPosition = random.nextInt(cNodes.size());
            //System.out.println("ID: " +cNodes.get(randomPosition));
            if(route.getCapacity() + cNodes.get(randomPosition).getDemand() <= capacity){
                //adds the node to the route
                System.out.println("NODE: "+ cNodes.get(randomPosition));
                route.addNode(cNodes.get(randomPosition));
                route.addLoad(cNodes.get(randomPosition).getDemand());
                //remove the node from the nodes
                cNodes.remove(randomPosition);
            }else{
                addRoute(route);
                route = new Route();
            }
        }
    }

    //get the number of routes in our set
    public int setSize(){
        return set.size();
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < setSize(); i++) {
            geneString += getRoute(i) + "\n";
        }
        return geneString;
    }

}
