package ga;

import java.lang.Math;
import java.util.Random;

public class GAMath
{
	public static int power(int num, int pow) {
		int i;
		int ret = 1;
		for (i = 0; i < pow; i++) {
			ret *= num;
		}
		
		return ret;
	}
	
	public static int bitAt(int num, int pos) {
		for (int i = 1; i < pos; i++) {
			num /= 2;
		}
		return num % 2;
	}
	
	//returns a random number between 1 and num
	//including 1 and num
	public static int random(int num) {
		int ret;
		
		Random rand;
		rand = new Random();
		
		ret = rand.nextInt(num);
		return ret + 1;
	}
	
	public static boolean toss(double prob) {
		double p = prob * 1000;
		int x = random(1000);
		
		if (x < p) 
			return true;
		else
			return false;
	}
	
	public static int spinTheWheel(double[] roulette, double val) {
		int ret = 0;
		int c = roulette.length;
	
		for (int i = 0; i < c; i++) {
			if (roulette[i] > val) {
				ret = i;
			}
		}
		
		return ret;
	}
				
}















//46
