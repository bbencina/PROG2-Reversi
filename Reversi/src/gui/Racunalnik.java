package gui;

import javax.swing.SwingWorker;

import inteligenca.Minimax;
import logika.Poteza;

public class Racunalnik extends Okupator {
	private GlavnoOkno master;
	
	private SwingWorker<Poteza, Object> napadalec;

	public Racunalnik(GlavnoOkno master) {
		this.master = master;
	}

	@Override
	public void zacni_potezo() {
		// razmisli in izbere potezo
		napadalec = new Minimax(master, master.tezavnost);
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
