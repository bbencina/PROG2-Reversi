package inteligenca;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import logika.Igra;
import logika.Igralec;
import logika.Poteza;

public class Minimax extends SwingWorker<Poteza, Object> {
	
	private GlavnoOkno master;
	
	private int globina;
	
	private Igralec jaz;
	
	public Minimax(GlavnoOkno master, int globina){
		this.master = master;
		this.globina = globina;
	}

	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.kopijaIgre();
		this.jaz = igra.getIgralecNaPotezi();
		OcenjenaPoteza p = minimax(0, igra);
		assert(p.poteza != null);
		return p.poteza;
	}
	
	@Override
	public void done(){
		try {
			Poteza p = this.get();
			if (p != null) master.igraj(p);
		} catch (Exception e) {
		}
	}

	private OcenjenaPoteza minimax(int i, Igra igra) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
