class Node{
    private int id;
    private int x;
    private int y;
    private int demand;
    private int[] distances;

    @Override
    public String toString(){
        return "X: " + getX() + " Y: " + getY();
    }

    //getters
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getDemand(){
        return demand;
    }

    //setters
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setDemand(int demand){
        this.demand = demand;
    }

    //calculate the distance from one node to another
    public double distanceTo(int destination){
        return distances[destination];
    }
}
