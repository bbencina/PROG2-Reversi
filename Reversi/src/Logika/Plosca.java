package Logika;

public class Plosca {
	
	public final static int velikost = 8;
	
	protected Polje[][] polja;
	
	{
		//Velikost plosce mora biti zaradi zaèetne pozicije VEDNO sodo število.
		assert(Plosca.velikost % 2 == 0);
	}
	
	public Plosca(){
		polja = new Polje[Plosca.velikost][Plosca.velikost];
		for (int i = 0; i < Plosca.velikost; i++){
			for (int j = 0; j < Plosca.velikost; j++){
				polja[i][j] = new Polje(i, j);
			}
		}
	}

}
