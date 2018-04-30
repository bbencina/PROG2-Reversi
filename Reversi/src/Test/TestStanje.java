package Test;

import Logika.Igra;
import Logika.Stanje;
import Logika.Plosca;
import junit.framework.TestCase;

public class TestStanje extends TestCase {
	
	public Igra igra;

	public void testStanje() {
		fail("Not yet implemented");
	}

	/*
	 * Test preverja, da števca ploščkov ne prekoračita velikosti plošče.
	 */
	public void testPrestejPoBarvah() {
		assertTrue(Igra.prestejPoBarvah(igra.plosca)[0] + Igra.prestejPoBarvah(igra.plosca)[1] <= Plosca.velikost * Plosca.velikost);
	}

}
