package oop2lab3siz42012;

import java.awt.*;

public class Talas extends Thread implements Prikaziva {
    public int x, y;
    public float dr, r, db, rgb;
    public Color boja;
    
    public Talas(int xx, int yy, float drr, float dbb) {
        x = xx; y = yy;
        r = 1; dr = drr; db = dbb;
        rgb = 0; 
        boja = new Color(rgb, rgb, rgb); //crna je 0.0, 0.0, 0.0
    }
    
    public float getRGB() { return rgb; }
    
    public void paint(Graphics g) {
        g.setColor(boja); //nova boja
        g.drawOval(x-(int)r/2, y-(int)r/2, (int)r, (int)r);
    }
    
    public void run() {
        try {
            while (!interrupted()) {
                sleep(40); //40 ms
                r += dr;
                rgb += db;
                if (rgb >= 1) break;
                boja = new Color(rgb, rgb, rgb);                
            }
        } catch (InterruptedException ie) {}
    }
    
    public void zavrsi() { interrupt(); }
}
