import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class Route{

    //route
    private ArrayList<Node> route = new ArrayList<Node>();
    //Cache
    private int distance = 0;
    //maximum capacity
    //private int maxCapacity;
    //capacity
    private int capacity = 0;

    /*
    public Route(int maxCapacity){
        this.maxCapacity = maxCapacity;
    }
    */

    // Gets a node from the route
    public Node getNode(int nodePosition) {
        return route.get(nodePosition);
    }

    // Gets the total distance of the route
    public int getDistance(){
        int routeDistance = 0;
        // Loop through the Nodes
        for (int nodeIndex=0; nodeIndex < routeSize(); nodeIndex++) {
            // Get node we're traveling from
            Node fromNode = getNode(nodeIndex);
            // node we're traveling to
            Node destinationNode;
            // Check we're not on our route's last node, if we are set our
            // route's final destination city to our starting city
            if(nodeIndex+1 < routeSize()){
                destinationNode= getNode(nodeIndex+1);
            }
            else{
                destinationNode = getNode(0);
            }
            // Get the distance between the two nodes
            routeDistance += fromNode.distanceTo(destinationNode.getId());
        }
        distance = routeDistance;
        return distance;
    }

    //get the current capacity
    public int getCapacity(){
        return capacity;
    }

    //get the route
    public ArrayList<Node> getRoute(){
        return route;
    }

    //add load to the capacity
    public void addLoad(int load){
        this.capacity += load;
    }

    //add a node into the route
    public void addNode(Node node){
        route.add(node);
    }

    // Get number of nodes on our route
    public int routeSize() {
        return route.size();
    }

    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < routeSize(); i++) {
            geneString += getNode(i)+ " " +"=>";
        }
        geneString += "Size: " + routeSize();
        return geneString;
    }
}
