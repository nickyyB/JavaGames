package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {

	public Igrac(Polje polje) {
		super(polje);
	}

	@Override
	public void iscrtaj() {
		//polje.repaint();
		Graphics g = polje.getGraphics();
		g.setColor(Color.RED);
		g.drawLine(polje.getWidth()/2, 0, polje.getWidth()/2, polje.getHeight()-1);
		g.drawLine(0, polje.getHeight()/2, polje.getWidth()-1, polje.getHeight()/2);
	}
}
