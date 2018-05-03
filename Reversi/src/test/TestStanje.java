package test;

import logika.Igra;
import logika.Stanje;
import logika.Plosca;
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
		assertTrue(igra.plosca.prestejPoBarvah()[0] + igra.plosca.prestejPoBarvah()[1] <= Plosca.velikost * Plosca.velikost);
	}

}
