package logika;

public class Smer {
	
	public int x, y;
	
	public Smer(int x, int y) {
		this.x = x;
		this.y = y;
		
		assert((x == 1 || x == -1 || x == 0) && (y == 1 || y == -1 || y == 0));
	}

}
