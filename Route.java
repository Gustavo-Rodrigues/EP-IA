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
        return route.get(nodePosition).copyNode();
    }

    // Gets the total distance of the route
    public int getDistance(){
        int routeDistance = 0;
        Node currentNode;
        // Loop through the Nodes
        int nodeIndex;
        for (nodeIndex=0; nodeIndex < routeSize()-1; nodeIndex++) {
            currentNode = getNode(nodeIndex);
            //System.out.println(currentNode.getId());
            //System.out.println(getNode(nodeIndex+1).getId());
            routeDistance += currentNode.distanceTo(getNode(nodeIndex+1).getId()-1);
            //System.out.println(routeDistance);
        }
        currentNode = getNode(nodeIndex);
        routeDistance += currentNode.distanceTo(0);
        //System.out.println(routeDistance);
        return routeDistance;
    }

    //get the current capacity
    public int getCapacity(){
        int routeCapacity = 0;
        Node currentNode;
        // Loop through the Nodes
        int nodeIndex;
        for (nodeIndex=0; nodeIndex < routeSize()-1; nodeIndex++) {
            currentNode = getNode(nodeIndex);
            //System.out.println(currentNode.getId());
            //System.out.println(getNode(nodeIndex+1).getId());
            routeCapacity += currentNode.getDemand();
            //System.out.println(routeDistance);
        }
        //System.out.println(routeDistance);
        currentNode = getNode(nodeIndex);
        routeCapacity += currentNode.getDemand();
        capacity = routeCapacity;
        return capacity;
    }

    //get the route
    public ArrayList<Node> getRoute(){
        ArrayList<Node> newList = new ArrayList<Node>(route);
        return newList;
    }

    public Route copyRoute(){
        Route newRoute = new Route();
        newRoute.setRoute(getRoute());
        newRoute.setDistance(getDistance());
        newRoute.setCapacity(getCapacity());
        return newRoute;
    }

    public void setRoute(ArrayList<Node> route){
        this.route = route;
    }

    public void setDistance(int distance){
        this.distance = distance;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public void setNode(int index, Node node){
        route.set(index,node);
    }

    //add load to the capacity
    public void addLoad(int load){
        this.capacity += load;
    }

    //add a node into the route
    public void addNode(Node node){
        route.add(node);
    }

    public void addNode(int index,Node node){
        route.add(index,node);
    }
    public void removeNode(int index){
        route.remove(index);
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
        geneString += " 0";
        geneString += " " + "Cost: " + getDistance();
        geneString += " " + "Capacity: " + getCapacity();
        return geneString;
    }
}
