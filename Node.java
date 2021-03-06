class Node{
    private int id;
    private int x;
    private int y;
    private int demand;
    //a little bit of lazyness
    public int[] distances;

    @Override
    public String toString(){
        return " "+ (id-1);
    }

    @Override
    public boolean equals(Object cmp){
        if( ((Node) cmp).getId() != getId()) return false;
        return true;
    }


    ////////////////////////////////////
    //            GETTERS             //
    ////////////////////////////////////

    //get the ID
    public int getId(){
        return id;
    }

    //get coordinate X
    public int getX(){
        return x;
    }

    //get coordinate Y
    public int getY(){
        return y;
    }

    //get node demand
    public int getDemand(){
        return demand;
    }

    //get the distance to other nodes
    public int[] getDistances(){
        return distances;
    }

    //copy an instance of a node
    public Node copyNode(){
        Node newNode = new Node();
        newNode.setId(getId());
        newNode.setX(getX());
        newNode.setY(getY());
        newNode.setDemand(getDemand());
        newNode.setDistances(getDistances());
        return newNode;
    }

    //this is used for the annealing
    //calculate the distance from one node to another
    public int distanceTo(int destination){
        return distances[destination];
    }

    public int getDistance(Node to){
        int distance = (int) Math.round(Math.sqrt(Math.pow((getX() - to.getX()), 2) + Math.pow((getY() - to.getY()), 2)));
        return distance;
    }

    ////////////////////////////////////
    //            SETTERS             //
    ////////////////////////////////////

    //set ID
    public void setId(int id){
        this.id = id;
    }
    //set Coordinate X
    public void setX(int x){
        this.x = x;
    }
    //set Coordinate Y
    public void setY(int y){
        this.y = y;
    }
    //set the demand
    public void setDemand(int demand){
        this.demand = demand;
    }

    //set the distance to other nodes
    public void setDistances(int []distances){
        this.distances = distances;
    }

}
