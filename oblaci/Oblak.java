package oop2lab3siz42012;

public class Oblak extends Thread {
    private long min, max;
    private Povrs povrs;
    private boolean radi;
    public final float MINQ = 8, MAXQ = 16; //ovo po tekstu zadatka treba da bude 2 i 8
    
    public Oblak(long minvr, long maxvr, Povrs p) {
        min = minvr;
        max = maxvr;
        povrs = p;
        radi = false;
        start();
    }
    
    public void run() {
        try {
            while (!interrupted()) {
                synchronized (this) { if (!radi) wait(); }
                java.util.Random random = new java.util.Random();
                sleep(Math.abs(random.nextLong())%(max-min+1)+min);
                int x = random.nextInt(povrs.getSirina());
                int y = random.nextInt(povrs.getVisina());
                float q = (float)Math.random()*(MAXQ-MINQ) + MINQ;
                Kap kap = new Kap(x, y, q);
                povrs.pala(kap);
            }
        } catch (InterruptedException ie) {}
    }
    
    public synchronized void kreni() { radi = true; notify(); }
    public synchronized void stani() { radi = false; }
    public void zavrsi() { interrupt(); }
}
