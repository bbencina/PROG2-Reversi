package test;

import junit.framework.TestCase;
import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Stanje;
import logika.Poteza;
import java.util.ArrayList;
import java.util.List;

public class TestIgra extends TestCase {

	public void testIgra() {
			
		Igra igra = new Igra();
		
		Stanje stanje;
		
		boolean konecIgre = false;
		
		// Na začetku je na potezi črni
		assertEquals(igra.igralecNaPotezi, Igralec.BLACK);
		
		while(! konecIgre) {
			Igralec igralec1 = igra.igralecNaPotezi;
			if(igra.obstajaPoteza()) {
				//Preuredimo množico možnih potez v seznam in izeberemo prvo potezo
				List<Poteza> izborPotez = new ArrayList<Poteza>(igra.veljavnePoteze());
				Poteza poteza = izborPotez.get(0);
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
				//preverimo, da število ploščkov ne preseže velikost plošče
				assertTrue(igra.plosca.prestejPoBarvah()[0] + igra.plosca.prestejPoBarvah()[1] <= Plosca.velikost * Plosca.velikost);
				
				int plosckiCrni = igra.plosca.prestejPoBarvah()[0];
				int plosckiBeli = igra.plosca.prestejPoBarvah()[1];
				
				//preverimo, da je rezultat pravilen
				if (plosckiCrni == plosckiBeli) assertEquals(stanje, Stanje.NEODLOCENO);
				else if(plosckiCrni < plosckiBeli) assertEquals(stanje, Stanje.ZMAGA_WHITE);
				else if(plosckiCrni > plosckiBeli) assertEquals(stanje, Stanje.ZMAGA_BLACK);
				
				konecIgre = true;
			}
		}
	}
	
}
