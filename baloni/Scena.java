package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("serial")
public class Scena extends Canvas implements Runnable {
	private Thread motor;
	private Igra igra;
	private LinkedList<KruznaFigura> figure;
	private Igrac igrac;
	private Random random;
	
	public Scena(Igra igra) {
		this.random = new Random(System.currentTimeMillis());
		this.igra = igra;
		this.figure = new LinkedList<KruznaFigura>();
	}
	
	public Igrac getIgrac() { return this.igrac; }
	
	@Override
	public void run() {
		try {
			while (!motor.interrupted()) {
				 motor.sleep(60);
				 
				 repaint();
				 
				 if (random.nextDouble() <= 0.1) {
					 Vektor centar = new Vektor((int)(getWidth()*random.nextDouble()), 20);
					 Vektor brzina = new Vektor(random.nextInt(10)-5, random.nextInt(20)+10);
					 synchronized (this) {
						 figure.add(new Balon(centar, Color.RED, 20, brzina, this));
					 }
				 }
				 
				 synchronized (this) {
					 for (int i=0; i<figure.size(); i++)
						 figure.get(i).istekaoPeriod();
					 
					 proveriSudar();
				 }
			}
		} catch (InterruptedException e) {}
	}
	
	void proveriSudar() {
		for (int i=0; i<figure.size() - 1; i++)
			for (int j=i+1; j<figure.size(); j++)
				if (figure.get(i).preklapaSe(figure.get(j))) {
					figure.get(i).sudaranje(figure.get(j));
					figure.get(j).sudaranje(figure.get(i));
				}
	}
	
	synchronized void inicijalizuj() {
		Vektor centar = new Vektor(this.getWidth()/2, this.getHeight()*9/10);
		Vektor brzina = new Vektor(0,0);
		figure.add(igrac = new Igrac(centar, 30, brzina, this));
	}
	
	public synchronized void izbaci(KruznaFigura f) {
		for (int i=0; i<figure.size(); i++)
			if (figure.get(i) == f) figure.remove(i);
	}
	
	public void pokreni() {
		inicijalizuj();
		motor = new Thread(this);
		motor.start();
	}
	
	public void zaustavi() { if (motor != null) motor.interrupt(); }
}
