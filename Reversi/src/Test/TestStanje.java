package Test;

import Logika.Plosca;
import Logika.Stanje;
import junit.framework.TestCase;

public class TestStanje extends TestCase {
	
	public Plosca plosca;

	public void testStanje() {
		fail("Not yet implemented");
	}

	/*
	 * Test preverja, da števca ploščkov ne prekoračita velikosti plošče.
	 */
	public void testPrestejPoBarvah() {
		assertTrue(Stanje.prestejPoBarvah(plosca)[0] + Stanje.prestejPoBarvah(plosca)[1] <= Plosca.velikost * Plosca.velikost);
	}

}
