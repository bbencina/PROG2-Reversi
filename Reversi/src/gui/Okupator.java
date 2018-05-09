package gui;

public abstract class Okupator {
	/**
	 * Metoda, ki jo klièe glavno okno, da igralec zaène potezo.
	 * (Pri èloveku prazna, raèunalnik pa tu misli.)
	 */
	public abstract void zacni_potezo();
	
	/**
	 * Metoda se klièe, ko je treba iz katerega koli razloga
	 * predèasno prekiniti potezo igralca ( npr. zaèetek nove igre)
	 * (Pri èloveku prazna.)
	 */
	public abstract void prekini();
	
	/**
	 * @param i - vrstica klika
	 * @param j - stolpec klika
	 * 
	 * Metoda se klièe, ko uporabnik pritisne na miško;
	 * glej IgralnoPolje.mouseClicked.
	 */
	public abstract void klik(int i, int j);
}
