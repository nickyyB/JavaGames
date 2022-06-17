package oop2lab3siz42012;

import java.util.LinkedList;
import java.awt.*;

public class Bara extends Povrs implements Runnable {
    private LinkedList<Talas> lista;
    
    public Bara() {
        lista = new LinkedList<Talas>();
    }
    
    public void pala(Kap k) {
        Talas t = new Talas(k.getX(), k.getY(), (float)0.05*k.getQ(), (float)0.05/k.getQ());
        synchronized (this) { lista.add(t); }
        t.start();
    }
    
    public void paint(Graphics g) {
        //g.setColor(Color.WHITE);
        //g.fillRect( 0, 0, getSirina(), getVisina());
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Thread.sleep(40);                
                repaint(); //poziva moj paint                
                for (int i=0; i<lista.size(); i++) {
                    lista.get(i).paint(this.getGraphics());                    
                    if (lista.get(i).getRGB() >= 1) { lista.get(i).zavrsi(); lista.remove(i); }
                }
            }
        } catch (InterruptedException ie) {}
    }
    
    public void zavrsi() {
        for (int i=0; i<lista.size(); i++)
            lista.get(i).zavrsi();        
    }
}
