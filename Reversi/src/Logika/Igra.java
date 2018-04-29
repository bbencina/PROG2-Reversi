package Logika;

import java.util.HashSet;

public class Igra {
	/**
	 * Igralec trenutno na potezi.
	 * (Zaradi konstantnega spreminjanja barv ploščkov se ne splača računati.)
	 */
	public Igralec igralecNaPotezi;
	
	/**
	 * Plošča lastna tej igri.
	 */
	public Plosca plosca;
	
	private HashSet<Poteza> moznePoteze;
	
	/**
	* Števec zaporednih pozicij brez veljavnih potez.
	*/
	private int zaporedneNeveljavne;
	
	public Igra() {
		plosca = new Plosca();
		
		igralecNaPotezi = Igralec.BLACK;
		
		zaporedneNeveljavne = 0;
	}
	
	public void igrajSe(){
		/**
		 * Za igralca na potezi preveri, če ima na razpolago veljavne poteze. -metoda obstajaPoteza
		 * Če so, počaka, da igralec opravi potezo. -Poteza -metoda opraviPotezo
		 * Zamenja igralca na potezi. -Igralec -metoda naslednji()
		 * 
		 * Če nobeden od igralcev nima veljavnih potez - konec igre. -Stanje
		 * (Lahko preverjamo tako, da definiramo števec zaporednih neveljavnih potez - ko je enak 2, je igre konec)
		 * 
		 * Če je plošča polna -> ni veljavnih potez -že preverjeno.
		 * 
		 * Razglasi rezultat. -Stanje -Igralec
		 */
		
		//if obstajaPoteza
		
			//opraviPotezo
		
		igralecNaPotezi.naslednji();
		
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
	
	/**
	 * @return trenutno stanje igre
	 */
	public Stanje stanje() {
		// Ali imamo zmagovalca?
		if (zaporedneNeveljavne == 2) {
			int [] rezultat = prestejPoBarvah(plosca);
			if (rezultat[0] == rezultat[1]) return Stanje.NEODLOCENO;
			else return (rezultat[0] < rezultat[1] ? Stanje.ZMAGA_WHITE : Stanje.ZMAGA_BLACK);	
		} else {
			igralecNaPotezi.naslednji();
			return (igralecNaPotezi == Igralec.BLACK ? Stanje.NA_POTEZI_BLACK : Stanje.NA_POTEZI_WHITE);
		}
	}

}
