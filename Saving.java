public class Saving implements Comparable<Saving>{
	private int val;
	private Node from;
	private Node to;
	private int frequency = 0;

	public Saving(int v,Node f,Node t){
		val = v;
		from = f;
		to = t;
	}

	public Node getFrom(){
		return from;
	}
	public Node getTo(){
		return to;
	}
	public int getValue(){
		return val;
	}
	public int getFrequency(){
		return frequency;
	}
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
