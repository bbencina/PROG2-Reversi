package Logika;

import java.util.HashSet;

public class Stanje {

	private HashSet<Poteza> moznePoteze;
	
	public Stanje() {
	}
	
	/**
	 * @param plosca
	 * @return par števil, prvo je število črnih in drugo število belih ploščkov na podani plošči.
	 */
	public static int[] prestejPoBarvah(Plosca plosca) {
		int[] steviloPlosckov = new int[2];
		
		int black = 0, white = 0;
		
		for (int i = 0; i < Plosca.velikost; i++) {
			for (int j = 0; j < Plosca.velikost; j++) {
				if (plosca.polje[i][j].ploscek == Ploscek.BLACK) black++;
				if (plosca.polje[i][j].ploscek == Ploscek.WHITE) white++;
			}
		}
		
		steviloPlosckov[0] = black;
		steviloPlosckov[1] = white;
		
		return steviloPlosckov;
	}
	
	/**
	 * @param plosca
	 * @param igralec
	 * V množico moznePoteze doda vse poteze, ki jih lahko igralec izvede.
	 */
	public void veljavnePoteze(Plosca plosca, Igralec igralec) {
		for (int i = 0; i < Plosca.velikost; i++) {
			for (int j = 0; j < Plosca.velikost; j++) {
				Polje trenutnoPolje = plosca.polje[i][j];
				if (trenutnoPolje.ploscek != null) {
					Poteza poteza = new Poteza(plosca, igralec, trenutnoPolje);
					if (poteza.jeVeljavna()) {
						moznePoteze.add(poteza);
					}
				}
			}
		}
	}
	
	/**
	 * @param plosca
	 * @param igralec
	 * @return true, če ima igralec na razpolago vsaj eno veljavno potezo.
	 */
	public boolean obstajaPoteza(Plosca plosca, Igralec igralec){
		veljavnePoteze(plosca, igralec);
		return this.moznePoteze.size() != 0;
	}

}
