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
    static int max_nodes;
    //transport capacity
    static int capacity;
    //nodes
    static Node[] nodes;
    //start node
    static Node depot;

    public static void ReadFile(String file){
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
            for(int hope = 0; hope<nodes.length; hope++){
                nodes[hope] = new Node();
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
                    nodes[count].x = Integer.parseInt(split[2]);
                    nodes[count].y = Integer.parseInt(split[3]);
                }else{
                    nodes[count].x = Integer.parseInt(split[1]);
                    nodes[count].y = Integer.parseInt(split[2]);
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
                nodes[count].demand =  Integer.parseInt(split[1]);
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

    public static void main(String args[]){
        ReadFile("/home/gustavo/Documents/EP-IA/A-n32-k5.vrp");
        for(int i = 0; i<nodes.length; i++){
            System.out.println("Coordinates:" + nodes[i].x + nodes[i].y );
        }
        //init_distance();
    }
}
