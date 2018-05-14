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
	
	
	// Moralo bi pisati private, a potrubujem public za jUnit teste.
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
		if (jeVeljavna(p)) {
			System.out.println("Poteza se bo izvedla...");
			opraviPotezo(p);
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
	 */
	public HashSet<Poteza> veljavnePoteze() {
		HashSet<Poteza> moznePoteze = new HashSet<Poteza>();
		for (int i = 0; i < Plosca.velikost; i++) {
			for (int j = 0; j < Plosca.velikost; j++) {
				if (plosca.polje[i][j] == Polje.PRAZNO) {
					Poteza poteza = new Poteza(i, j);
					if (jeVeljavna(poteza)) {
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

	
	/**
	 * Preveri veljavnost poteze in nastavi ugodne smeri, če obstajajo.
	 */
	public boolean jeVeljavna(Poteza p){
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
					ugodneSmeri.add(s);
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
	 * Kliče jo metoda opraviPotezo.
	 * 
	 * Pomembno: ta metoda je striktno private, kliče se jo lahko samo iz
	 * metode opraviPotezo in iz nikjer drugje. Če piše, da je public, je
	 * to le v namene testov (če se da, najdi drugačen način).
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
