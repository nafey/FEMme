package fem;

import mat.*;

public class TrussAnalyser
{
	//Member classes for storing results
	public static class PreProcessingResult 
	{
		public static Truss truss;
		public static Matrix FlexiMat;
	}
	
	public static class ProcessingResult
	{
		public static Matrix Disp;		
	}
	
	public static class PostProcessingResult
	{
		public static Matrix MemberForces;
	}				
	//////////HELPER METHODS///////////////////
	
	//calls uknDisp
	public static Matrix kwnForces(Truss t) {
		int[] uD = TrussAnalyser.uknDisp(t);
		int cuD = uD.length;
		
		Matrix ret = new Matrix(cuD, 1);
		
		int i = 0;
		for (i = 0; i < cuD; i++) {
			ret.setValueAt(i, 0, t.getLoadFromIndex(uD[i]));
		}
		
		return ret;
	}		
	
	//Gets the directional indices for unknown displacements
	private static int[] uknDisp(Truss t) {
		int i = 0;
		int c = 0;
		int[] ret;
		for (i = 0; i < t.nodeCount(); i++) {
			if (t.getNode(i).getType() == NodeType.PinnedFree) {
				c += 2;
			}
			else if (t.getNode(i).getType() == NodeType.RollerHorizontal) {
				c++;
			}
			else if (t.getNode(i).getType() == NodeType.RollerVertical) {
				c++;
			}
		}
			
		ret = new int[c];
		int j = 0;
		for (i = 0; i < t.nodeCount(); i++) {
			if (t.getNode(i).getType() == NodeType.PinnedFree) {
				ret[j] = 2 * i;
				j++;
				ret[j] = 2 * i + 1;
				j++;
			}
			else if (t.getNode(i).getType() == NodeType.RollerHorizontal) {
				ret[j] = 2 * i;
				j++;
			}
			else if (t.getNode(i).getType() == NodeType.RollerVertical) {
				ret[j] = 2 * i + 1;
				j++;
			}
		}		
		return ret;
	}
	
	//Determines whether or not a truss is stable
	private static boolean isStable(Truss t) {
		int b = t.memberCount();
		int r = t.reactionCount();
		int j = t.nodeCount();
		
		if (b + r < 2 * j) 
			return false;
		else
			return true;
	}		
	
	//////////ESSENTIAL METHODS////////////////
	private static void preProcess(Truss t) {
		Matrix mflex = new Matrix(t.getFlexiMatrix().getData());
		TrussAnalyser.PreProcessingResult.FlexiMat = mflex;	
		TrussAnalyser.PreProcessingResult.truss = t;
	}
		
	private static void preProcess(Truss t, boolean print) {
		TrussAnalyser.preProcess(t);
		if (print) {
			System.out.println("");
			System.out.println("The Flexibility Matrix for the Truss is");
			TrussAnalyser.PreProcessingResult.FlexiMat.printMatrix();
		}		
	}
	
	private static void process(Truss t) {
		int[] uD = TrussAnalyser.uknDisp(t);
		
		Matrix mflex = 	TrussAnalyser.PreProcessingResult.FlexiMat;		
		Matrix msmall = mflex.subMatrix(uD);
		Matrix mkwnf = TrussAnalyser.kwnForces(t);
		Matrix mres = MatrixHelper.multiply(MatrixHelper.inverse(msmall), mkwnf);		
		Matrix mdisp = new Matrix(t.dirCount(), 1);
		
		int i = 0;
		for (i = 0; i < uD.length; i++) {
			mdisp.setValueAt(uD[i], 0, mres.getValueAt(i, 0));
		}

		TrussAnalyser.ProcessingResult.Disp = mdisp;
	}
		
	private static void process(Truss t, boolean print) {
		TrussAnalyser.process(t);
		if (print) {
			System.out.println("");
			System.out.println("The displacement matrix for the Truss is");
			TrussAnalyser.ProcessingResult.Disp.printMatrix();
		}
	}
	
	private static void postProcess(Truss t) {
		//for each member get a force; +ve if tensile
		double[] memf; //member forces
		memf = new double[t.memberCount()];
		
		Matrix disp;
		disp = TrussAnalyser.ProcessingResult.Disp;
			
		double a, e, l, mx, my, d1, d2, d3, d4;
		
		int i = 0;
		for (i = 0; i < t.memberCount(); i++) {
			a = t.getMember(i).getArea();
			e = t.getMember(i).getE();
			l = t.getMember(i).getLength();
			mx = t.getMember(i).mX();
			my = t.getMember(i).mY();
			d1 = disp.getValueAt(t.getXIndexFromNode(t.getMember(i).getN1()), 0);
			d2 = disp.getValueAt(t.getYIndexFromNode(t.getMember(i).getN1()), 0);
			d3 = disp.getValueAt(t.getXIndexFromNode(t.getMember(i).getN2()), 0);
			d4 = disp.getValueAt(t.getYIndexFromNode(t.getMember(i).getN2()), 0);
			memf[i] = a * e * ((d3 * mx + d4 * my) - (d1 * mx + d2 * my))/l;
		}
		
		TrussAnalyser.PostProcessingResult.MemberForces = new Matrix(memf);
	}
	
	private static void postProcess(Truss t, boolean print) {
		TrussAnalyser.postProcess(t);
		if (print) {
			System.out.println("");
			System.out.println("The Member forces in the Truss are");
			TrussAnalyser.PostProcessingResult.MemberForces.printMatrix();
		}
	}
			
		
	
	public static void solve(Truss t, boolean print) {
		if (TrussAnalyser.isStable(t)) {
			TrussAnalyser.preProcess(t, print);
			TrussAnalyser.process(t, print);
			TrussAnalyser.postProcess(t, print);
		}
		else {
			System.out.println("Error: Truss is not stable");
		}
	}
		
}

//179
