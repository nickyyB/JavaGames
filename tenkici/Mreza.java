package igra;

import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Random;

public class Mreza extends Panel implements Runnable {
	private Thread motor;
	private Polje [][] polje;
	private LinkedList<Figura> tenkovi;
	private LinkedList<Figura> novcici;
	private Igrac igrac;
	private Igra igra;
	private Random random;
	private boolean rezimIgranja;
	private int brojPoena;
	private MouseAdapter mouseAdapter;
	
	public Mreza(Igra ig) {
		this(17, ig);
	}
	
	public Mreza(int dimenz, Igra ig) {
		rezimIgranja = false; //treba da pocne od false
		
		random = new Random(System.currentTimeMillis());
		
		novcici = new LinkedList<Figura>();
		tenkovi = new LinkedList<Figura>();
		
		setLayout(new GridLayout(dimenz, dimenz));
		
		polje = new Polje[dimenz][dimenz];
		for (int i=0; i<dimenz; i++) {
			for (int j=0; j<dimenz; j++) {
				polje[i][j] = new Trava(this); //default objekat
				if (random.nextDouble() < 0.8) { //80%
					polje[i][j] = new Trava(this);
				} else { //20%
					polje[i][j] = new Zid(this);
				}

				final Mreza mreza = this;
				//mouselistener
				mouseAdapter = new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						if (rezimIgranja == true) return; //ne radi tokom igranja
									
						boolean zid = igra.getZid().getState();
						//System.out.println("Mis " + zid);
						for (int i=0; i<dimenz; i++) {
							for (int j=0; j<dimenz; j++) {
								mreza.remove(polje[i][j]);
							}
						}
						int [] poz = ((Polje)e.getSource()).getPozicija();
						//System.out.println(polje[poz[0]][poz[1]]);
						if (zid) { //farbaj u zid
							polje[poz[0]][poz[1]] = new Zid(mreza);							
						} else { //farbaj u travu
							polje[poz[0]][poz[1]] = new Trava(mreza);
						}						
						//System.out.println(polje[poz[0]][poz[1]]);
						polje[poz[0]][poz[1]].addMouseListener(mouseAdapter);
						
						for (int i=0; i<dimenz; i++) {
							for (int j=0; j<dimenz; j++) {
								mreza.add(polje[i][j]);								
								//polje[i][j].invalidate();
								//if (poz[0] == i && poz[1] == j) System.out.println(polje[i][j]);
							}
						}
						mreza.revalidate();
					}

				};
				polje[i][j].addMouseListener(mouseAdapter);
				add(polje[i][j]);
				//System.out.println(polje[i][j]);
			}
		}
		
		igra = ig;
	}
	
	public boolean getRezimIgranja() {
		return rezimIgranja;
	}

	public void setRezimIgranja(boolean rezimIgranja) {
		this.rezimIgranja = rezimIgranja;
	}

	public Igrac getIgrac() {
		return igrac;
	}

	private boolean poljeSlobodno(int i, int j) {
		Polje p = polje[i][j];
		
		for (Figura f : novcici) {
			if (f.polje == p) return false;
		}
		
		for (Figura f : tenkovi) {
			if (f.polje == p) return false;
		}
		
		if (igrac != null) {
			if (igrac.polje == p) return false;
		}
		
		return true;
	}
	
	public void inicijalizuj(int brojNovcica) {
		if (!rezimIgranja) return;
		
		igra.setPoena(0);
		
		int brojTenkova = brojNovcica / 3;
		int dimenzija = polje.length;
		int vrsta, kolona;
		
		novcici = new LinkedList<Figura>();
		for (int i=0; i<brojNovcica; i++) {
			vrsta = random.nextInt(dimenzija);
			kolona = random.nextInt(dimenzija);
			if (poljeSlobodno(vrsta, kolona) == false || polje[vrsta][kolona] instanceof Zid) {
				i--;
				continue; //ostaje da ponovo pravi novcic za isto polje
			}
			Novcic n = new Novcic(polje[vrsta][kolona]);
			//System.out.println(n);
			novcici.add(n);
		}
		
		for (int i=0; i<brojTenkova; i++) {
			vrsta = random.nextInt(dimenzija);
			kolona = random.nextInt(dimenzija);
			if (poljeSlobodno(vrsta, kolona) == false || polje[vrsta][kolona] instanceof Zid) {
				i--;
				continue; //ostaje da ponovo pravi novcic za isto polje
			}
			Tenk t = new Tenk(polje[vrsta][kolona]);
			t.pokreni();
			tenkovi.add(t);
		}
		
		do {
			vrsta = random.nextInt(dimenzija);
			kolona = random.nextInt(dimenzija);
		} while (poljeSlobodno(vrsta, kolona) == false || polje[vrsta][kolona] instanceof Zid);
		
		igrac = new Igrac(polje[vrsta][kolona]);
		
		brojPoena = 0;
		
		for (int i=0; i<polje.length; i++) {
			for (int j=0; j<polje.length; j++) {
				polje[i][j].revalidate();
			}
		}
		
		motor = new Thread(this);
		motor.start();
	}
	
	private void azuriraj() {
		//da li igrac uzeo novcic ili tenk ubio igraca
		for (int i=0; i<novcici.size(); i++)
			if (igrac.getPolje() == novcici.get(i).getPolje()) {
				igra.setPoena(igra.getPoena() + 1);
				novcici.remove(i);
			}
		
		if (novcici.size() == 0) { zavrsi(); }
		
		for (int i=0; i<tenkovi.size(); i++)
			if (igrac.getPolje() == tenkovi.get(i).getPolje()) {
				zavrsi();
			}
	}
	
	private void iscrtajFigureiPolja() {
		for (int i=0; i<polje.length; i++) {
			for (int j=0; j<polje.length; j++) {
				polje[i][j].invalidate();
			}
		}
		
		for (int i=0; i<novcici.size(); i++) {
			novcici.get(i).iscrtaj();
		}
		
		for (int i=0; i<tenkovi.size(); i++) {
			tenkovi.get(i).iscrtaj();
		}
		
		if (igrac != null) {
			igrac.iscrtaj();
		}
	}
	
	@Override
	public void run() {
		try {
			while (!motor.interrupted()) {
				motor.sleep(200);
				azuriraj();
				iscrtajFigureiPolja();
			}
		} catch (InterruptedException e) {}
	}



	public int [] odrediPoziciju(Polje p) {
		int [] result = new int[2];
		

		for (int i=0; i<polje.length; i++) {
			for (int j=0; j<polje[i].length; j++) {
				if (polje[i][j] == p) {
					result[0] = i;
					result[1] = j;
					return result;
				}
			}
		}
		return null;
	}
	
	public Polje vratiUdaljenoPolje(Polje p, int vr, int kol) {
		int [] pozicija = odrediPoziciju(p);
		if (pozicija[0] + vr < 0 || pozicija[0] + vr > polje.length-1) {
			return null;
		}
		if (pozicija[1] + kol < 0 || pozicija[1] + kol > polje.length-1) {
			return null;
		}
		return polje[pozicija[0] + vr][pozicija[1] + kol];
	}
	
	public void zavrsi() { 
		for (Figura f : tenkovi) {
			((Tenk)f).zavrsi();
		}
		novcici.clear();
		tenkovi.clear();
		for (int i=0; i<polje.length; i++)
			for (int j=0; j<polje.length; j++) {
				polje[i][j].repaint();
			}
		igrac = null;
		if (motor != null) {
			motor.interrupt(); 
		}
	}
}
