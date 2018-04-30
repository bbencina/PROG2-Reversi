package Logika;

import java.util.Scanner;

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
	
	
	public Igra() {
		plosca = new Plosca();
		
		igralecNaPotezi = Igralec.BLACK;
	}
	
	/**
	 * Glavna metoda igre. Požene se v main metodi.
	 * Odgovorna za igranje igre.
	 */
	public void igrajSe(){
		while (true) {
			/**
			 * Blok, odgovoren za izvedbo poteze in nastavitev novega stanja igre.
			 */
			Stanje stanje;
			//preveri, ali za trenutnega igralca obstajajo poteze in hkrati nastavi možne poteze igralcu.
			if (this.obstajaPoteza(plosca, igralecNaPotezi)) {
				Poteza poteza = null;
				Scanner in = new Scanner(System.in);
				// Prebere katero od možnih potez bi igralec rad odigral...
				// (Izbira poteze tu je le simbolična, kasneje se bo gledal klik na polje -> koordinate.)
				while (poteza == null) {
					int vrstica, stolpec;
					System.out.println("Vnesi vrstico: ");
					vrstica = in.nextInt();
					System.out.println("Vnesi stolpec: ");
					stolpec = in.nextInt();
					poteza = this.izberiPotezo(this.igralecNaPotezi, vrstica, stolpec);
				}
				in.close();
				// ...jo opravi...
				poteza.opraviPotezo();
				// ...nato pa nastavi števec neveljavnih nazaj na 0, da se igra ne konča predčasno.
				this.igralecNaPotezi.zaporedneNeveljavne = 0;
				// Preveri stanje igre, tudi zamenja igralca na potezi.
				stanje = this.stanje();
			}
			else {
				// Posodobi število neveljavnih in zamenja igralca.
				this.igralecNaPotezi.zaporedneNeveljavne++;
				// Preveri stanje igre, tudi zamenja igralca na potezi.
				stanje = this.stanje();
			}
			
			/**
			 * Blok, ki po opravljeni potezi obravnava stanje igre.
			 * System.out klici so le placeholderji; razmisliti bo treba, na kakšen način razglasiti zmagovalca,
			 * da bo uporabniški vmesnik lahko to prebral.
			 * 
			 * Ideja: namesto tipa void, lahko ta funkcija vrne stanje ob koncu igre.
			 */
			if (stanje == Stanje.NEODLOCENO) {
				System.out.println("Igra je neodločena!");
				return;
			} else if (stanje == Stanje.ZMAGA_BLACK) {
				System.out.println("Zmagal je črni igralec!");
				return;
			} else if (stanje == Stanje.ZMAGA_WHITE) {
				System.out.println("Zmagal je beli igralec!");
				return;
			} else {
				continue;
			}
		}
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
	 * Kliče jo metoda obstajaPoteza.
	 * Paziti je treba, da se ta metoda res pokliče, drugače bo zmeda.
	 */
	public void veljavnePoteze(Plosca plosca, Igralec igralec) {
		for (int i = 0; i < Plosca.velikost; i++) {
			for (int j = 0; j < Plosca.velikost; j++) {
				Polje trenutnoPolje = plosca.polje[i][j];
				if (trenutnoPolje.ploscek != null) {
					Poteza poteza = new Poteza(plosca, igralec, trenutnoPolje);
					if (poteza.jeVeljavna()) {
						igralec.moznePoteze.add(poteza);
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
		return igralec.moznePoteze.size() != 0;
	}
	
	/**
	 * @param igralec
	 * @param vrstica
	 * @param stolpec
	 * @return poteza p: trenutno veljavna poteza, če obstaja; null sicer.
	 */
	public Poteza izberiPotezo(Igralec igralec, int vrstica, int stolpec) {
		for (Poteza p : igralec.moznePoteze) {
			
			assert(p.igralec == igralec);
			
			if (p.polje.vrstica == vrstica && p.polje.stolpec == stolpec) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * @return trenutno stanje igre
	 * Metoda najprej preveri, ali je kateri od igralcev slučajno zmagal, sicer vrne kdo je na potezi.
	 * POMEMBNO: Metoda tudi zamenja igralca na potezi (zato tega ni treba delati ročno)!
	 */
	public Stanje stanje() {
		if (Igralec.BLACK.zaporedneNeveljavne == 2 || Igralec.WHITE.zaporedneNeveljavne == 2) {
			int [] rezultat = prestejPoBarvah(plosca);
			if (rezultat[0] == rezultat[1]) return Stanje.NEODLOCENO;
			else return (rezultat[0] < rezultat[1] ? Stanje.ZMAGA_WHITE : Stanje.ZMAGA_BLACK);	
		} else {
			igralecNaPotezi.naslednji();
			return (igralecNaPotezi == Igralec.BLACK ? Stanje.NA_POTEZI_BLACK : Stanje.NA_POTEZI_WHITE);
		}
	}

}
