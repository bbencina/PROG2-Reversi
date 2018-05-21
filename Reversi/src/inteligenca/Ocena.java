package inteligenca;

import logika.Igra;
import logika.Plosca;

/**
 * Ker bodo vse metode statiène, ta razred ne potrebuje konstruktorja.
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

}
