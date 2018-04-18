package Logika;

public class Polje {
	
	protected int vrstica, stolpec;
	
	protected Ploscek ploscek;
	
	public Polje(int vrstica, int stolpec){
		this.vrstica = vrstica;
		this.stolpec = stolpec;
		
		this.ploscek = null;
	}
	
	public boolean jePrazno(){
		return this.ploscek == null;
	}

}
