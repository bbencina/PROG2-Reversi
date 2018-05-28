package logika;

public class Poteza {
	
	public int vrstica, stolpec;
	
	/**
	 * Koordinate polja, na katerem se izvaja poteza.
	 * @param vrstica
	 * @param stolpec
	 */
	public Poteza(int vrstica, int stolpec) {
		this.vrstica = vrstica;
		this.stolpec = stolpec;
	}

	@Override
	public String toString() {
		return "Poteza [vrstica=" + vrstica + ", stolpec=" + stolpec + "]";
	}

}