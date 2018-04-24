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
	
	

}
