package igra;

public abstract class Figura {
	protected Polje polje;

	public Figura(Polje polje) {
		super();
		this.polje = polje;
	}

	public Polje getPolje() {
		return polje;
	}
	
	public void pomeri(Polje p) {
		if (p != null) {
			polje = p;
			//iscrtaj();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Figura)) throw new RuntimeException("Objekat mora da bude figura!");
		return this.polje == ((Figura)obj).polje;
	}
	
	public abstract void iscrtaj();
}
