package test;

import java.util.HashSet;

import junit.framework.TestCase;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;
import logika.Poteza;
import logika.Smer;

public class TestPoteza extends TestCase {
	
	Plosca plosca = new Plosca();
	HashSet<Poteza> poteze = new HashSet<Poteza>();
	
	{
		Poteza p;
		
		for (int i = 0; i < Plosca.velikost; i++) {
			for (int j = 0; j < Plosca.velikost; j++) {
				p = new Poteza(plosca, Igralec.BLACK, i, j);
				poteze.add(p);
				p = new Poteza(plosca, Igralec.WHITE, i, j);
				poteze.add(p);
			}
		}
	}
	
	/**
	 * Test preveri, ali se poteze res opravljajo le na praznih poljih.
	 */
	public void testJeVeljavna_PraznaPolja() {
		for (Poteza p : poteze) {
			if (p.jeVeljavna() && plosca.polje[p.vrstica][p.stolpec] != Polje.PRAZNO) {
				fail("Poskus poteze na že zapolnjenem polju.");
			}
		}
	}
	
	/**
	 * Test preveri, ali veljavna poteza res vrne neprazno množico ugodnih smeri.
	 * To je namreč pomembno za izvedbo poteze z metodo opraviSe().
	 */
	public void testJeVeljavna_UgodneSmeri() {
		for (Poteza p : poteze) {
			assertEquals(p.jeVeljavna(), ! p.ugodneSmeri.isEmpty());
		}
	}
	
	public void testJeVeljavna_SosednjiPloscki() {
		for (Poteza p : poteze) {
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
