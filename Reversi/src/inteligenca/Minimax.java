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
	private int alpha, beta;
	
	private Igralec jaz;
	
	private static int MINUSNESKONCNO = -1000000000;
	private static int NESKONCNO = 1000000000;
	
	public Minimax(GlavnoOkno master, int globina){
		this.master = master;
		this.globina = globina;
		alpha = MINUSNESKONCNO;
		beta = NESKONCNO;
	}

	@Override
	protected Poteza doInBackground() throws Exception {
		Igra igra = master.kopijaIgre();
		this.jaz = igra.getIgralecNaPotezi();
		
		// če se kliče naivni minimax
		//OcenjenaPoteza p = minimax(0, igra);
		
		// če se kliče nenaivni minimax - alpha-beta pruning
		OcenjenaPoteza p = alphabeta(0, igra);
		
		assert(p.poteza != null);
		System.out.println("Minimax je izračunal potezo: " + p);
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
	
	private OcenjenaPoteza alphabeta (int i, Igra igra) {
		Igralec trenutniIgralec = null;
		OcenjenaPoteza v;
		
		System.out.println("***GLOBINA " + i + " ***");
		
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
		
		int j = 0;
		
		if (trenutniIgralec == this.jaz){
			v = new OcenjenaPoteza(null, MINUSNESKONCNO);
			
			
			for (Poteza p : igra.potezeIgralca(trenutniIgralec)){
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.igrajPotezo(p);
				
				System.out.println(++j);
				
				OcenjenaPoteza vmesna = alphabeta(i+1, kopijaIgre);
				
				if (v.vrednost < vmesna.vrednost){
					v = new OcenjenaPoteza(p, vmesna.vrednost);
				}
				alpha = Math.max(alpha, v.vrednost);
				if (beta <= alpha){
					System.out.println("Beta manjši od alfa: " + beta + " " + alpha);
					break;
				}
			}
			
			System.out.println("***GLOBINA " + i + " ***");
			
			return v;
			
		} else {
			v = new OcenjenaPoteza(null, NESKONCNO);
			
			for (Poteza p : igra.potezeIgralca(trenutniIgralec)){
				Igra kopijaIgre = new Igra(igra);
				kopijaIgre.igrajPotezo(p);
				
				System.out.println(++j);
				
				OcenjenaPoteza vmesna = alphabeta(i+1, kopijaIgre);
				
				if (v.vrednost > vmesna.vrednost){
					v =  new OcenjenaPoteza(p, vmesna.vrednost);;
				}
				beta = Math.min(beta, v.vrednost);
				if (beta <= alpha){
					System.out.println("Beta manjši od alfa: " + beta + " " + alpha);
					break;
				}
			}
			
			System.out.println("***GLOBINA " + i + " ***");
			
			return v;
		}
	}
	
}
