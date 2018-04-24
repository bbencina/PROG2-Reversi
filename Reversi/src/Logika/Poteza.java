package Logika;


public class Poteza {
	
	public Igralec igralec;
	public Polje polje;
	public Plosca plosca;
	
	private static Smer[] smer;
	
	{
		/**
		 * Nastavi seznam možnih smeri za kasnejše lažje preverjanje.
		 * Smeri je vedno (neodvisno od velikosti plošče) 8.
		 * Pogoj v zankah služi temu, da odpravi nesmiselno deveto smer, kjer se nikamor ne premaknemo.
		 * Nesmiselne smeri v odvisnosti od polja (vogal, rob, ena stran od roba) bo urejala funkcija jeVeljavna.
		 */
		int[] ds = {-1, 0, 1};
		smer = new Smer[8];
		int counter = 0;
		
		for (int dx : ds) {
			for (int dy : ds) {
				if (dx == 0 && dy == 0) {
					continue;
				} else {
					smer[counter] = new Smer(dx, dy);
					counter++;
				}
			}
		}
	}
	
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
		boolean successFlag = false;
		
		for (Smer s : smer) {
			//HERE
		}
		
		return true;
	}
}
