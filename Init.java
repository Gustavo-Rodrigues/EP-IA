import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;

class Init{
    //the amount of nodes
    private int max_nodes;
    //transport capacity
    private int capacity;
    //nodes
    private Node[] nodes;
    //start node
    private Node depot;

    public Node[] getNodes(){
        return nodes;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getMaxNodes(){
        return max_nodes;
    }

    //reads the file and creates nodes
    public Init(String file){
        BufferedReader br = null;
		try {
			String currentLine;
			br = new BufferedReader(new FileReader(file));

            currentLine = br.readLine();
            String split[] = currentLine.split(" ");
            //read the header
            while(!(split[0].equals("NODE_COORD_SECTION"))){
                if(split[0].equals("DIMENSION")) max_nodes = Integer.parseInt(split[2]);
                if(split[0].equals("CAPACITY")) capacity = Integer.parseInt(split[2]);
                currentLine = br.readLine();
                split = currentLine.split(" ");
            }

            nodes = new Node[max_nodes];
            for(int h = 0; h<nodes.length; h++){
                nodes[h] = new Node();
            }

            currentLine = br.readLine();
            //read the nodes
            //initialize the coordinates
            split = currentLine.split(" ");
            int count = 0;
            while(!(split[0].equals("DEMAND_SECTION"))){
                if(split.length == 4){
                    nodes[count].setId(Integer.parseInt(split[1]));
                    nodes[count].setX(Integer.parseInt(split[2]));
                    nodes[count].setY(Integer.parseInt(split[3]));
                }else{
                    nodes[count].setId(Integer.parseInt(split[0]));
                    nodes[count].setX(Integer.parseInt(split[1]));
                    nodes[count].setY(Integer.parseInt(split[2]));
                }
                currentLine = br.readLine();
                split = currentLine.split(" ");
                count++;
            }

            currentLine = br.readLine();
            //read the demand section
            split = currentLine.split(" ");
            count = 0;
            while(!(split[0].equals("DEPOT_SECTION"))){
                nodes[count].setDemand(Integer.parseInt(split[1]));
                currentLine = br.readLine();
                split = currentLine.split(" ");
                count++;
            }

            currentLine = br.readLine();
            //read the depot section
            currentLine = br.readLine();
            depot = nodes[0];
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }

    //initialize the distance vector
    public void init_distance(){
        for(int i = 0; i<nodes.length; i++){
            nodes[i].distances = new int[max_nodes];
            for(int j = 0; j<nodes.length; j++){
                nodes[i].distances[j] = (int) Math.round(Math.sqrt(Math.pow((nodes[i].getX() - nodes[j].getX()), 2) +
                Math.pow((nodes[i].getY() - nodes[j].getY()), 2)));
            }
        }
    }
}
