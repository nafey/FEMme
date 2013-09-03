import fem.*;
import mat.*;
import ga.*;

public class Main
{
	public static Truss t;	
	public static BitInfo bi, bj;
	public static Bit b1, b2, b3, b4, b5, b6;
	public static BitString bs1;
	public static BitString bs[];
	public static GAMachine gam;
	
	public static void main(String args[]) {
		Node n1, n2, n3;
		n1 = new Node(0, 0, NodeType.PinnedFree);
		n2 = new Node(3, 0);
		n3 = new Node(3, 4);
		
		Member m1, m2;
		m1 = new Member(n1, n2);
		m2 = new Member(n1, n3);
		
		t = new Truss();
		t.addNode(n1);
		t.addNode(n2);
		t.addNode(n3);
		t.addMember(m1);
		t.addMember(m2);
		t.addForce(new Force(n1, 0, -2000));
		
		TrussAnalyser.solve(t, false);
				
		bi = new BitInfo(50, 1, 5);	
		bj = new BitInfo(500, 250, 3);
				
		b1 = new Bit(10, bi);
 		b2 = new Bit(2, bj);
 		b3 = new Bit(5, bi);
 		b4 = new Bit(6, bj);
 		b5 = new Bit(3, bi);
 		b6 = new Bit(1, bj);
		
 		Bit[] ba;
 		ba = new Bit[4];
 		
 		ba[0] = b1;
 		ba[1] = b2;
 		ba[2] = b3;
 		ba[3] = b4;
 		 		 		
 		bs1 = new BitString(ba);
 		bs1.randomize();
 		
 		GATruss gat;
 		gat = new GATruss();
 		gat.init(t, 50, 12, 0.01, 0.9);
 		gat.doGA();
	}
}

//1040
