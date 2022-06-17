package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Krug {
	protected Vektor centar;
	protected Color boja;
	protected double R;
	
	public Krug(Vektor centar, Color boja, double R) {
		this.centar = centar;
		this.boja = boja;
		this.R = R;
	}
	
	public boolean preklapaSe(Krug k) {
		return Math.sqrt((centar.getX() - k.centar.getX()) * (centar.getX() - k.centar.getX()) +
				(centar.getY() - k.centar.getY()) * (centar.getY() - k.centar.getY())) <
				(R / 2 + k.R / 2);
	}
	
	public void iscrtaj(Scena s) {
		Graphics g = s.getGraphics();
		repaint(s);
		g.setColor(boja);
		g.fillOval((int)(centar.getX() - R/2), (int)(centar.getY() - R/2), (int)R, (int)R);
	}

	private void repaint(Scena s) {
		Graphics g = s.getGraphics();
		g.setColor(boja);
		g.fillOval((int)(centar.getX() - R/2), (int)(centar.getY() - R/2), (int)R, (int)R);
	}
}
