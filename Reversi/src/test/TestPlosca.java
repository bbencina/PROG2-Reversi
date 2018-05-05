package test;

import junit.framework.TestCase;
import logika.Plosca;

public class TestPlosca extends TestCase {
	
	/**
	 * Test le prešteje ploščke in preveri, da jih je na začetku pričakovano
	 * število. Preveri tudi, če je tabela ploščkov pravih dimenzij.
	 */
	public void testPlosca() {
		Plosca plosca = new Plosca();
		
		// preveri, da sta res zapolnjeni le dve polji za vsako barvo
		assertEquals(plosca.prestejPoBarvah()[0], plosca.prestejPoBarvah()[1]);
		assertEquals(plosca.prestejPoBarvah()[0], 2);
		
		assertEquals(plosca.polje.length, Plosca.velikost);
		assertEquals(plosca.polje[0].length, Plosca.velikost);
	}
	

}
