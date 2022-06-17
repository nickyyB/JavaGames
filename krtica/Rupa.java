package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Rupa extends Canvas implements Runnable {
	private Thread motor;
	private Basta basta;
	private Zivotinja zivotinja;
	private boolean radi;
	private int brojKoraka, trenutniKorak;
	
	public Rupa(Basta basta) {
		this.basta = basta;
		this.zivotinja = null;
		this.motor = null;
		this.radi = false;
		this.brojKoraka = 0;
		this.trenutniKorak = 0;
	}
	
	public Basta getBasta() {
		return basta;
	}
	
	public synchronized Zivotinja getZivotinja() {
		return zivotinja;
	}

	public synchronized void setZivotinja(Zivotinja zivotinja) {
		this.zivotinja = zivotinja;
	}
	
	public synchronized int getTrenutniKorak() {
		return trenutniKorak;
	}
	
	public synchronized int getBrojKoraka() {
		return brojKoraka;
	}
	
	public synchronized void setBrojKoraka(int brojKoraka) {
		this.brojKoraka = brojKoraka;
	}

	public synchronized void zgazi() {
		if (zivotinja != null) {
			zivotinja.efekatUdarena();
		}
	}

	@Override
	public void run() {
		try {
			//while (!motor.interrupted()) {
			repaint();
				
			motor.sleep(2000);
			zaustavi();
			repaint();
			//}
		} catch (InterruptedException e) {}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(101, 67, 33));
		g.fillRect(0, 0, getWidth()-1, getHeight()-1);
	
		if (zivotinja != null) {
			synchronized (this) {
				for (trenutniKorak=0; trenutniKorak<brojKoraka; trenutniKorak++) {
					try {
						motor.sleep(100);
					} catch (InterruptedException e) { motor.interrupt(); return; }
				
					if (zivotinja != null)
						zivotinja.paint(this);
					}
				}
			}
	}
	
	public synchronized boolean pokrenuta() { return radi; }
	
	public synchronized void stvori() {
		if (motor != null) {
			zaustavi();
		}
		motor = new Thread(this);
	}
	
	public synchronized void pokreni() {
		if (motor != null) {
			motor.start();
			radi = true;
		}
	}
	
	public synchronized void zaustavi() {
		motor.interrupt();
		motor = null;
		radi = false;
		if (zivotinja != null) {
			if (zivotinja.getUdarena() == false) {
				zivotinja.efekatPobegla();
			}
			zivotinja = null;
		}
		basta.rupaSlobodna();
		repaint();
	}
}
