package igra;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame implements ActionListener {
	private Mreza mreza;
	private int poena = 0;
	private TextField brojNovcicaTextField;
	private Label brojPoenaLabel;
	private Button pocniButton;
	private Checkbox trava, zid;
	private CheckboxGroup grupa = new CheckboxGroup();
	
	public Igra() {
		super("Igra");
		setBounds(100, 100, 600, 500);
		
		MenuBar menuBar = new MenuBar();
		Menu rezim = new Menu("Rezim");
		rezim.add("Rezim izmena");
		rezim.add("Rezim igranje");
		rezim.addActionListener(this);
		menuBar.add(rezim);
		setMenuBar(menuBar);
		
		mreza = new Mreza(this);
		add(mreza, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				mreza.zavrsi();
				dispose();
			}
			
		});
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (mreza == null) return;
				if (mreza.getIgrac() == null) return;
				
				Polje trenutno = mreza.getIgrac().getPolje();
				int [] pozicija = trenutno.getPozicija();
				//System.out.println(pozicija[0] + "" + pozicija[1]);
				Polje novo = null;
				//System.out.println(e.getKeyChar());
				switch (e.getKeyChar()) {
				case 'a': //levo
					novo = trenutno.udaljeno(0, -1);
					break;
				case 'd': //desno
					novo = trenutno.udaljeno(0, +1);
					break;
				case 'w': //gore
					novo = trenutno.udaljeno(-1, 0);
					break;
				case 's': //dole
					novo = trenutno.udaljeno(+1, 0);
					break;
				}
				
				if (novo != null && novo.dozvoljeno(mreza.getIgrac())) {
					mreza.getIgrac().pomeri(novo);
					trenutno.repaint();
					novo.repaint();
				}
			}
			
		});
		
		Panel jug = new Panel();
		jug.add(new Label("Novcica: "));
		jug.add(brojNovcicaTextField = new TextField("12"));
		if (mreza.getRezimIgranja() == false) brojNovcicaTextField.setEnabled(false);
		brojPoenaLabel = new Label("Broj poena: " + poena);
		jug.add(brojPoenaLabel);
		pocniButton = new Button("Pocni");
		final Frame prozor = this;
		pocniButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Pocni")) {
					int brojNovcica = 0;
					try {
						brojNovcica = Integer.parseInt(brojNovcicaTextField.getText());
						mreza.inicijalizuj(brojNovcica);
						//brojNovcicaTextField.setEnabled(false);
						((Button)e.getSource()).setLabel("Stani");
						prozor.setFocusable(true);
						prozor.requestFocus();
					} catch (NumberFormatException ne) {}
				}
				if (e.getActionCommand().equals("Stani")) {
					((Button)e.getSource()).setLabel("Pocni");
					mreza.zavrsi();
					//brojNovcicaTextField.setEnabled(false);
				}
			}
		});
		if (mreza.getRezimIgranja() == false) pocniButton.setEnabled(false);
		jug.add(pocniButton);
		add(jug, BorderLayout.SOUTH);
		
		Panel istokVeliki = new Panel();
		istokVeliki.setLayout(new GridLayout(1, 2));
			Panel istokLevi = new Panel();
			istokLevi.setLayout(new GridLayout(1, 1));
			istokLevi.add(new Label("Podloga:"));
			
			istokVeliki.add(istokLevi);
			Panel istokDesni = new Panel();
			istokDesni.setLayout(new GridLayout(2, 1));
				Panel gore = new Panel();
				gore.setBackground(Color.GREEN);
				gore.setLayout(new GridLayout(1, 1));
				trava = new Checkbox("Trava", true, grupa);
				if (mreza.getRezimIgranja() == true) trava.setEnabled(false);
				gore.add(trava);
				istokDesni.add(gore);
				
				Panel dole = new Panel();
				dole.setBackground(Color.LIGHT_GRAY);
				dole.setLayout(new GridLayout(1, 1));
				zid = new Checkbox("Zid", false, grupa);
				if (mreza.getRezimIgranja() == true) zid.setEnabled(false);
				dole.add(zid);
				istokDesni.add(dole);
			istokVeliki.add(istokDesni);
		add(istokVeliki, BorderLayout.EAST);
		
		
		invalidate();
		setResizable(false);
		setVisible(true);

		setFocusable(true);
		requestFocus();
	}
	
	public Checkbox getTrava() {
		return trava;
	}

	public Checkbox getZid() {
		return zid;
	}
	
	public Button getPocniButton() {
		return pocniButton;
	}

	public int getPoena() {
		return poena;
	}

	public void setPoena(int poena) {
		this.poena = poena;
		if (brojPoenaLabel != null) brojPoenaLabel.setText("Broj poena: " + poena);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Rezim izmena")) {
			mreza.setRezimIgranja(false);
			pocniButton.setEnabled(false);
			brojNovcicaTextField.setEnabled(false);
			zid.setEnabled(true);
			trava.setEnabled(true);
		}
		if (e.getActionCommand().equals("Rezim igranje")) {
			mreza.setRezimIgranja(true);
			pocniButton.setEnabled(true);
			brojNovcicaTextField.setEnabled(true);
			zid.setEnabled(false);
			trava.setEnabled(false);
		}		
	}

	public static void main(String [] args) {
		new Igra();
	}
}
