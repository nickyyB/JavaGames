package igra;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Basta extends Panel implements Runnable {
	private Thread motor;
	private int povrce;
	private long interval;
	private int brojKoraka;
	private int vrsta, kolona;
	private Rupa rupe[][];
	private MouseListener listener;
	private Igra igra;
	
	public Basta(long interval, int brojKoraka, int vrsta, int kolona, Igra igra) {
		this.povrce = 100;
		this.interval = interval;
		this.brojKoraka = brojKoraka;
		this.vrsta = vrsta;
		this.kolona = kolona;
		this.igra = igra;
		
		setBackground(Color.GREEN);
		setLayout(new GridLayout(vrsta, kolona, 10, 10));
		
		rupe = new Rupa[vrsta][kolona];
		for (int i = 0; i<vrsta; i++) {
			for (int j = 0; j<kolona; j++) {
				rupe[i][j] = new Rupa(this);
				rupe[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						/*for (int i=0; i<vrsta; i++) //test
							for (int j=0; j<kolona; j++)
								if (e.getComponent() == rupe[i][j]) {
									Zivotinja z = ((Rupa)e.getComponent()).getZivotinja();
									System.out.println("Udarena " + i + "," + j + " z: " + z);
								}
						*/
						((Rupa)e.getComponent()).zgazi();
					}
					
				});
				add(rupe[i][j]);
			}
		}
	}
	
	public synchronized int getBrojKoraka() {
		return brojKoraka;
	}
	
	public synchronized void setPovrce(int povrce) {
		this.povrce = povrce;
	}
	
	public synchronized int getPovrce() {
		return povrce;
	}

	public synchronized void setBrojKoraka(int brojKoraka) {
		this.brojKoraka = brojKoraka;
		for (int i = 0; i<vrsta; i++) {
			for (int j = 0; j<kolona; j++) {
				rupe[i][j].setBrojKoraka(brojKoraka);
			}
		}
	}

	public synchronized void setInterval(long interval) {
		this.interval = interval;
	}

	@Override
	public void run() {
		Random random = new Random(System.currentTimeMillis());
		try {
			while (!motor.interrupted()) {
				int vr = random.nextInt(vrsta);
				int kol = random.nextInt(kolona);
				
				rupe[vr][kol].setZivotinja(new Krtica(rupe[vr][kol]));
				rupe[vr][kol].setBrojKoraka(brojKoraka);
				rupe[vr][kol].stvori();
				rupe[vr][kol].pokreni();
				
				motor.sleep(interval);
				interval = (long)(interval * 0.99);
				
				//ovo nije sigurno da treba, nekad udarena krtica ostane na slici
				synchronized(this) { wait(); } //cekam da mi javi da je rupa slobodna
			}
		} catch (InterruptedException e) {}
	}

	public synchronized void rupaSlobodna() { 
		//nije sigurno da treba
		notifyAll(); 
	}
	
	public synchronized void ukradiPovrce() { 
		povrce--; 
		igra.azurirajPovrce(povrce);
	}
	
	public void pokreni() { 
		motor = new Thread(this);
		motor.start(); 
	}
	public void zaustavi() { 
		if (motor != null) {
			motor.interrupt(); 
		}
	}
}
