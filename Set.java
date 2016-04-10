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

    public Set(){};

    //contructs the nodes
    public Set(Node nodes[], int capacity){
        //for(int i = 0; i<nodes.length;i++) System.out.println(nodes[i]);
        this.nodes = new ArrayList<Node>(Arrays.asList(nodes));
        this.capacity = capacity;
    }

    public Set(ArrayList<Route> set, int totalDistance, ArrayList<Node> nodes, int capacity){
        this.set = set;
        this.totalDistance = totalDistance;
        this.nodes = nodes;
        this.capacity = capacity;
    }

    public Set(Set aSet){
        this(aSet.getSet(), aSet.getDistance(), aSet.getNodes(), aSet.getMaximumCapacity());
    }

    public Set copySet(){
        Set newSet = new Set();
        newSet.setSet(getSet());
        newSet.setTotalDistance(getDistance());
        newSet.setCapacity(getMaximumCapacity());
        return newSet;
    }


    public ArrayList<Node> getNodes(){
        return nodes;
    }

    public int getMaximumCapacity(){
        return capacity;
    }

    //get the instance
    public Set getInstance(){
        return this;
    }

    //get the set
    public ArrayList<Route> getSet(){
        ArrayList<Route> newList = new ArrayList<Route>(set);
        return newList;
    }

    //get the route
    public Route getRoute(int index){
        return set.get(index).copyRoute();
    }

    public int getDistance(){
        int temp  = 0;
        for(int i = 0; i<setSize();i++){
            //System.out.println("Route distance: " + getRoute(i).getDistance());
            temp += getRoute(i).getDistance();
        }
        totalDistance = temp;
        return totalDistance;
    }

    //adds a route
    public void addRoute(Route route){
        set.add(route);
    }

    public void setRoute(int index, Route route){
        set.set(index,route);
    }

    public void setSet(ArrayList<Route> set){
        this.set = set;
    }

    public void setTotalDistance(int totalDistance){
        this.totalDistance = totalDistance;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    //create a set of routes in which the routes are created randomically accordingly to the limit of capacity
    public void createSet(){
        //get the copy of the nodes
        ArrayList<Node> cNodes = new ArrayList<Node>(nodes);
        Random random = new Random();
        Route route = new Route();
        int randomPosition = random.nextInt(cNodes.size());
        while(cNodes.size() > 0){
            //raffle the position
            randomPosition = random.nextInt(cNodes.size());
            if(route.getCapacity() + cNodes.get(randomPosition).getDemand() <= capacity){
                //adds the node to the route
                route.addNode(cNodes.get(randomPosition));
                route.addLoad(cNodes.get(randomPosition).getDemand());
                //remove the node from the nodes
                cNodes.remove(randomPosition);
            }else{
                addRoute(route);
                route = new Route();
            }
        }
        addRoute(route);
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
