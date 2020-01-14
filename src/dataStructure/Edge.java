package dataStructure;

public class Edge implements edge_data {
	private int src;
	private int des;
	private double weight;
	private String info;
	private int tag;

	public Edge(int src, int des, double weight, String info, int tag) {
		this.src = src;
		this.des = des;
		if (weight < 0) {
			throw new RuntimeException("Weight must be positive");
		}
		this.weight =weight;
		this.info = info;
		this.tag = tag;
	}
	public Edge(int src, int des,double weight) {
		this.src = src;
		this.des = des;
		if (weight < 0) {
			throw new RuntimeException("Weight must be positive");
		}
		this.weight =weight;
		info = "";
		tag = -1;
	}
	public Edge(Edge edge) {
		this.src = edge.src;
		this.des = edge.des;
		this.weight =edge.weight;
		this.info = edge.info;
		this.tag = edge.tag;
	}

	@Override
	public int getSrc() {
		return this.src;
	}

	@Override
	public int getDest() {
		return this.des;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
	}
	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;		
	}

}
