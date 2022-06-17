package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Zid extends Polje {
	public Zid(Mreza mreza) {
		super(mreza);
	}
	
	@Override
	public boolean dozvoljeno(Figura f) {
		return false;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
