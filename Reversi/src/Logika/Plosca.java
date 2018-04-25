package Logika;

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
				polje[i][j] = new Polje(i, j);
			}
		}
		
		// Nastavitev začetne postavitve na plošči
		polje[Plosca.velikost / 2][Plosca.velikost / 2].ploscek = Ploscek.WHITE;
		polje[Plosca.velikost / 2][(Plosca.velikost / 2) + 1].ploscek = Ploscek.BLACK;
		polje[(Plosca.velikost / 2) + 1][Plosca.velikost / 2].ploscek = Ploscek.BLACK;
		polje[(Plosca.velikost / 2) + 1][(Plosca.velikost / 2) + 1].ploscek = Ploscek.WHITE;
	}

}
