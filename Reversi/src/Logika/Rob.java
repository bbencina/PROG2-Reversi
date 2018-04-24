package Logika;

public enum Rob {
	LEVI,
	DESNI,
	ZGORNJI,
	SPODNJI;
	
	public static boolean jeNaRobu(Polje polje) {
		if ((polje.vrstica == 0 || polje.vrstica == Plosca.velikost - 1) ||
				(polje.stolpec == 0 || polje.stolpec == Plosca.velikost - 1)) {
			return true;
		}
		return false;
	}
	
	public static boolean jeEnaOdRoba(Polje polje) {
		if ((polje.vrstica == 1 || polje.vrstica == Plosca.velikost - 2) ||
				(polje.stolpec == 1 || polje.stolpec == Plosca.velikost - 2)) {
			return true;
		}
		return false;
	}
	
	public static Rob rob(Polje polje) {
		assert(Rob.jeNaRobu(polje));
		
		if (polje.vrstica == 0) {
			return Rob.ZGORNJI;
		} else if (polje.vrstica == Plosca.velikost -1) {
			return Rob.SPODNJI;
		} else if (polje.stolpec == 0){
			return Rob.LEVI;
		} else {
			return Rob.DESNI;
		}
	}
	
	public static Rob enaOdRoba(Polje polje) {
		assert(Rob.jeEnaOdRoba(polje));
		
		if (polje.vrstica == 1) {
			return Rob.ZGORNJI;
		} else if (polje.vrstica == Plosca.velikost - 2) {
			return Rob.SPODNJI;
		} else if (polje.stolpec == 1){
			return Rob.LEVI;
		} else {
			return Rob.DESNI;
		}
	}
}
