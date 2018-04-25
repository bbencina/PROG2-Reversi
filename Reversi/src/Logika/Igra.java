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
		 * Za igralca na potezi preveri, če ima na razpolago veljavne poteze. -Stanje -metoda obstajaPoteza
		 * Če so, počaka, da igralec opravi potezo. -Poteza -metoda opraviPotezo
		 * Zamenja igralca na potezi. -Igralec -metoda naslednji()
		 * 
		 * Če nobeden od igralcev nima veljavnih potez - konec igre. -Stanje
		 * (Lahko preverjamo tako, da definiramo števec zaporednih neveljavnih potez - ko je enak 2, je igre konec)
		 * 
		 * Če je plošča polna -> ni veljavnih potez -že preverjeno.
		 * 
		 * Razglasi rezultat. -Stanje -Igralec
		 */
		
		//if obstajaPoteza
		
			//opraviPotezo
		
		igralecNaPotezi.naslednji();
		
	}
}
