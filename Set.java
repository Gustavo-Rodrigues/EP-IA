import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class Set{

    //Set
    private ArrayList<Route> set = new ArrayList<Route>();
    //total distance travalled, can be seen as the total cost
    private int totalDistance = 0;
    //nodes
    private ArrayList<Node> nodes;
    //distance array, distances between the nodes
    private int dist[][];
    //capacity of each vehicle
    private int capacity;

    ////////////////////////////////////
    //          CONSTRUCTORS          //
    ////////////////////////////////////
    public Set(){};

    public Set(int capacity){
        this.capacity = capacity;
    }

    public Set(Node nodes[], int capacity){
        this.nodes = new ArrayList<Node>(Arrays.asList(nodes));
        this.capacity = capacity;
        dist = new int[nodes.length][nodes.length];
        for(int i = 0; i<nodes.length; i++){
            for(int j = 0; j< nodes.length; j++){
                dist[i][j] = nodes[i].getDistances()[j];
            }
        }
    }

    ////////////////////////////////////
    //            GETTERS             //
    ////////////////////////////////////

    //copy the instance of an Set
    public Set copySet(){
        Set newSet = new Set();
        newSet.setSet(getSet());
        newSet.setTotalDistance(getDistance());
        newSet.setCapacity(getMaximumCapacity());
        return newSet;
    }

    //get the nodes
    public ArrayList<Node> getNodes(){
        ArrayList<Node> n = new ArrayList<Node>(nodes);
        return n;
    }

    //get the maximum capacity
    public int getMaximumCapacity(){
        return capacity;
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

    //get the total distance
    public int getDistance(){
        int temp  = 0;
        for(int i = 0; i<setSize();i++){
            //System.out.println("Route distance: " + getRoute(i).getDistance());
            temp += getRoute(i).getDistance();
        }
        totalDistance = temp;
        return totalDistance;
    }

    //get the number of routes in our set
    public int setSize(){
        return set.size();
    }

    //get the quantity of nodes in the set
    public int getTotalNodes(){
        int total = 0;
        for(int i = 0; i<set.size(); i++){
            //minus 1 beacuse of the 0 at the beginning
            total += set.get(i).routeSize()-1;
        }
        return total;
    }

    ////////////////////////////////////
    //            SETTERS             //
    ////////////////////////////////////

    //set a route
    public void setRoute(int index, Route route){
        set.set(index,route);
    }

    //set a set
    public void setSet(ArrayList<Route> set){
        this.set = set;
    }

    //set the total distance
    public void setTotalDistance(int totalDistance){
        this.totalDistance = totalDistance;
    }

    //set the capacity
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    ////////////////////////////////////
    //             ADDERS             //
    ////////////////////////////////////

    //adds a route
    public void addRoute(Route route){
        set.add(route);
    }

    ////////////////////////////////////
    //           REMOVERS             //
    ////////////////////////////////////

    //remove route
    public void removeRoute(int index){
        set.remove(index);
    }

    //algorithm was made based on the following site:
    //http://web.mit.edu/urban_or_book/www/book/chapter6/6.4.12.html
    //clarke and Wright algorithm
    public void clarkeWright(){
        ArrayList<Saving> savings  = computeSaving();
        Collections.sort(savings);
        int count = 0;
        int found = 0;
        while(savings.size()>0){
            found = 0;
            int fromRoute = 0;
            int toRoute = 0;
            int position = 0;
            boolean isInternal = false;
            int which = -1;
            for(int i = 0; i<setSize(); i++){
                for(int j = 0; j<set.get(i).routeSize(); j++){
                    if(set.get(i).getNode(j).getId() == savings.get(0).getFrom().getId()){
                        found++;
                        fromRoute = i;
                        if(set.get(i).getCapacity() + savings.get(0).getTo().getDemand() <= capacity) position = j;
                        which = 1;
                        if(position+1 == set.get(i).routeSize() || position-1 != 0 ) isInternal = true; //there is no 0 at the end
                    }else if(set.get(i).getNode(j).getId() == savings.get(0).getTo().getId()){
                        found++;
                        toRoute = i;
                        if(set.get(i).getCapacity() + savings.get(0).getFrom().getDemand() <= capacity) position = j;
                        which = 0;
                        if(position-1 != 0 || position+1 == set.get(i).routeSize()) isInternal = true;
                    }
                }
                if(found == 2) break;
            }

            if(found == 0){
                if(savings.get(0).getFrom().getDemand() + savings.get(0).getTo().getDemand() <= capacity){
                    Route newRoute = new Route();
                    newRoute.addNode(nodes.get(0));
                    newRoute.addNode(savings.get(0).getFrom());
                    newRoute.addNode(savings.get(0).getTo());
                    addRoute(newRoute);
                }
            }
            if(found == 1 && !isInternal){
                if(which == 1 && set.get(fromRoute).getCapacity() + savings.get(0).getTo().getDemand() <= capacity){
                    if(position - 1 == 0){
                        set.get(fromRoute).addNode(position,savings.get(0).getTo());
                    }else set.get(fromRoute).addNode(position+1,savings.get(0).getTo());
                }else if(which == 0 && set.get(toRoute).getCapacity() + savings.get(0).getFrom().getDemand() <= capacity){
                    if(position - 1 == 0){
                        set.get(toRoute).addNode(position,savings.get(0).getFrom());
                    } else set.get(toRoute).addNode(position+1,savings.get(0).getFrom());
                }
            }
            if(found == 2 && fromRoute != toRoute && !isInternal && set.get(fromRoute).getCapacity() + set.get(toRoute).getCapacity() <= capacity){
                ArrayList<Node> merge = new ArrayList<Node>(set.get(fromRoute).getRoute());
                ArrayList<Node> temp = new ArrayList<Node>(set.get(toRoute).getRoute());
                temp.remove(0);
                merge.addAll(temp);
                Route mergedRoute = new Route();
                mergedRoute.setRoute(merge);

                if(toRoute > fromRoute){
                    set.remove(fromRoute);
                    set.remove(toRoute-1);
                }else{
                    set.remove(toRoute);
                    set.remove(fromRoute-1);
                }

                set.add(mergedRoute);
            }
            savings.remove(0);
        }

        ArrayList<Node> checkNodes = new ArrayList<Node>(nodes);
        for(int i = 0; i<setSize(); i++){
            for(int j = 0; j<set.get(i).routeSize(); j++){
                for(int k = 0; k<checkNodes.size(); k++){
                    if(set.get(i).getNode(j).getId() == checkNodes.get(k).getId()){
                        checkNodes.remove(k);
                        i = 0;
                        j = 0;
                        k = 0;
                        break;
                    }
                }
            }
        }

        while(checkNodes.size()>0){
            Route newRoute = new Route();
            ArrayList<Node> insertNodes = new ArrayList<Node>();
            insertNodes.add(nodes.get(0));
            insertNodes.add(checkNodes.get(0));
            newRoute.setRoute(insertNodes);
            set.add(newRoute);
            checkNodes.remove(0);
        }
    }

    //saving function of clarke and wirght algorithm
    public ArrayList<Saving> computeSaving(){
		int sav[][] = new int[nodes.size()][nodes.size()];
		ArrayList<Saving> sList = new ArrayList<Saving>();
		for(int i=1;i<dist.length;i++){
			for(int j=i+1;j<dist.length;j++){
				sav[i][j] = dist[0][i] + dist[j][0] - dist[i][j];
				Node n1 = nodes.get(i).copyNode();
				Node n2 = nodes.get(j).copyNode();
				Saving s = new Saving(sav[i][j],n1, n2);
				sList.add(s);
			}
		}
		return sList;
	}
    /*

    //create a set of routes in which the routes are created randomically accordingly to the limit of capacity
    public void createSet(){
        //get the copy of the nodes
        ArrayList<Node> cNodes = new ArrayList<Node>(nodes);
        Random random = new Random();
        Route route = new Route();
        int randomPosition = random.nextInt(cNodes.size());
        //System.out.println("WTF");
        while(cNodes.size() > 0){
            //System.out.println("CNODES SIZE:"+cNodes.size());
            //raffle the position
            randomPosition = random.nextInt(cNodes.size());
            //System.out.println("RAND:"+cNodes.get(randomPosition).getDemand());
            if(route.getCapacity() + cNodes.get(randomPosition).getDemand() <= capacity){
                //System.out.println("WTF2");
                //adds the node to the route
                route.addNode(cNodes.get(randomPosition));
                route.addLoad(cNodes.get(randomPosition).getDemand());
                //remove the node from the nodes
                cNodes.remove(randomPosition);
            }else{
                //System.out.println("WTF3");
                addRoute(route);
                route = new Route();
            }
        }
        addRoute(route);
    }
    */

    @Override
    public boolean equals(Object cmp){
        for(int i = 0; i<((Set) cmp).setSize(); i++){
            if(!((Set) cmp).getRoute(i).equals(set.get(i))) return false;
        }
        return true;
    }

    @Override
    public String toString(){
        String geneString = "";
        for (int i = 0; i < setSize(); i++) {
            geneString += getRoute(i) + "\n";
        }
        return geneString;
    }

}
