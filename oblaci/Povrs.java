package oop2lab3siz42012;

import java.awt.*;

public abstract class Povrs extends Canvas {
    public int getSirina() { return super.getWidth(); }
    public int getVisina() { return super.getHeight(); }
    public abstract void pala(Kap k);
}
