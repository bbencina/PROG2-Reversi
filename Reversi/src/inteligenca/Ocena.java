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
	
	public static final int VOGAL = 100;
	public static final int ROB = 20;
	public static final int ENA_OD_ROBA = 2;
	public static final int NAVADNO = 5;
	
	
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
	
	protected static int oceniPozicijo(Igra igra, Igralec jaz){
		Plosca plosca = igra.getPlosca();
		int ocenaPlosce = 0;
		
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
		
		if (mojiPloscki + nasprotnikoviPloscki == Plosca.velikost * Plosca.velikost){
			if (mojiPloscki == nasprotnikoviPloscki) return NEODLOCENO;
			else return (mojiPloscki > nasprotnikoviPloscki ? ZMAGA : PORAZ);
		}
		
		for (int i = 0; i < Plosca.velikost; i++){
			for (int j = 0; j < Plosca.velikost; j++){
				if (plosca.polje[i][j] == Polje.PRAZNO) {continue;}
				
				Polje mojPloscek = (jaz == Igralec.BLACK ? Polje.BLACK : Polje.WHITE);
				
				if (plosca.polje[i][j] == mojPloscek){
					if (naVogalu(i,j)) ocenaPlosce += VOGAL;
					else if (naRobu(i, j)) ocenaPlosce += ROB;
					else if (enaOdRoba(i, j)) ocenaPlosce += ENA_OD_ROBA;
					else ocenaPlosce += NAVADNO;
				} else {
					if (naVogalu(i,j)) ocenaPlosce -= VOGAL;
					else if (naRobu(i, j)) ocenaPlosce -= ROB;
					else if (enaOdRoba(i, j)) ocenaPlosce -= ENA_OD_ROBA;
					else ocenaPlosce -= NAVADNO;
				}
			}
		}
		
		int steviloPotez = igra.potezeIgralca(igra.getIgralecNaPotezi()).size();
		if (igra.getIgralecNaPotezi() == jaz) ocenaPlosce += 5 * steviloPotez;
		else ocenaPlosce -= 5 * steviloPotez;
		
		return ocenaPlosce;
	}
	
	/**
	 * Oceni trenutno pozicijo.
	 * @return vrednost trenutne igre
	 * Zaenkrat je še zelo neumna, samo prešteje ploščke obeh igralcev in vrne razliko.
	 */
	protected static int oceniPozicijoGreedy(Igra igra, Igralec jaz){
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
