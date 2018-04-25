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
	
	public void igrajSe(){
		/**
		 * Za igralca na potezi preveri, če ima na razpolago veljavne poteze. -Stanje
		 * Če so, počaka, da igralec opravi potezo. -Poteza
		 * Zamenja igralca na potezi. -Tukaj
		 * 
		 * Če nobeden od igralcev nima veljavnih potez - konec igre. -Stanje
		 * Če je plošča polna -> ni veljavnih potez -že preverjeno.
		 * 
		 * Razglasi rezultat. -Stanje -Igralec
		 */
	}
}
