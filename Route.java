import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

public class Route{

    //route
    private ArrayList<Node> route = new ArrayList<Node>();
    //the distance travelled in the route, can be seen as the cost
    private int distance = 0;
    //the amount of load that is carried
    private int capacity = 0;


    ////////////////////////////////////
    //            GETTERS             //
    ////////////////////////////////////
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

    //copy the instance of an object
    public Route copyRoute(){
        Route newRoute = new Route();
        newRoute.setRoute(getRoute());
        newRoute.setDistance(getDistance());
        newRoute.setCapacity(getCapacity());
        return newRoute;
    }

    //Get the size of the route
    public int routeSize() {
        return route.size();
    }

    ////////////////////////////////////
    //            SETTERS             //
    ////////////////////////////////////

    //set the route
    public void setRoute(ArrayList<Node> route){
        this.route = route;
    }

    //set the distance
    public void setDistance(int distance){
        this.distance = distance;
    }

    //set the capacity
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    //set a node in a specific position
    public void setNode(int index, Node node){
        route.set(index,node);
    }

    ////////////////////////////////////
    //             ADDERS             //
    ////////////////////////////////////

    //Currently not used because we are not using the random generator
    //add load to the capacity
    public void addLoad(int load){
        this.capacity += load;
    }

    //append a not into the route
    public void addNode(Node node){
        route.add(node);
    }

    //adds a node in a specific position
    public void addNode(int index,Node node){
        route.add(index,node);
    }

    ////////////////////////////////////
    //           REMOVERS             //
    ////////////////////////////////////

    //remove a node
    public void removeNode(int index){
        route.remove(index);
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
