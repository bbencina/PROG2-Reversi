package Logika;

public enum Ploscek {
	BLACK,
	WHITE;
	
	/**
	 * @param ploscek
	 * "Obrne" plošček oz. spremeni barvo podanemu ploščku.
	 */
	public static Ploscek obrniSe(Ploscek ploscek) {
		return (ploscek == BLACK ? WHITE : BLACK);
	}
	
	public Ploscek nasprotni() {
		return (this == BLACK ? WHITE : BLACK);
	}
}
