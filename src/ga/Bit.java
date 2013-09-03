package ga;

import ga.GAMath;
import java.lang.Integer;

public class Bit 
{
	private int value;
	private BitInfo binf;
	
	//////////CONSTRUCTORS////////////////	
	public Bit(int av, BitInfo abf) {
		this.binf = abf;
		this.setValue(av);
	}
		
	public Bit(BitInfo abf) {
		this.binf = abf;
		this.setValue(0);
	}
	
	public Bit(Bit abt) {
		this.binf = abt.binf;
		this.value = abt.value;
	}
	//////////PROPERTIES//////////////////
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int val) {
		if (val < this.getMaxValue()) {
			this.value = val;
		}
		else {
			this.value = this.getMaxValue();
		}
	}
	
	public BitInfo getBitInfo() {
		return this.binf;
	}
	
	public int getMaxValue() {
		return (GAMath.power(2, this.getLength()) - 1);
	}
	
	public int getLength() {
		return this.getBitInfo().getDigits();
	}
	
	public double getRealValue() {
		double top = this.getBitInfo().getTop();
		double bot = this.getBitInfo().getBot();
		double val = this.getValue();
		double max = this.getMaxValue();
		
		return bot + (top - bot)*(val / max);
	}

	//////////METHODS/////////////////////
	//the pos here refers to unity based index refering to position 
	//where we wind the value of binary bit
	public int getBitAt(int pos) {
		if (pos < 1 || pos > this.getLength()) {
			return 0;
		}
		else {
			return GAMath.bitAt(this.getValue(), pos);
		}
	}
	
	//edit later
	public void toggleBitAt(int pos) {
		if (this.getBitAt(pos) == 1) {
			this.setValue(this.getValue() - GAMath.power(2, pos - 1));
		}
		else {
			this.setValue(this.getValue() + GAMath.power(2, pos - 1));
		}
	}
	
	public String toBinaryString() {
		String ret = "";
		for (int i = 0; i < this.getLength(); i++) {
			ret += Integer.toString(this.getBitAt(this.getLength() - i));
		}
		
		return ret;
	}
	
	public void randomize() {
		this.setValue(GAMath.random(this.getMaxValue() + 1) -1);
	}
		
}

