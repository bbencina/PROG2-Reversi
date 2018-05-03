package Logika;

public enum Polje {
	BLACK,
	WHITE,
	PRAZNO;
	
	public static Polje obrniPloscek(Polje polje) {
		if (polje == PRAZNO){
			return PRAZNO;
		}
		return (polje == BLACK ? WHITE : BLACK);
	}
	
	public Polje nasprotno() {
		if (this == PRAZNO) return PRAZNO;
		return (this == BLACK ? WHITE : BLACK);
	}

}
