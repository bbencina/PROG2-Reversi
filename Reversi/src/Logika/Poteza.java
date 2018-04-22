package Logika;

public class Poteza {
	
	Igralec igralec;
	Polje polje;
	Plosca plosca;
	
	/**
	 * @param plosca - plosca, na kateri igralec poskuša narediti potezo;
	 * @param igralec - igralec, ki poskuša narediti potezo;
	 * @param polje - polje, na katerega igralec poskuša postaviti plošček;
	 */
	public Poteza(Plosca plosca, Igralec igralec, Polje polje) {
		this.igralec = igralec;
		this.polje = polje;
		this.plosca = plosca;
	}
	
	public boolean jeVeljavna(Poteza poteza) {
		//TODO
		return true;
	}
}
