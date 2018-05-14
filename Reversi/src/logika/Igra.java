package logika;

import java.util.HashSet;

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
		if (p.jeVeljavna()) {
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
		System.out.println("igrajPotezo vrača false..");
		return false;
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
