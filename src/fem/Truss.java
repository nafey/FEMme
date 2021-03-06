package fem;

import mat.*;
import java.util.ArrayList;

public class Truss {
	private ArrayList<Node> Nodes;
	private ArrayList<Member> Members;
	private ArrayList<Force> Forces;

	/////////CONSTRUCTORS/////////////////////
	public Truss() {
		this.Nodes = new ArrayList<Node>();
		this.Members = new ArrayList<Member>();
		this.Forces = new ArrayList<Force>();
	}
	
	/////////PROPERTIES//////////////////////
	public int nodeCount() {
		return this.Nodes.size();
	}

	public int memberCount() {
		return this.Members.size();
	}
	
	public int forceCount() {
		return this.Forces.size();
	}
	
	public int dirCount() {
		return this.nodeCount() * 2;
	}
	
	public Node getNode(int i) {
		return this.Nodes.get(i);
	}
	
	public Member getMember(int i) {
		return this.Members.get(i);
	}
	
	public Force getForce(int i) {
		return this.Forces.get(i);
	}

	//returns  the index in Forces which has the 
	public int getForceIndex(Node n) {
		int i = 0;
		for (i = 0; i < this.Forces.size(); i++) {
			if (Forces.get(i).getNode().IsEqual(n)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int getForceIndex(int ai) {
		int i = 0;
		for (i = 0; i < this.Forces.size(); i++) {
			if (Forces.get(i).getNode().IsEqual(this.Nodes.get(ai))) {
				return i;
			}
		}
		
		return -1;
	}
	
	//Input Node to get the DirInd corresponding to X direction
	public int getXIndexFromNode(Node aN) {
		int x = this.findNode(aN);
		if (x >= 0) {
			return (x * 2);
		}
		else {
			return -1;
		}
	}
	
	//Input Node to get the DirInd corresponding to Y direction
	public int getYIndexFromNode(Node aN) {
		int y = this.findNode(aN);
		if (y >= 0) {
			return (y * 2 + 1);
		}
		else {
			return -1;
		}
	}
	
	//Input DirInd to output the Node
	public Node getNodeFromIndex(int ai) { 
		ai = ai - (ai % 2);
		int x = ai / 2; //integer division 
		return this.getNode(x);
	}	
				
	//Input node to get force on it
	public Force getForceFromNode(Node aN) {
		int i = 0;
		int x = 0;
		for (i = 0; i < this.Forces.size(); i++) {
			if (Forces.get(i).getNode().IsEqual(aN)) {
				x = i;
			}
		}
		return this.getForce(x);
	}
		
	//Input DirInd to get Load in that direction
	public double getLoadFromIndex(int ai) {
		Force f = this.getForceFromNode(this.getNodeFromIndex(ai));
		if (ai % 2 == 0) {
			return f.getFx();
		}
		else {
			return f.getFy();
		}
	}
			
	//////////METHODS////////////////////
	public void addNode(Node aN) {
		this.Nodes.add(aN);
	}
	
	public void addMember(Member aM) {
		this.Members.add(aM);
	}
	
	public void addForce(Force aF) {
		this.Forces.add(aF);
	}
	
	public void addForce(Node aN, double aFx, double aFy) {
		this.Forces.add(new Force(aN, aFx, aFy));
	}	
	
	public int reactionCount() {
		int i, x;
		x = 0;
		for (i = 0; i < this.nodeCount(); i++) {
			if (this.getNode(i).getType() == NodeType.PinnedAttached) {
				x += 2;
			}
			else if (this.getNode(i).getType() == NodeType.RollerHorizontal) {
				x++;
			}
			else if (this.getNode(i).getType() == NodeType.RollerVertical) {
				x++;
			}
		}
		
		return x;
	}
			
	
	//returns the position of aN in Nodes
	public int findNode(Node aN) {
		int i = 0;
		while (i <= Nodes.size() - 1) {
			if (aN.IsEqual(Nodes.get(i))) {
				return i;
			}
			else {
				i++;
			}
		}
		
		return -1;
	}			
		
	//The four directional indices corresponding to a member
	public int[] matrixIndices(Member aM) {
		int[] ret;
		ret = new int[4];
		
		ret[0] = this.getXIndexFromNode(aM.getN1());
		ret[1] = this.getYIndexFromNode(aM.getN1());
		ret[2] = this.getXIndexFromNode(aM.getN2());
		ret[3] = this.getYIndexFromNode(aM.getN2());
		
		return ret;
	}

	public Matrix getFlexiMatrix() {
		int i, j, k;
		Matrix iMat;			
		Matrix ret;
		int[] iInd;
		
		i = 0; j = 0; k = 0; 
		ret = new Matrix(Nodes.size() * 2, Nodes.size() * 2);
		iMat = new Matrix(4, 4);
		iInd = new int[4];
		
		for (i = 0; i <= this.memberCount() - 1; i++) {
			iMat = Members.get(i).getFlexiMatrix();
			iInd = matrixIndices(Members.get(i));
			for (j = 0; j <= 3; j++) {
				for (k = 0; k <= 3; k++) {
					ret.setValueAt(iInd[j], iInd[k], 
						ret.getValueAt(iInd[j], iInd[k]) + iMat.getValueAt(j, k));
				}
			}
		}		
		return ret;
	}
	
}

//211
