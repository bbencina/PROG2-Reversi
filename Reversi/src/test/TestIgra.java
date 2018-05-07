package test;

import junit.framework.TestCase;
import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Stanje;
import logika.Poteza;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestIgra extends TestCase {

	public void testIgra() {
		
		// S for zanko lahko z vsakim testom preverimo poljubno število naključnih iger
		int iteracije = 10000;
		
		for (int i = 0; i <= iteracije; i++) {
			
			Igra igra = new Igra();
			
			Stanje stanje;
			
			boolean konecIgre = false;
			
			// Na začetku je na potezi črni
			assertEquals(igra.igralecNaPotezi, Igralec.BLACK);
			
			while(! konecIgre) {
				Igralec igralec1 = igra.igralecNaPotezi;
				if(igra.obstajaPoteza()) {
					//Preuredimo množico možnih potez v seznam in izberemo naključno potezo
					List<Poteza> izborPotez = new ArrayList<Poteza>(igra.veljavnePoteze());
					Random r = new Random(i);
					Poteza poteza = izborPotez.get(r.nextInt(izborPotez.size()));
					poteza.opraviPotezo();
					igra.zaporedneNeveljavne = 0;
				} else {
					igra.zaporedneNeveljavne ++;
				}
				stanje = igra.stanje();
				
				if (igra.zaporedneNeveljavne < 2) {
					//Preverimo, da se je igralec na potezi zamenjal
					assertTrue(igralec1 != igra.igralecNaPotezi);
					//Preverimo, da je kdo na potezi
					assertTrue(stanje == Stanje.NA_POTEZI_BLACK | stanje == Stanje.NA_POTEZI_WHITE);
				} 
				
				//Če ni več veljavnih potez
				else {
					
					int plosckiCrni = igra.plosca.prestejPoBarvah()[0];
					int plosckiBeli = igra.plosca.prestejPoBarvah()[1];
					
					//preverimo, da število ploščkov ne preseže velikost plošče
					assertTrue(plosckiCrni + plosckiBeli <= Plosca.velikost * Plosca.velikost);
					
					//preverimo, da je rezultat pravilen
					if (plosckiCrni == plosckiBeli) assertEquals(stanje, Stanje.NEODLOCENO);
					else if(plosckiCrni < plosckiBeli) assertEquals(stanje, Stanje.ZMAGA_WHITE);
					else if(plosckiCrni > plosckiBeli) assertEquals(stanje, Stanje.ZMAGA_BLACK);
					
					konecIgre = true;
				}
			}
		}
	}
	
}
