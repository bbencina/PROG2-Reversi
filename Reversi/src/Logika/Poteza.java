package Logika;

import java.util.HashSet;

public class Poteza {
	
	public Igralec igralec;
	public Polje polje;
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
	public Poteza(Plosca plosca, Igralec igralec, Polje polje) {
		this.igralec = igralec;
		this.polje = polje;
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
	 * Preveri veljavnosti poteze.
	 */
	public boolean jeVeljavna(){
		boolean successFlag = false;
		
		int vrstica = this.polje.vrstica, stolpec = this.polje.stolpec;
		
		for (Smer s : smer) {
			int koef = 1;
			while (polje.vrstica + koef * s.y >= 0 && polje.stolpec + koef * s.x >= 0 &&
					polje.vrstica + koef * s.y < Plosca.velikost && polje.stolpec + koef * s.x < Plosca.velikost){
				vrstica = polje.vrstica + koef * s.y;
				stolpec = polje.stolpec + koef * s.x;
				if (this.plosca.polje[vrstica][stolpec].ploscek == null){
					break;
				} else if (this.plosca.polje[vrstica][stolpec].ploscek == this.igralec.ploscek() && koef == 1){
					break;
				} else if (koef > 1 && this.plosca.polje[vrstica][stolpec].ploscek == this.igralec.ploscek()){
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
			int koef = 1, vrstica = this.polje.vrstica, stolpec = this.polje.stolpec;
			while (this.plosca.polje[polje.vrstica + koef * s.y][polje.stolpec + koef * s.x].ploscek != igralec.ploscek()){
				vrstica = polje.vrstica + koef * s.y;
				stolpec = polje.stolpec + koef * s.x;
				Ploscek.obrniSe(this.plosca.polje[vrstica][stolpec].ploscek);
			}
		}
	}
}
