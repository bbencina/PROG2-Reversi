package gui;

import javax.swing.SwingWorker;

import inteligenca.Minimax;
import logika.Poteza;

public class Racunalnik extends Okupator {
	private GlavnoOkno master;
	private int tezavnost;
	
	private SwingWorker<Poteza, Object> napadalec;

	public Racunalnik(GlavnoOkno master, int tezavnost) {
		this.master = master;
		this.tezavnost = tezavnost;
	}

	@Override
	public void zacni_potezo() {
		// razmisli in izbere potezo
		napadalec = new Minimax(master, tezavnost);
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
