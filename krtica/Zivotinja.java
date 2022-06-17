package igra;

public abstract class Zivotinja {
	protected Rupa rupa;
	protected boolean udarena;

	public Zivotinja(Rupa rupa) {
		this.rupa = rupa;
		this.udarena = false;
	}
	
	public boolean getUdarena() {
		return this.udarena;
	}
	
	public abstract void paint(Rupa rupa);
	public void efekatUdarena() {
		udarena = true;
	}
	public abstract void efekatPobegla();
}
