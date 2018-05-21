package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Plosca;
import logika.Polje;

/**
 * Ker bodo vse metode statične, ta razred ne potrebuje konstruktorja.
 * V nasprotnem primeru imaš tu opomnik, da ga napiši.
 */
public class Ocena {
	public static final int ZMAGA = 1000000; // milijon
	public static final int PORAZ = (-1) * ZMAGA;
	public static final int NEODLOCENO = 0;
	
	private static boolean naVogalu(int vrstica, int stolpec){
		if ((vrstica == 0 || vrstica == Plosca.velikost - 1) &&
			(stolpec == 0 || stolpec == Plosca.velikost - 1)) {
			return true;
		}
		return false;
	}
	
	private static boolean naRobu(int vrstica, int stolpec){
		if ((vrstica == 0 || vrstica == Plosca.velikost - 1 ||
				stolpec == 0 || stolpec == Plosca.velikost - 1) &&
				! naVogalu(vrstica, stolpec)) {
				return true;
			}
		return false;
	}
	
	private static boolean enaOdRoba(int vrstica, int stolpec){
		if ((vrstica == 1 || vrstica == Plosca.velikost - 2 ||
				stolpec == 1 || stolpec == Plosca.velikost - 2) &&
				! naRobu(vrstica, stolpec)) {
				return true;
			}
		return false;
	}
	
	/**
	 * Oceni trenutno pozicijo.
	 * @return vrednost trenutne igre
	 * Zaenkrat je še zelo neumna, samo prešteje ploščke obeh igralcev in vrne razliko.
	 */
	protected int oceniPozicijo(Igra igra, Igralec jaz){
		Plosca plosca = igra.getPlosca();
		int mojiPloscki = 0;
		int nasprotnikoviPloscki = 0;
		switch(jaz){
		case BLACK:
			mojiPloscki = plosca.prestejPoBarvah()[0];
			nasprotnikoviPloscki = plosca.prestejPoBarvah()[1];
		case WHITE:
			mojiPloscki = plosca.prestejPoBarvah()[1];
			nasprotnikoviPloscki = plosca.prestejPoBarvah()[0];
		}
		return mojiPloscki - nasprotnikoviPloscki;
	}

}
