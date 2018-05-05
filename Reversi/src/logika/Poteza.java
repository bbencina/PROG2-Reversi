package logika;

import java.util.HashSet;

public class Poteza {
	
	public Igralec igralec;
	protected int vrstica, stolpec;
	public Plosca plosca;
	
	private HashSet<Smer> ugodneSmeri = new HashSet<Smer>();
	
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
	public Poteza(Plosca plosca, Igralec igralec, int vrstica, int stolpec) {
		this.igralec = igralec;
		this.vrstica = vrstica;
		this.stolpec = stolpec;
		this.plosca = plosca;
	}
	

	/**
	 * Glavna metoda za opravljanje poteze. Preveri njeno veljavnost in jo v primeru, da je veljavna, tudi opravi.
	 * Vrne true, če je bila poteza opravljena, false sicer.
	 */
	public boolean opraviPotezo(){
		if (this.jeVeljavna()){
			this.opraviSe(this.ugodneSmeri);
			return true;
		}
		return false;
	}
	
	/**
	 * Preveri veljavnost poteze in nastavi ugodne smeri, če obstajajo.
	 */
	protected boolean jeVeljavna(){
		boolean successFlag = false;
		
		int vrstica = this.vrstica, stolpec = this.stolpec;
		
		if (this.plosca.polje[this.vrstica][this.stolpec] != Polje.PRAZNO) return false;
		
		for (Smer s : smer) {
			int koef = 1;
			while (this.vrstica + koef * s.y >= 0 && this.stolpec + koef * s.x >= 0 &&
					this.vrstica + koef * s.y < Plosca.velikost && this.stolpec + koef * s.x < Plosca.velikost){
				vrstica = this.vrstica + koef * s.y;
				stolpec = this.stolpec + koef * s.x;
				if (this.plosca.polje[vrstica][stolpec] == Polje.PRAZNO){
					break;
				} else if (this.plosca.polje[vrstica][stolpec] == this.igralec.barva() && koef == 1){
					break;
				} else if (koef > 1 && this.plosca.polje[vrstica][stolpec] == this.igralec.barva()){
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
	 * Ta funkcija dejansko opravi potezo (obrača ploščke).
	 * Kliče jo funkcija opraviPotezo.
	 */
	private void opraviSe(HashSet<Smer> smeri){
		for (Smer s : smeri){
			int koef = 1, vrstica = this.vrstica, stolpec = this.stolpec;
			//Najprej postavimo plošček na izbrano polje.
			if (this.igralec == Igralec.BLACK) {
				this.plosca.polje[this.vrstica][this.stolpec]= Polje.BLACK;
			} else {
				this.plosca.polje[this.vrstica][this.stolpec] = Polje.WHITE;
			}
			//Nato še obrnemo preostale ploščke, ki jih igralec odvzame nasprotniku.
			// Še enkrat dodan pogoj za ploščo, ker se pojavlja napaka.
			while (this.plosca.polje[this.vrstica + koef * s.y][this.stolpec + koef * s.x] == igralec.barva().nasprotno() &&
					this.vrstica + koef * s.y >= 0 && this.stolpec + koef * s.x >= 0 && 
					this.vrstica + koef * s.y < Plosca.velikost && this.stolpec + koef * s.x < Plosca.velikost){
				vrstica = this.vrstica + koef * s.y;
				stolpec = this.stolpec + koef * s.x;
				this.plosca.polje[vrstica][stolpec] = Polje.obrniPloscek(this.plosca.polje[vrstica][stolpec]);
				koef++;
			}
		}
	}
}
