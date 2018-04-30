package Logika;

import java.util.HashSet;

public enum Igralec {
	BLACK(),
	WHITE();
	
	public HashSet<Poteza> moznePoteze;
	public int zaporedneNeveljavne;
	
	private Igralec() {
		this.moznePoteze = new HashSet<Poteza>();
		this.zaporedneNeveljavne = 0;
	}
	
	public Ploscek ploscek() {
		return (this == BLACK ? Ploscek.BLACK : Ploscek.WHITE);
	}
	
	public Igralec naslednji(){
		return (this == BLACK ? WHITE : BLACK);
	}
}
