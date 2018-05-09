package gui;

public abstract class Okupator {
	/**
	 * Metoda, ki jo kli�e glavno okno, da igralec za�ne potezo.
	 * (Pri �loveku prazna, ra�unalnik pa tu misli.)
	 */
	public abstract void zacni_potezo();
	
	/**
	 * Metoda se kli�e, ko je treba iz katerega koli razloga
	 * pred�asno prekiniti potezo igralca ( npr. za�etek nove igre)
	 * (Pri �loveku prazna.)
	 */
	public abstract void prekini();
	
	/**
	 * @param i - vrstica klika
	 * @param j - stolpec klika
	 * 
	 * Metoda se kli�e, ko uporabnik pritisne na mi�ko;
	 * glej IgralnoPolje.mouseClicked.
	 */
	public abstract void klik(int i, int j);
}
