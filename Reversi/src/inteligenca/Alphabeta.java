package inteligenca;

import javax.swing.SwingWorker;

import gui.GlavnoOkno;
import logika.Igra;
import logika.Igralec;
import logika.Poteza;

public class Alphabeta extends SwingWorker<Poteza, Object> {
	
	private GlavnoOkno master;
		
		private int globina;
		private static int MINUSNESKONCNO = -1000000000;
		private static int NESKONCNO = 1000000000;
		private static final int ALPHA = MINUSNESKONCNO;
		private static final int BETA = NESKONCNO;
		
		private Igralec jaz;
		
		public Alphabeta(GlavnoOkno master, int globina){
			this.master = master;
			this.globina = globina;
		}
	
		@Override
		protected Poteza doInBackground() throws Exception {
			Igra igra = master.kopijaIgre();
			this.jaz = igra.getIgralecNaPotezi();
			
			OcenjenaPoteza p = alphabeta(0, igra, ALPHA, BETA);
			
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
		
		private OcenjenaPoteza alphabeta (int i, Igra igra, int alpha, int beta) {
			Igralec trenutniIgralec = null;
			OcenjenaPoteza v;
			
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
			
			if (trenutniIgralec == this.jaz){
				v = new OcenjenaPoteza(null, MINUSNESKONCNO);
				
				for (Poteza p : igra.potezeIgralca(trenutniIgralec)){
					Igra kopijaIgre = new Igra(igra);
					kopijaIgre.igrajPotezo(p);

					OcenjenaPoteza vmesna = alphabeta(i+1, kopijaIgre, alpha, beta);
					
					if (v.poteza == null || v.vrednost < vmesna.vrednost){
						v = new OcenjenaPoteza(p, vmesna.vrednost);
						alpha = Math.max(alpha, v.vrednost);
						if (beta <= alpha){
							break;
						}
					}
				}
				return v;
				
			} else {
				v = new OcenjenaPoteza(null, NESKONCNO);
				
				for (Poteza p : igra.potezeIgralca(trenutniIgralec)){
					Igra kopijaIgre = new Igra(igra);
					kopijaIgre.igrajPotezo(p);
					
					OcenjenaPoteza vmesna = alphabeta(i+1, kopijaIgre, alpha, beta);
					
					if (v.poteza == null || v.vrednost > vmesna.vrednost){
						v =  new OcenjenaPoteza(p, vmesna.vrednost);
						beta = Math.min(beta, v.vrednost);
						if (beta <= alpha){
							break;
						}
					}
					
				}
				return v;
			}
		}
}
