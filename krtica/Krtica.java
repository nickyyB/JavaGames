package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Krtica extends Zivotinja {
	
	public Krtica(Rupa rupa) {
		super(rupa);
	}

	@Override
	public void paint(Rupa rupa) {
		Graphics g = rupa.getGraphics();
		g.setColor(Color.DARK_GRAY);
		
		double procenat = (double)rupa.getTrenutniKorak() / rupa.getBrojKoraka();
		int x = rupa.getWidth() / 2;
		int y = rupa.getHeight() / 2;
		int sirina = (int)(rupa.getWidth() * procenat);
		int visina = (int)(rupa.getHeight() * procenat);
		
		g.fillOval(x - sirina/2, y - visina/2, sirina, visina);
	}

	@Override
	public void efekatUdarena() {
		super.efekatUdarena(); //da bi se stavilo udarena=true;
		rupa.zaustavi();
	}

	@Override
	public void efekatPobegla() {
		rupa.getBasta().ukradiPovrce();
	}
	
}
