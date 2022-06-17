package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends KruznaFigura {
	public Igrac(Vektor c, double r, Vektor br, Scena sc) {
		super(c, Color.GREEN, r, br, sc);
	}

	@Override
	public void istekaoPeriod() {
		iscrtaj(scena);
	}
	
	public void pomeri(double pomeraj) {
		 if(centar.getX() + pomeraj < 0 || centar.getX() + pomeraj > scena.getWidth() -1) return;
		 centar.dodaj(new Vektor(pomeraj, 0));
	}
	
	@Override
	public void iscrtaj(Scena s) {
		super.iscrtaj(s);
		paint(s);
		Graphics g = s.getGraphics();
		g.setColor(Color.BLUE);
		g.fillOval((int)(centar.getX() - R/4), (int)(centar.getY() - R/4), (int)R/2, (int)(R/2));
	}

	private void paint(Scena s) {
		super.iscrtaj(s);
		
		Graphics g = s.getGraphics();
		g.setColor(Color.BLUE);
		g.fillOval((int)(centar.getX() - R/4), (int)(centar.getY() - R/4), (int)R/2, (int)(R/2));
		
	}

	@Override
	public void sudaranje(KruznaFigura f) {
		if(f instanceof Balon) {
			scena.zaustavi();
		}
	}
	
	
}
