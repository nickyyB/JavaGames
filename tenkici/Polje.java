package igra;

import java.awt.Canvas;
import java.awt.Graphics;

public abstract class Polje extends Canvas {
	protected Mreza mreza;
	
	public Polje(Mreza mreza) {
		this.mreza = mreza;
	}

	public Mreza getMreza() {
		return mreza;
	}

	public int[] getPozicija() {
		if (mreza == null) {
			return null;
		} else {
			return mreza.odrediPoziciju(this);
		}
	}
	
	public Polje udaljeno(int vr, int kol) {
		return mreza.vratiUdaljenoPolje(this, vr, kol);
	}

	public abstract boolean dozvoljeno(Figura f);	
}
