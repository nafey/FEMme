package fem;

public class Force {
	private Node n;
	private double fx;
	private double fy;
	
	public Force() {
		this.fx = 0;
		this.fy = 0;
	}
	public Force(Node an, double aFx, double aFy) {
		this.n = an;
		this.fx = aFx;
		this.fy = aFy;
	}
	
	public Node getNode() {
		return this.n;
	}
	
	public double getFx() {
		return this.fx;
	}
	
	public double getFy() {
		return this.fy;
	}	
}
	
//29
