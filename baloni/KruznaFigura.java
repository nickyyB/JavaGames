package igra;

import java.awt.Color;

public class KruznaFigura extends Krug {
	protected Vektor brzina;
	protected Scena scena;
	
	public KruznaFigura(Vektor c, Color b, double r, Vektor brzina, Scena scena) {
		super(c, b, r);
		this.brzina = brzina;
		this.scena = scena;
	}
	
	public void istekaoPeriod() {
		centar.dodaj(brzina); 
		int sirina = scena.getWidth();
		int visina = scena.getHeight();
		if(centar.getX() < 0 || centar.getX() > sirina -1 || centar.getY() < 0 || centar.getY() > visina - 1) {
			scena.izbaci(this);
		} else {
			iscrtaj(scena);
		}
	}
	
	public void sudaranje(KruznaFigura f) {}
}
