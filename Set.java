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

    //generate a random set of routes
    public void generateRoute(){
        //get the copy of the nodes
        ArrayList<Node> cNodes = new ArrayList<Node>(nodes);
        int x = 1;
        while(x != 31){
            //raffle the position
            Random random = new Random();
            x = random.nextInt(cNodes.size());
            System.out.println(x);
        }

    }
}
