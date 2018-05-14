package logika;

import java.util.HashSet;
import java.util.Scanner;

public class Igra {
	
	/**
	 * Igralec trenutno na potezi.
	 * (Zaradi konstantnega spreminjanja barv ploščkov se ne splača računati.)
	 */
	private Igralec igralecNaPotezi;

	/**
	 * Plošča lastna tej igri.
	 */
	private Plosca plosca;

	public Stanje stanjeIgre;
	
	public int zaporedneNeveljavne;
	
	
	public Igra() {
		plosca = new Plosca();
		zaporedneNeveljavne = 0;
		
		igralecNaPotezi = Igralec.BLACK;
		stanjeIgre = Stanje.NA_POTEZI_BLACK;
		
	}
	
	public Igra(Igra igra) {
		igralecNaPotezi = igra.igralecNaPotezi;
		zaporedneNeveljavne = igra.zaporedneNeveljavne;
		stanjeIgre = igra.stanjeIgre;
		
		plosca = new Plosca();
		
		for (int i = 0; i < Plosca.velikost; i++) {
			for (int j = 0; j < Plosca.velikost; j++) {
				plosca.polje[i][j] = igra.plosca.polje[i][j];
			}
		}
	}
	
	public Plosca getPlosca() {
		return plosca;
	}
	
	public Igralec getIgralecNaPotezi() {
		return igralecNaPotezi;
	}
	
	public boolean igrajPotezo(Poteza p) {
		System.out.println("Poteza je v logika/Igra");
		if (!this.obstajaPoteza()) {
			System.out.println("Ne obstaja poteza...");
			this.zaporedneNeveljavne++;
			this.stanjeIgre = this.stanje();
			return true;
		}
		for (Poteza veljavnaP : this.veljavnePoteze()) {
			if (veljavnaP.jeEnaka(p)) {
				System.out.println("Poteza se bo izvedla...");
				p.opraviPotezo();
				this.zaporedneNeveljavne = 0;
				this.stanjeIgre = this.stanje();
				if(!this.obstajaPoteza()){
					this.zaporedneNeveljavne++;
					this.stanjeIgre = this.stanje();
					if(!this.obstajaPoteza()){
						this.zaporedneNeveljavne++;
						this.stanjeIgre = this.stanje();
					}
				}
				return true;
			}
		}
		System.out.println("igrajPotezo vrača false..");
		return false;
	}
	
	protected void igrajSe(){
		// Začasne spremenljivke:
		int zaporednaPoteza = 0;
		
		
		/**
		 * Blok, odgovoren za izvedbo poteze in nastavitev novega stanja igre.
		 */
		while (true) {
			/**
			 * Vmesen blok, namenjen izpisovanju igre v konzolo.
			 */
			for (int i = 0; i < Plosca.velikost; i++) {
				System.out.print("*");
			}
			System.out.print("\n");
			
			System.out.println("Zaporedna poteza: " + zaporednaPoteza);
			zaporednaPoteza++;
			
			System.out.print("Igralec na potezi: ");
			if (igralecNaPotezi == Igralec.BLACK) {
				System.out.println("BLACK");
			} else if (igralecNaPotezi == Igralec.WHITE) {
				System.out.println("WHITE");
			} else {
				System.out.println("PLAYER_ERROR");
			}
			
			this.plosca.izpisiSe();
			
			for (int i = 0; i < Plosca.velikost; i++) {
				System.out.print("*");
			}
			System.out.print("\n");
			
			//preveri, ali za trenutnega igralca obstajajo poteze in hkrati nastavi možne poteze igralcu.
			if (this.obstajaPoteza()) {
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
					poteza = this.izberiPotezo(vrstica, stolpec);
				}
				in.reset();
				
				// ...jo opravi...
				poteza.opraviPotezo();
				// ...nato pa nastavi števec neveljavnih nazaj na 0, da se igra ne konča predčasno.
				this.zaporedneNeveljavne = 0;
				// Preveri stanje igre, tudi zamenja igralca na potezi.
				stanjeIgre = this.stanje();
				System.out.println(stanjeIgre);
			}
			else {
				// Posodobi število neveljavnih in zamenja igralca.
				this.zaporedneNeveljavne++;
				// Preveri stanje igre, tudi zamenja igralca na potezi.
				stanjeIgre = this.stanje();
				System.out.println(stanjeIgre);
			}
			
			
			
			/**
			 * Blok, ki po opravljeni potezi obravnava stanje igre.
			 * System.out klici so le placeholderji; razmisliti bo treba, na kakšen način razglasiti zmagovalca,
			 * da bo uporabniški vmesnik lahko to prebral.
			 * 
			 * Ideja: namesto tipa void, lahko ta funkcija vrne stanje ob koncu igre.
			 */
			if (stanjeIgre == Stanje.NEODLOCENO) {
				System.out.println("Igra je neodločena!");
				return;
			} else if (stanjeIgre == Stanje.ZMAGA_BLACK) {
				System.out.println("Zmagal je črni igralec!");
				return;
			} else if (stanjeIgre == Stanje.ZMAGA_WHITE) {
				System.out.println("Zmagal je beli igralec!");
				return;
			} else {
				continue;
			}
		}
	}
	
	/**
	 * @param plosca
	 * @param igralec
	 * V množico moznePoteze doda vse poteze, ki jih lahko igralec izvede.
	 * Kliče jo metoda obstajaPoteza.
	 * Paziti je treba, da se ta metoda res pokliče, drugače bo zmeda.
	 */
	public HashSet<Poteza> veljavnePoteze() {
		HashSet<Poteza> moznePoteze = new HashSet<Poteza>();
		for (int i = 0; i < Plosca.velikost; i++) {
			for (int j = 0; j < Plosca.velikost; j++) {
				if (plosca.polje[i][j] == Polje.PRAZNO) {
					Poteza poteza = new Poteza(plosca, igralecNaPotezi, i, j);
					if (poteza.jeVeljavna()) {
						moznePoteze.add(poteza);
					}
				}
			}
		}
		return moznePoteze;
	}
	
	/**
	 * @param plosca
	 * @param igralec
	 * @return true, če ima igralec na razpolago vsaj eno veljavno potezo.
	 */
	public boolean obstajaPoteza(){
		return veljavnePoteze().size() != 0;
	}
	
	/**
	 * @param igralec
	 * @param vrstica
	 * @param stolpec
	 * @return poteza p: trenutno veljavna poteza, če obstaja; null sicer.
	 */
	private Poteza izberiPotezo(int vrstica, int stolpec) {
		for (Poteza p : veljavnePoteze()) {
			
			if (p.vrstica == vrstica && p.stolpec == stolpec) {
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
		if (this.zaporedneNeveljavne >= 2) {
			int [] rezultat = this.plosca.prestejPoBarvah();
			if (rezultat[0] == rezultat[1]) return Stanje.NEODLOCENO;
			else return (rezultat[0] < rezultat[1] ? Stanje.ZMAGA_WHITE : Stanje.ZMAGA_BLACK);	
		} else {
			igralecNaPotezi = igralecNaPotezi.naslednji();
			return (igralecNaPotezi == Igralec.BLACK ? Stanje.NA_POTEZI_BLACK : Stanje.NA_POTEZI_WHITE);
		}
	}

}
