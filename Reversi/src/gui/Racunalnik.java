package gui;

import javax.swing.SwingWorker;

import inteligenca.Alphabeta;
import inteligenca.Minimax;
import logika.Poteza;

public class Racunalnik extends Okupator {
	private GlavnoOkno master;
	private int tezavnost;
	private Mozgani mozgani;
	
	private SwingWorker<Poteza, Object> napadalec;

	public Racunalnik(GlavnoOkno master, int tezavnost) {
		this.master = master;
		this.tezavnost = tezavnost;
		this.mozgani = master.getMozgani();
	}

	@Override
	public void zacni_potezo() {
		// razmisli in izbere potezo
		switch (this.mozgani) {
			case MOZGANI_ALPHABETA: napadalec = new Alphabeta(master, tezavnost); break;
			case MOZGANI_MINIMAX: napadalec = new Minimax(master, tezavnost); break;
			
			default: napadalec = new Alphabeta(master, tezavnost);
		}
		napadalec.execute();

	}

	@Override
	public void prekini() {
		if (napadalec != null) {
			napadalec.cancel(true);
		}

	}

	@Override
	public void klik(int i, int j) {
		// Ne naredimo ničesar
	}

}
