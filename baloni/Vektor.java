package igra;

public class Vektor implements Cloneable {
	private double x, y;
	
	public Vektor(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public Vektor pomnozi(double vrednost) {
		x *= vrednost;
		y *= vrednost;
		return this;
	}
	
	public Vektor dodaj(Vektor drugi) {
		x += drugi.x;
		y += drugi.y;
		return this;
	}
	
	@Override
	public Object clone() {
		Vektor v = null;
		try {
			v = (Vektor)super.clone();
		}catch(CloneNotSupportedException e) {}
		return v;
	}
}
