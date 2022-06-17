package igra;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame implements ActionListener{
	private static Igra instance = null; //Singleton, lazy instantialization
	
	private Basta basta;
	private Rupa rupa;
	
	private static final String tezinaNaslov[] = { "Lako", "Srednje", "Tesko" };
	private Checkbox tezina[];
	private Button kreni;
	private Label kolPovrca;	
	
	private Igra() {
		super("Igra");
		setBounds(100, 100, 500, 400);
		
		basta = new Basta(1000, 10, 4, 4, this);
		add(basta, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (basta != null) {
					basta.zaustavi();
				}
				dispose();
			}
		});
		
		Panel istok = new Panel();
		istok.setLayout(new GridLayout(2, 1));
				
		Panel gornji = new Panel();
		gornji.setLayout(new GridLayout(5, 1));
		Label tezinaLabel = new Label("Tezina: ");
		Font myFont = new Font("Serif", Font.BOLD, 14);
		tezinaLabel.setFont(myFont);
		tezinaLabel.setAlignment(Label.CENTER);
		gornji.add(tezinaLabel);
		
		CheckboxGroup grupa = new CheckboxGroup();
		tezina = new Checkbox[3];
		for (int i=0; i<3; i++) {
			tezina[i] = new Checkbox(tezinaNaslov[i], grupa, i==0); //nulti je setovan na true
			gornji.add(tezina[i]);
		}
		
		kreni = new Button("Kreni");
		kreni.addActionListener(this);
		gornji.add(kreni);
		
		istok.add(gornji);
		
		Panel donji = new Panel();
		donji.setLayout(new GridLayout(5, 1));
		donji.add(new Label(""));
		donji.add(new Label(""));
		kolPovrca = new Label("Povrce:0");
		myFont = new Font("Serif", Font.BOLD, 18);
		kolPovrca.setFont(myFont);
		kolPovrca.setAlignment(Label.CENTER);
		donji.add(kolPovrca);
		istok.add(donji);
		
		add(istok, BorderLayout.EAST);
		
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Kreni")) {
			int i = 0;
			for (i=0; i<3; i++) {
				if (tezina[i].getState()) break;
			}
			long interval = 1000;
			int brojKoraka = 10;
			
			switch (i) {
			case 0:
				interval = 1000;
				brojKoraka = 10;
				break;
			case 1:
				interval = 750;
				brojKoraka = 8;
				break;
			case 2:
				interval = 500;
				brojKoraka = 6;
				break;
			}
			
			basta.setInterval(interval);
			basta.setBrojKoraka(brojKoraka);
			basta.setPovrce(100);
			
			//revalidate();
			basta.pokreni(); //uzeti parametre igre, napraviti bastu i pokrenuti
			
			kolPovrca.setText("Povrce:" + basta.getPovrce());
			
			kreni.setLabel("Stani");
		}
		if (e.getActionCommand().equals("Stani")) {
			basta.zaustavi();
			
			kreni.setLabel("Kreni");
		}
	}
	
	public void azurirajPovrce(int kol) {
		if (kol <= 0) kol = 0;
		kolPovrca.setText("Povrce:" + kol);
		if (kol == 0) {
			basta.zaustavi();
			kreni.setLabel("Kreni");
		}
	}

	public static Igra getInstance() {
		if (instance == null) {
			synchronized (Igra.class) { //double locking Singleton
				if (instance == null) {
					instance = new Igra();
				}
			}
		}
		return instance;
	}
	
	public static void main(String [] args) {
		Igra.getInstance();
	}
}
