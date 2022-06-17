package oop2lab3siz42012;

import java.awt.*;
import java.awt.event.*;

public class Main extends Frame implements ActionListener {
    private Bara bara;
    private Thread nit;
    private Oblak oblak;
    
    public Main() {
        super("Pljusak");
        setSize(400, 250);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                nit.interrupt();
                bara.zavrsi();
                oblak.zavrsi();
                dispose();
            }
        });
        
        bara = new Bara();
        nit = new Thread(bara);
        nit.start(); //bara je Runnable, pa pokrecemo na drugi nacin
        oblak = new Oblak(200, 500, bara);
        
        setLayout(new BorderLayout());
        
        add(BorderLayout.CENTER, bara);
        
        Panel jug = new Panel();
        jug.setLayout(new GridLayout(1,6));
        jug.setBackground(Color.LIGHT_GRAY);
        jug.add(new Label(""));
        jug.add(new Label(""));
        Button kreni = new Button("Kreni");
        kreni.addActionListener(this);
        jug.add(kreni);
        Button stani = new Button("Stani");        
        stani.addActionListener(this);
        jug.add(stani);
        jug.add(new Label(""));
        jug.add(new Label(""));
        add(BorderLayout.SOUTH, jug);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Kreni")) oblak.kreni();
        if (e.getActionCommand().equals("Stani")) oblak.stani();
    }
    
    public static void main(String [] args) {
        new Main();
    }
}
