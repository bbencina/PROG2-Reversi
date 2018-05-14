package gui;

import logika.Poteza;

public class Clovek extends Okupator {
	private GlavnoOkno master;
	
	public Clovek(GlavnoOkno master) {
		this.master = master;
	}

	@Override
	public void zacni_potezo() {
	}

	@Override
	public void prekini() {
	}

	@Override
	public void klik(int i, int j) {
		master.igraj(new Poteza(i, j));
	}
	
}
