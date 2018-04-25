package Logika;

public enum Igralec {
	BLACK,
	WHITE;
	
	public Ploscek ploscek() {
		if (this == BLACK) {
			return Ploscek.BLACK;
		} else {
			return Ploscek.WHITE;
		}
	}
	
	
	public Igralec naslednji(){
		if (this == BLACK) {
			return WHITE;
		} else {
			return BLACK;
		}
	}

}
