package Logika;

public enum Igralec {
	BLACK,
	WHITE;
	
	public Ploscek ploscek() {
		return (this == BLACK ? Ploscek.BLACK : Ploscek.WHITE);
	}
	
	public Igralec naslednji(){
		return (this == BLACK ? WHITE : BLACK);
	}
}
