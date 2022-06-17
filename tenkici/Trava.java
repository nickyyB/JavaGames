package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Trava extends Polje {
	public Trava(Mreza mreza) {
		super(mreza);
	}

	@Override
	public boolean dozvoljeno(Figura f) {
		return true;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
