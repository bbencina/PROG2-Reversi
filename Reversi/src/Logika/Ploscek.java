package Logika;

public enum Ploscek {
	BLACK,
	WHITE;
	
	/**
	 * @param ploscek
	 * "Obrne" plošček oz. spremeni barvo podanemu ploščku.
	 */
	public static void obrniSe(Ploscek ploscek) {
		if (ploscek == Ploscek.BLACK) ploscek = Ploscek.WHITE;
		if (ploscek == Ploscek.WHITE) ploscek = Ploscek.BLACK;
	}
}
