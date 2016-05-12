public class Saving implements Comparable<Saving>{

	//the saving
	private int val;
	//node 1
	private Node from;
	//node 2
	private Node to;
	//frequency
	private int frequency = 0;

	//contructor
	public Saving(int v,Node f,Node t){
		val = v;
		from = f;
		to = t;
	}

	//get node 1
	public Node getFrom(){
		return from;
	}

	//get node 2
	public Node getTo(){
		return to;
	}

	//get the saving
	public int getValue(){
		return val;
	}

	//get the frequency
	public int getFrequency(){
		return frequency;
	}

	//increments the frequency
	public void addFrequency(){
		frequency++;
	}

    @Override
    public String toString(){
        return from +" "+ to;
    }

	@Override
	public int compareTo(Saving o) {
		if(o.val<this.val){
			return -1;
		}else if(o.val == this.val){
			return 0;
		}else{
			return 1;
		}
	}
}
