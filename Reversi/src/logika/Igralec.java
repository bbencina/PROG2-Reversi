package logika;

public enum Igralec {
	BLACK(),
	WHITE();
	
	protected Polje barva() {
		return (this == BLACK ? Polje.BLACK : Polje.WHITE);
	}
	
	protected Igralec naslednji(){
		return (this == BLACK ? WHITE : BLACK);
	}
}
