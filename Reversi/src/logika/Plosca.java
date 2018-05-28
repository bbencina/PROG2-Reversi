package logika;

public class Plosca {
	
	public final static int velikost = 8;
	
	public Polje[][] polje;
	
	{
		//Velikost plošče mora biti zaradi začetne pozicije VEDNO sodo število.
		assert(Plosca.velikost % 2 == 0);
	}
	
	public Plosca(){
		polje = new Polje[Plosca.velikost][Plosca.velikost];
		for (int i = 0; i < Plosca.velikost; i++){
			for (int j = 0; j < Plosca.velikost; j++){
				polje[i][j] = Polje.PRAZNO;
			}
		}
		
		// Nastavitev začetne postavitve na plošči
		polje[Plosca.velikost / 2 - 1][Plosca.velikost / 2 - 1] = Polje.WHITE;
		polje[Plosca.velikost / 2 - 1][(Plosca.velikost / 2)] = Polje.BLACK;
		polje[(Plosca.velikost / 2)][Plosca.velikost / 2 - 1] = Polje.BLACK;
		polje[(Plosca.velikost / 2)][(Plosca.velikost / 2)] = Polje.WHITE;
	}
	
	/**
	 * @param plosca
	 * @return par števil, prvo je število črnih in drugo število belih ploščkov na podani plošči.
	 */
	public int[] prestejPoBarvah() {
		int[] steviloPlosckov = new int[2];
		
		int black = 0, white = 0;
		
		for (int i = 0; i < Plosca.velikost; i++) {
			for (int j = 0; j < Plosca.velikost; j++) {
				if (this.polje[i][j] == Polje.BLACK) black++;
				if (this.polje[i][j] == Polje.WHITE) white++;
			}
		}
		
		steviloPlosckov[0] = black;
		steviloPlosckov[1] = white;
		
		return steviloPlosckov;
	}


}
