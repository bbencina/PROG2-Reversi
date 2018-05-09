package gui;

public abstract class Okupator {
	/**
	 * Metoda, ki jo kliče glavno okno, da igralec začne potezo.
	 * (Pri človeku prazna, računalnik pa tu misli.)
	 */
	public abstract void zacni_potezo();
	
	/**
	 * Metoda se kliče, ko je treba iz katerega koli razloga
	 * predčasno prekiniti potezo igralca ( npr. začetek nove igre)
	 * (Pri človeku prazna.)
	 */
	public abstract void prekini();
	
	/**
	 * @param i - vrstica klika
	 * @param j - stolpec klika
	 * 
	 * Metoda se kliče, ko uporabnik pritisne na miško;
	 * glej IgralnoPolje.mouseClicked.
	 */
	public abstract void klik(int i, int j);
}
