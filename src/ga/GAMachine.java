package ga;

public abstract class GAMachine 
{
	private BitString[] strings;
	private BitStringInfo strinf;
	private double[] fit;
	private int maxgen;
	private int currgen;
	private int pop;
	private double mprob;
	private double cprob;
	
	//////////CONSTRUCTORS////////////////////
	//a bitstring which is an example of the type of bitstrings to be used
	protected GAMachine() {
		
	}
	
	protected void init(BitStringInfo binf, int aMaxGen, int aPop, double aMprob, double aCprob) {
		this.strinf = binf;
		this.maxgen = aMaxGen;		
		this.currgen = 0;
		this.pop = aPop;
		this.strings = new BitString[aPop];
		this.fit = new double[aPop];
		this.mprob = aMprob;
		this.cprob = aCprob;
	}
	//////////PROPERTIES//////////////////////
	private BitString getString(int ai) {
		return this.strings[ai];
	}
	
	private void setString(int ai, BitString bs) {
		this.strings[ai] = bs;
	}
	
	protected int currentGen() {
		return this.currgen;
	}
	
	protected void incrementGen() {
		this.currgen++;
	}
	
	protected int maxGen() {
		return this.maxgen;
	}
	
	protected double mutateProb() {
		return this.mprob;
	}
	
	protected double crossoverProb() {
		return this.cprob;
	}
	
	protected int population() {
		return this.pop;
	}
	
	private double getFitness(int i) {
		return this.fit[i];
	}
	
	private void setFitness(int i, double val) {
		this.fit[i] = val;
	}
		

	//////////METHODS//////////////////////
	protected void initGA() {
		for (int i = 0; i < this.population(); i++) {
			BitString bs = new BitString(strinf);
			this.setString(i, bs);
		}
		
		for (int i = 0; i < this.population(); i++) {
			this.getString(i).randomize();
		}
	}
	
	protected abstract double fitness(BitString bs);
	
	
	private void setAllFitness() {
		for (int i = 0; i < this.population(); i++) {
			this.setFitness(i, this.fitness(this.getString(i)));
		}
	}	
	
	protected double avgFitness() {
		double ret = 0;
		for (int i = 0; i <  this.population(); i++) {
			ret += this.fitness(this.getString(i));
		}
		ret = ret / this.population();
		return ret;
	}			
	
	protected void stepGA() {
		this.setAllFitness();
		
		double[] roul;
		roul = new double[this.population()];
		
		double sum = 0;
		for (int i = 0; i < this.population(); i++) {
			sum += this.getFitness(i);
		}
		
		roul[0] = this.getFitness(0);
		for (int i = 1; i < this.population(); i++) {
			roul[i] = (this.getFitness(i) + roul[0]) / sum;
		}
		
		double[] rnd;
		rnd = new double[this.population()];
		for (int i = 0; i < this.population(); i++) {
			rnd[i] = GAMath.random(1000) / 1000;
		}
		
		int[] winner;
		winner = new int[this.population()];
		
		for (int i = 0; i < this.population(); i++) {
			winner[i] = GAMath.spinTheWheel(roul, rnd[i]);
		}
		
		BitString[] bs1;
		bs1 = new BitString[this.population()];
		
		for (int i = 0; i < this.population(); i++) {
			bs1[i] = new BitString(this.getString(winner[i]));
		}
		
		this.strings = bs1;
		
		for (int i = 0; i < (this.population() / 2); i++) {
			BitString[] bs2;
			bs2 = new BitString[2];
						
			bs2 = this.crossover(this.getString(2 * i), this.getString(2 * i + 1), this.crossoverProb());
			this.setString(2 * i, bs2[0]);
			this.setString(2 * i + 1, bs2[1]);
		}
		
		for (int i = 0; i < this.population(); i++) {
			this.setString(i, this.mutate(this.getString(i), this.mutateProb()));
		}			
	}		
		

	protected void printGen() {
		for (int i = 0; i < this.population(); i++) {
			System.out.println(this.getString(i).toBinaryString());
		}
	}			
			
	//aProb is the fractional prob of mutation for a certain string 
	private BitString mutate(BitString aBs, double aProb) {
		for (int i = 1; i <= aBs.getLength(); i++) {
			if (GAMath.toss(aProb)) {
				aBs.toggleBitAt(i);
			}
		}		
		return new BitString(aBs);
	}
	
	
	//bs1 and bs2 must be of same format
	private BitString[] crossover(BitString bs1, BitString bs2, double aProb) {
		BitString[] bs;
		bs = new BitString[2];
		
		if (GAMath.toss(aProb)) {
			int ix = GAMath.random(bs1.bitCount()) - 1;
			Bit b;
			b = new Bit(bs1.getBit(ix).getBitInfo());
			
			b.setValue(bs1.getBit(ix).getValue());
			bs1.getBit(ix).setValue(bs2.getBit(ix).getValue());
			bs2.getBit(ix).setValue(b.getValue());
			
			bs[0] = new BitString(bs1);
			bs[1] = new BitString(bs2);
		}
		else {
			bs[0] = new BitString(bs1);
			bs[1] = new BitString(bs2);
		}
		
		return bs;
	}
}
















