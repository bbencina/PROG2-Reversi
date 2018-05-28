package inteligenca;

import logika.Poteza;

public class OcenjenaPoteza {
	protected Poteza poteza;
	protected int vrednost;
	
	public OcenjenaPoteza(Poteza poteza, int vrednost){
		this.poteza = poteza;
		this.vrednost = vrednost;
	}

	@Override
	public String toString() {
		return "OcenjenaPoteza [poteza=" + poteza + ", vrednost=" + vrednost + "]";
	}
	
	
}
