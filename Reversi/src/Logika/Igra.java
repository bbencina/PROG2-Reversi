package Logika;

public class Igra {
	/**
	 * Igralec trenutno na potezi.
	 * (Zaradi konstantnega spreminjanja barv ploščkov se ne splača računati.)
	 */
	public Igralec igralecNaPotezi;
	
	public Igralec igralecCrni, igralecBeli;
	
	/**
	 * Plošča lastna tej igri.
	 */
	public Plosca plosca;
	
	public Igra() {
		plosca = new Plosca();
		
		igralecCrni = new Igralec(Ploscek.BLACK);
		igralecBeli = new Igralec(Ploscek.WHITE);
		
		igralecNaPotezi = igralecCrni;
	}
}
