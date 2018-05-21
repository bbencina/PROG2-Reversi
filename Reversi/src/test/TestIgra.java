package test;

import junit.framework.TestCase;
import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import logika.Stanje;
import logika.Poteza;
import logika.Smer;

import java.util.LinkedList;
import java.util.Random;

public class TestIgra extends TestCase {

	public void testIgra() {
		
		// S for zanko lahko z vsakim testom preverimo poljubno število naključnih iger
		int iteracije = 1000;
		
		for (int i = 0; i <= iteracije; i++) {
			
			Igra igra = new Igra();
			
			Stanje stanje;
			
			boolean konecIgre = false;
			
			Poteza p;
			
			// Na začetku je na potezi črni
			assertEquals(igra.getIgralecNaPotezi(), Igralec.BLACK);
			
			while(! konecIgre) {
				Igralec igralec1 = igra.getIgralecNaPotezi();
				stanje = igra.stanje();
				//Preverimo, da je kdo na potezi
				assertTrue(stanje == Stanje.NA_POTEZI_BLACK | stanje == Stanje.NA_POTEZI_WHITE);
				
				if(! igra.potezeIgralca(igralec1).isEmpty()) {
					//Preuredimo množico možnih potez v seznam in izberemo naključno potezo
					LinkedList<Poteza> izborPotez = igra.potezeIgralca(igralec1);
					Random r = new Random(i);
					Poteza poteza = izborPotez.get(r.nextInt(izborPotez.size()));
					igra.igrajPotezo(poteza);
					igralec1 = igra.getIgralecNaPotezi();
				}
				stanje = igra.stanje();
					
				//Preverimo, da se je igralec na potezi zamenjal
				assertTrue(igralec1 == igra.getIgralecNaPotezi());
				
				//Če ni več veljavnih potez
				if (stanje == Stanje.NEODLOCENO || stanje == Stanje.ZMAGA_BLACK || stanje == Stanje.ZMAGA_WHITE){
					
					int plosckiCrni = igra.getPlosca().prestejPoBarvah()[0];
					int plosckiBeli = igra.getPlosca().prestejPoBarvah()[1];
					
					//preverimo, da število ploščkov ne preseže velikost plošče
					assertTrue(plosckiCrni + plosckiBeli <= Plosca.velikost * Plosca.velikost);
					
					//preverimo, da je rezultat pravilen
					if (plosckiCrni == plosckiBeli) assertEquals(stanje, Stanje.NEODLOCENO);
					else if(plosckiCrni < plosckiBeli) assertEquals(stanje, Stanje.ZMAGA_WHITE);
					else if(plosckiCrni > plosckiBeli) assertEquals(stanje, Stanje.ZMAGA_BLACK);
					
					konecIgre = true;
				}
				

				//Testiramo vse možne poteze na plošči
				for (int x = 0; x < Plosca.velikost; x++) {
					for (int y = 0; y < Plosca.velikost; y++) {
						p = new Poteza(x, y);
						
						//Ustvarimo kopijo igre, ker ne smemo spreminjati igre, ki se trenutno igra.
						Igra kopijaIgre = new Igra(igra);
						
						//Test preveri, ali se poteze res opravljajo le na praznih poljih.
						if (kopijaIgre.jeVeljavna(p) && kopijaIgre.getPlosca().polje[p.vrstica][p.stolpec] != Polje.PRAZNO) {
							fail("Poskus poteze na že zapolnjenem polju.");
						}
						
						//Test preveri, ali igra.jeVeljavna(p, true) res nastavi neprazno množico ugodnih smeri.
						assertEquals(kopijaIgre.napolniUgodneCeVeljavna(p), ! kopijaIgre.ugodneSmeri.isEmpty());
						
						//Test le neumno pogleda okolico polja v ugodnih smereh in preveri, da sosednje polje 
						//slučajno ni prazno ali z iste barve ploščkom.
						for (Smer s : kopijaIgre.ugodneSmeri) {
							if (kopijaIgre.getPlosca().polje[p.vrstica + s.y][p.stolpec + s.x] == Polje.PRAZNO) {
								fail("Sosednje polje je prazno");
							}
							if (kopijaIgre.getPlosca().polje[p.vrstica + s.y][p.stolpec + s.x] == kopijaIgre.getPlosca().polje[p.vrstica][p.stolpec]) {
								fail("Sosednje polje je enako izvornemu.");
							}
						}
					}
				}
			}
		}
	}
}
		

