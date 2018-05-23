package inteligenca;

import java.util.LinkedList;
import java.util.Random;

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
		Igralec trenutniIgralec = null;
		
		switch (igra.stanje()){
		case NA_POTEZI_BLACK: trenutniIgralec = Igralec.BLACK; break;
		case NA_POTEZI_WHITE: trenutniIgralec = Igralec.WHITE; break;
		case ZMAGA_BLACK:
			return new OcenjenaPoteza(
					null, jaz == Igralec.BLACK ? Ocena.ZMAGA : Ocena.PORAZ);
		case ZMAGA_WHITE:
			return new OcenjenaPoteza(
					null, jaz == Igralec.WHITE ? Ocena.ZMAGA : Ocena.PORAZ);
		case NEODLOCENO:
			return new OcenjenaPoteza(null, Ocena.NEODLOCENO);
		}
		
		// ali smo pregloboko
		if (i >= globina){
			return new OcenjenaPoteza(
					null,
					Ocena.oceniPozicijo(igra, jaz));
		}
		
		LinkedList<Poteza> najboljsePoteze = new LinkedList<Poteza>();
		int ocenaNajboljse = 0;
		
		for (Poteza p : igra.potezeIgralca(trenutniIgralec)){
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.igrajPotezo(p);
			
			// rekurziven klic
			int ocenaPoteze = minimax(i+1, kopijaIgre).vrednost;
			
			if (najboljsePoteze.isEmpty() ||
				(trenutniIgralec == jaz && ocenaPoteze > ocenaNajboljse) ||
				(trenutniIgralec != jaz && ocenaPoteze < ocenaNajboljse)
				){
				najboljsePoteze.clear();
				najboljsePoteze.add(p);
				ocenaNajboljse = ocenaPoteze;
			} else if (ocenaPoteze == ocenaNajboljse) {
				najboljsePoteze.add(p);
			}				
		}
		
		assert(! najboljsePoteze.isEmpty());
		
		Random r = new Random();
		Poteza najboljsa = najboljsePoteze.get(r.nextInt(najboljsePoteze.size()));
		
		return new OcenjenaPoteza(najboljsa, ocenaNajboljse);
		
		
	}
	
}
