package igra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tenk extends Figura implements Runnable {
	private Thread motor;
	private Random random;
	
	public Tenk(Polje polje) {
		super(polje);
		motor = new Thread(this);
		random = new Random(System.currentTimeMillis());
	}
	
	public void pokreni() { motor.start(); }
	public void zavrsi() { motor.interrupt(); }

	@Override
	public void run() {
		try {
			while (!motor.interrupted()) {
				motor.sleep(500);
				int vr = random.nextInt(3) - 1;
				int kol = random.nextInt(3) - 1;
				
				int [] pozicija = polje.getPozicija();
				Polje novo = polje.udaljeno(vr, kol);
				
				if (novo != null && novo.dozvoljeno(this)) {
					Polje staro = polje;
					pomeri(novo);
					staro.repaint();
				}
				
			}
		} catch (InterruptedException e) {}		
	}

	@Override
	public void iscrtaj() {
		//polje.repaint();
		if (polje != null) {
			//System.out.println(polje);
			Graphics g = polje.getGraphics();
			g.setColor(Color.BLACK);
			g.drawLine(0, 0, polje.getWidth()-1, polje.getHeight()-1);
			g.drawLine(polje.getWidth()-1, 0, 0, polje.getHeight()-1);
		}
	}
}