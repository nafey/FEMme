package fem;

import mat.*;

public class Member
{
	private Node n1;
	private Node n2;
	private double ar;
	private double e;

	////////////CONSTRUCTORS///////////
	public Member(Node an1, Node an2) {
		this.n1 = an1;
		this.n2 = an2;
		this.ar = 1;
		this.e = 1;
		
		if (this.getLength() == 0) {
			System.out.println("Error: Identical node initialised");
		}
	}
	
	
	///////////PROPERTIES//////////////
	public Node getN1() {
		Node retNode = new Node(n1);
		return retNode;
	}
	
	public Node getN2() {
		Node retNode = new Node(n2);
		return retNode;
	}
	
	public double getArea() {
		double ret = this.ar;
		return ret;
	}
	
	public double getE() {
		double ret = this.e;
		return ret;
	}
	
	public void setArea(double aAr) {
		this.ar = aAr;
	}
	
	public void setE(double aE) {
		this.e  = aE ;
	}

	public double getLength() {
		double res = 0;
		res = Math.sqrt((n1.getY() - n2.getY())*(n1.getY() - n2.getY()) + 
						(n1.getX() - n2.getX())*(n1.getX() - n2.getX()));
		return res;
	}
	
	public double mX() {
		double retmX = 0;
		retmX = ((n2.getX() - n1.getX())/this.getLength());
		return retmX;
	}

	public double mY() {
		double retmY = 0;
		retmY = ((n2.getY() - n1.getY())/this.getLength());
		return retmY;
	}

	//	mX2		mXmY	-mX2	-mXmy //
	//	mXmy	mY2		-mXmy	-mY2  //
	// -mX2	   -mXmY	 mX2	 mXmy //
	// -mXmY   -mY2		 mXmy	 mY2  //
	public Matrix getFlexiMatrix() {
		double[][] res;
		res = new double[4][4];

		res[0][0] = this.mX() * this.mX() * this.getArea()  *  this.getE() /  this.getLength();
		res[0][1] = this.mX() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();
		res[0][2] =-this.mX() * this.mX() * this.getArea()  *  this.getE() /  this.getLength();
		res[0][3] =-this.mX() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();

		res[1][0] = this.mX() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();
		res[1][1] = this.mY() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();
		res[1][2] =-this.mX() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();
		res[1][3] =-this.mY() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();

		res[2][0] =-this.mX() * this.mX() * this.getArea()  *  this.getE() /  this.getLength();
		res[2][1] =-this.mX() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();
		res[2][2] = this.mX() * this.mX() * this.getArea()  *  this.getE() /  this.getLength();
		res[2][3] = this.mX() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();

		res[3][0] =-this.mX() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();
		res[3][1] =-this.mY() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();
		res[3][2] = this.mX() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();
		res[3][3] = this.mY() * this.mY() * this.getArea()  *  this.getE() /  this.getLength();
		
		Matrix mres;
		mres = new Matrix(res);

		return mres;
	}

}

//107
