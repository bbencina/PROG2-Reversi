package Logika;

public enum Vogal {
	LEVIZGORNJI,
	LEVISPODNJI,
	DESNIZGORNJI,
	DESNISPODNJI;
	
	public static boolean jeNaVogalu(Polje polje) {
		if ((polje.vrstica == 0 || polje.vrstica == Plosca.velikost - 1) &&
				(polje.stolpec == 0 || polje.stolpec == Plosca.velikost - 1)) {
			return true;
		}
		return false;
	}
	
	public Vogal vogal(Polje polje) {
		assert(Vogal.jeNaVogalu(polje));
		
		if (polje.vrstica == 0 && polje.stolpec == 0) {
			return Vogal.LEVIZGORNJI;
		} else if (polje.vrstica == 0 && polje.stolpec == Plosca.velikost - 1){
			return Vogal.DESNIZGORNJI;
		} else if (polje.vrstica == Plosca.velikost -1 && polje.stolpec == 0){
			return Vogal.LEVISPODNJI;
		} else {
			return Vogal.DESNISPODNJI;
		}
	}

}
