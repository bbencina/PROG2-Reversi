package Logika;

public class Polje {
	
	protected int vrstica, stolpec;
	
	/**
	 * Plošček lasten temu polju (črn ali bel).
	 * Če plošček = null, je polje prazno.
	 */
	public Ploscek ploscek;
	
	public Polje(int vrstica, int stolpec){
		this.vrstica = vrstica;
		this.stolpec = stolpec;
		
		this.ploscek = null;
	}
	
	public boolean jePrazno(){
		return this.ploscek == null;
	}

}
