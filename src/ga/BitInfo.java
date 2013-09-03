package ga;

public class BitInfo 
{
	private double top;
	private double bot;
	private int digs;
	
	public BitInfo(double at, double ab, int ad) {
		this.top = at;
		this.bot = ab;
		this.digs = ad;
	}
	
	public double getTop() {
		return top;
	}
	
	public double getBot() {
		return bot;
	}
	
	public int getDigits() {
		return digs;
	}
}

//26
