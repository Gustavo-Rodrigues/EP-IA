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

    //getters
    public int getId(){
        return id;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getDemand(){
        return demand;
    }
    public int[] getDistances(){
        return distances;
    }
    public Node copyNode(){
        Node newNode = new Node();
        newNode.setId(getId());
        newNode.setX(getX());
        newNode.setY(getY());
        newNode.setDemand(getDemand());
        newNode.setDistances(getDistances());
        return newNode;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setDemand(int demand){
        this.demand = demand;
    }
    public void setDistances(int []distances){
        this.distances = distances;
    }

    //calculate the distance from one node to another
    public int distanceTo(int destination){
        return distances[destination];
    }
}
