package Logika;

public class Igra {
	/**
	 * Igralec trenutno na potezi.
	 * (Zaradi konstantnega spreminjanja barv ploščkov se ne splača računati.)
	 */
	public Igralec igralecNaPotezi;
	
	/**
	 * Plošča lastna tej igri.
	 */
	public Plosca plosca;
	
	public Igra() {
		plosca = new Plosca();
		
		igralecNaPotezi = Igralec.BLACK;
	}
}
