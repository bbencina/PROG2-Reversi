package logika;

import java.util.HashSet;
import java.util.LinkedList;

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
	
	
	// Moralo bi pisati private, a potrebujem public za jUnit teste.
	public HashSet<Smer> ugodneSmeri = new HashSet<Smer>();
	
	private static final Smer[] smer;
	
	static {
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
	
	
	public Igra() {
		plosca = new Plosca();
		
		igralecNaPotezi = Igralec.BLACK;
		
	}
	
	public Igra(Igra igra) {
		igralecNaPotezi = igra.igralecNaPotezi;
		
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
		if (jeVeljavna(p, true)) {
			opraviPotezo(p);
			if (! potezeIgralca(igralecNaPotezi.naslednji()).isEmpty()) {
				igralecNaPotezi = igralecNaPotezi.naslednji();
			}
			return true;
		}
		return false;
	}
	
	/**
	 * @param plosca
	 * @param igralec
	 * V množico moznePoteze doda vse poteze, ki jih lahko igralec izvede.
	 */
	public LinkedList<Poteza> veljavnePoteze() {
		LinkedList<Poteza> moznePoteze = new LinkedList<Poteza>();
		for (int i = 0; i < Plosca.velikost; i++) {
			for (int j = 0; j < Plosca.velikost; j++) {
				if (plosca.polje[i][j] == Polje.PRAZNO) {
					Poteza poteza = new Poteza(i, j);
					if (jeVeljavna(poteza, false)) {
						moznePoteze.add(poteza);
					}
				}
			}
		}
		return moznePoteze;
	}
	
	/**
	 * @param igralec
	 * @return množico potez, ki jih ima dani igralec na voljo
	 */
	public LinkedList<Poteza> potezeIgralca(Igralec igralec) {
		LinkedList<Poteza> poteze = new LinkedList<Poteza>();
		if (igralec == this.igralecNaPotezi) {
			poteze = veljavnePoteze();
		} else {
			// Začasno spremenimo igralca na potezi
			igralecNaPotezi = igralec;
			poteze = veljavnePoteze();
			// Spremenimo ga takoj nazaj, preden stanje to izve.
			igralecNaPotezi = igralec.naslednji();
		}
		return poteze;
	}
	
	/**
	 * @return trenutno stanje igre
	 * Metoda najprej preveri, ali je kateri od igralcev slučajno zmagal, sicer vrne kdo je na potezi.
	 * POMEMBNO: Metoda tudi zamenja igralca na potezi (zato tega ni treba delati ročno)!
	 */
	public Stanje stanje() {
		if (potezeIgralca(igralecNaPotezi).isEmpty()
			&& potezeIgralca(igralecNaPotezi.naslednji()).isEmpty()) {
			int [] rezultat = this.plosca.prestejPoBarvah();
			if (rezultat[0] == rezultat[1]) return Stanje.NEODLOCENO;
			else return (rezultat[0] < rezultat[1] ? Stanje.ZMAGA_WHITE : Stanje.ZMAGA_BLACK);	
		}
		return (igralecNaPotezi == Igralec.BLACK ? Stanje.NA_POTEZI_BLACK : Stanje.NA_POTEZI_WHITE);
	}

	
	/**
	 * Preveri veljavnost poteze in nastavi ugodne smeri, če obstajajo.
	 */
	public boolean jeVeljavna(Poteza p, boolean polniUgodne){
		boolean successFlag = false;
		
		int vrstica = p.vrstica, stolpec = p.stolpec;
		
		if (this.plosca.polje[p.vrstica][p.stolpec] != Polje.PRAZNO) return false;
		
		for (Smer s : smer) {
			int koef = 1;
			while (p.vrstica + koef * s.y >= 0 && p.stolpec + koef * s.x >= 0 &&
					p.vrstica + koef * s.y < Plosca.velikost && p.stolpec + koef * s.x < Plosca.velikost){
				vrstica = p.vrstica + koef * s.y;
				stolpec = p.stolpec + koef * s.x;
				if (this.plosca.polje[vrstica][stolpec] == Polje.PRAZNO){
					break;
				} else if (this.plosca.polje[vrstica][stolpec] == this.igralecNaPotezi.barva() && koef == 1){
					break;
				} else if (koef > 1 && this.plosca.polje[vrstica][stolpec] == this.igralecNaPotezi.barva()){
					if (polniUgodne) {
						ugodneSmeri.add(s);
						}
					successFlag = true;
					break;
				}
				koef++;
			}
		}
		return successFlag;
	}
	
	
	/**
	 * @param smeri - sprejme ugodne smeri
	 * Ta metoda dejansko opravi potezo (obrača ploščke).
	 * 
	 * Pomembno: ta metoda je striktno private.
	 */
	private void opraviPotezo(Poteza p){
		for (Smer s : ugodneSmeri){
			int koef = 1, vrstica = p.vrstica, stolpec = p.stolpec;
			//Najprej postavimo plošček na izbrano polje.
			if (this.igralecNaPotezi == Igralec.BLACK) {
				this.plosca.polje[p.vrstica][p.stolpec]= Polje.BLACK;
			} else {
				this.plosca.polje[p.vrstica][p.stolpec] = Polje.WHITE;
			}
			//Nato še obrnemo preostale ploščke, ki jih igralec odvzame nasprotniku.
			// Še enkrat dodan pogoj za ploščo, ker se pojavlja napaka.
			while (this.plosca.polje[p.vrstica + koef * s.y][p.stolpec + koef * s.x] == igralecNaPotezi.barva().nasprotno() &&
					p.vrstica + koef * s.y >= 0 && p.stolpec + koef * s.x >= 0 && 
					p.vrstica + koef * s.y < Plosca.velikost && p.stolpec + koef * s.x < Plosca.velikost){
				vrstica = p.vrstica + koef * s.y;
				stolpec = p.stolpec + koef * s.x;
				this.plosca.polje[vrstica][stolpec] = Polje.obrniPloscek(this.plosca.polje[vrstica][stolpec]);
				koef++;
			}
		}
		this.ugodneSmeri.clear();
	}
}
