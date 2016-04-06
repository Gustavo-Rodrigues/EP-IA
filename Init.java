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

    public Init(String file){
        BufferedReader br = null;
		try {
			String currentLine;
			br = new BufferedReader(new FileReader(file));

            currentLine = br.readLine();
            //System.out.println(currentLine);
            String split[] = currentLine.split(" ");
            //read the header
            while(!(split[0].equals("NODE_COORD_SECTION"))){
                //System.out.println(currentLine);
                if(split[0].equals("DIMENSION")) max_nodes = Integer.parseInt(split[2]);
                if(split[0].equals("CAPACITY")) capacity = Integer.parseInt(split[2]);
                currentLine = br.readLine();
                split = currentLine.split(" ");
            }

            nodes = new Node[max_nodes];
            for(int h = 0; h<nodes.length; h++){
                nodes[h] = new Node();
            }

            //System.out.println("DIMENSION:" + max_nodes + "\nCAPACITY: " + capacity);

            //System.out.println(currentLine);

            /*
            split = currentLine.split(" ");
            for(int i = 0; i<split.length; i++){
                System.out.println(split[i]);
            }
            System.out.println("Length:"+split.length);
            */

            currentLine = br.readLine();
            //read the nodes
            //initialize the coordinates
            split = currentLine.split(" ");
            int count = 0;
            while(!(split[0].equals("DEMAND_SECTION"))){
                if(split.length == 4){
                    nodes[count].setX(Integer.parseInt(split[2]));
                    nodes[count].setY(Integer.parseInt(split[3]));
                }else{
                    nodes[count].setX(Integer.parseInt(split[1]));
                    nodes[count].setY(Integer.parseInt(split[2]));
                }
                //System.out.println("Line:" + currentLine);
                //System.out.println(nodes[count].x + " " + nodes[count].y );
                currentLine = br.readLine();
                split = currentLine.split(" ");
                count++;
                //System.out.println(count);
            }



            currentLine = br.readLine();
            //read the demand section
            split = currentLine.split(" ");
            count = 0;
            while(!(split[0].equals("DEPOT_SECTION"))){
                nodes[count].setDemand(Integer.parseInt(split[1]));
                //System.out.println("Line:" + currentLine);
                //System.out.println(nodes[count].demand);
                currentLine = br.readLine();
                split = currentLine.split(" ");
                count++;
            }

            currentLine = br.readLine();
            //read the depot section
            currentLine = br.readLine();
            depot = nodes[0];
            //System.out.println("Coordinates:" + nodes[0].x + nodes[0].y );
            //System.out.println(depot.x + " " + depot.y);
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
    public static void init_distance(){
        for(int i = 0; i<nodes.length; i++){
            nodes[i].distances = new int[max_nodes];
            for(int j = 0; j<nodes.length; j++){
                nodes[i].distances[j] = (int) Math.round(Math.sqrt(Math.pow((nodes[i].x - nodes[j].x), 2) + Math.pow((nodes[i].y - nodes[j].y), 2)));
                //System.out.println(nodes[i].distances[j]);
            }
        }
    }
}
