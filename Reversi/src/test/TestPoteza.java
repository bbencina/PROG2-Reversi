package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import logika.Poteza;
import logika.Smer;

public class TestPoteza extends TestCase {
	
	private static List<Plosca> plosce = new ArrayList<Plosca>();
	private static HashMap<Plosca, HashSet<Poteza>> poteze = new HashMap<Plosca, HashSet<Poteza>>();
	
	{
		/**
		 * Blok, ki napolni seznam plosce z vsemi vmesnimi ploščami naključno
		 * odigranih 10000 iger (iteracije).
		 */
		int iteracije = 10000; // št. generiranih iger
		
		for (int i = 0; i < iteracije; i++) {
			Igra igra = new Igra();
			boolean konecIgre = false;
			
			while (! konecIgre) {
				if(igra.obstajaPoteza()) {
					//Preuredimo množico možnih potez v seznam in izberemo naključno potezo (isto kot testIgra)
					List<Poteza> izborPotez = new ArrayList<Poteza>(igra.veljavnePoteze());
					Random r = new Random(i);
					Poteza poteza = izborPotez.get(r.nextInt(izborPotez.size()));
					poteza.opraviPotezo();
					igra.zaporedneNeveljavne = 0;
				} else {
					igra.zaporedneNeveljavne ++;
				}
				
				// ploscam za testiranje dodamo trenutno ploščo
				plosce.add(igra.plosca);
				
				// za razliko od testIgra, tukaj potrebujemo le, da se igra konča, ne pa dejanskega stanja
				if (igra.zaporedneNeveljavne >= 2) {
					konecIgre = true;
				}
			}
		}
		
		
		/**
		 * Blok, ki se nato sprehodi čez vse plošče iz seznama plosce in sestavi
		 * slovar, ki ima za ključe plošče, za vrednosti pa množice vseh 128 potez,
		 * ki so na plošči na voljo "slepemu opazovalcu".
		 * (64 polj * 2 igralca, če ignoriramo, kdo je na potezi, postavitev ploščkov,...)
		 */
		Poteza p;
		
		for (Plosca plosca : plosce) {
			HashSet<Poteza> potezePlosce = new HashSet<Poteza>();
			for (int i = 0; i < Plosca.velikost; i++) {
				for (int j = 0; j < Plosca.velikost; j++) {
					p = new Poteza(plosca, Igralec.BLACK, i, j);
					potezePlosce.add(p);
					p = new Poteza(plosca, Igralec.WHITE, i, j);
					potezePlosce.add(p);
				}
			}
			poteze.put(plosca, potezePlosce);
		}
	}

	/**
	 * Test preveri, ali se poteze res opravljajo le na praznih poljih.
	 */
	public void testJeVeljavna_PraznaPolja() {
		for (Plosca plosca : poteze.keySet()) {
			for (Poteza p : poteze.get(plosca)) {
				if (p.jeVeljavna() && plosca.polje[p.vrstica][p.stolpec] != Polje.PRAZNO) {
					fail("Poskus poteze na že zapolnjenem polju.");
				}
			}
		}
	}
	
	/**
	 * Test preveri, ali veljavna poteza res vrne neprazno množico ugodnih smeri.
	 * To je namreč pomembno za izvedbo poteze z metodo opraviSe().
	 */
	public void testJeVeljavna_UgodneSmeri() {
		for (Plosca plosca : poteze.keySet()) {
			for (Poteza p : poteze.get(plosca)) {
				assertEquals(p.jeVeljavna(), ! p.ugodneSmeri.isEmpty());
			}
		}
	}
	
	/**
	 * Test le neumno pogleda okolico polja v ugodnih smereh in preveri,
	 * da sosednje polje slučajno ni prazno ali z iste barve ploščkom.
	 */
	public void testJeVeljavna_SosednjiPloscki() {
		for (Plosca plosca : poteze.keySet()) {
			for (Poteza p : poteze.get(plosca)) {
				for (Smer s : p.ugodneSmeri) {
					if (plosca.polje[p.vrstica + s.y][p.stolpec + s.x] == Polje.PRAZNO) {
						fail("Sosednje polje je prazno");
					}
					if (plosca.polje[p.vrstica + s.y][p.stolpec + s.x] == plosca.polje[p.vrstica][p.stolpec]) {
						fail("Sosednje polje je enako izvornemu.");
					}
				}
			}
		}
	}
}
