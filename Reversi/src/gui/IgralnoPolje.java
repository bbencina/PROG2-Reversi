package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import logika.Plosca;

@SuppressWarnings("serial")
public class IgralnoPolje extends JPanel implements MouseListener{
	
	private GlavnoOkno master;
	
	private final static double DEBELINA_CRTE = 0.1;
	private final static double PADDING = 0.1;
	private final static int ZAMIK_X = 5;
	private final static int ZAMIK_Y = 5;
	
	private final static Color barvaCrte = Color.RED;
	
	public IgralnoPolje(GlavnoOkno master) {
		super();
		Color dark_green = new Color(0, 102, 0);
		setBackground(dark_green);
		this.master = master;
		this.addMouseListener(this);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(512, 512);
	}
	
	private double stranicaPolja() {
		return Math.min(getWidth(), getHeight()) / Plosca.velikost - ZAMIK_X;
	}
	
	private void narisiPloscek(Graphics2D g, Color c,
							int vrstica, int stolpec) {
		double dim = this.stranicaPolja();
		double premer = dim * (1.0 - DEBELINA_CRTE - 2.0 * PADDING);
		double robY = dim * (vrstica + 0.5 * DEBELINA_CRTE + PADDING);
		double robX = dim * (stolpec + 0.5 * DEBELINA_CRTE + PADDING);
		g.setColor(c);
		g.drawOval((int)(robX + ZAMIK_X), (int)(robY + ZAMIK_Y),
				   (int)premer, (int)premer);
		g.fillOval((int)(robX + ZAMIK_X), (int)(robY + ZAMIK_Y),
				   (int)premer, (int)premer);
	}
	
	
	@Override
	protected void paintComponent(Graphics arg_g) {
		super.paintComponent(arg_g);
		Graphics2D g = (Graphics2D) arg_g;
		double dim = this.stranicaPolja();
		
		// risanje črt
		g.setColor(barvaCrte);
		g.setStroke(new BasicStroke((float) (dim * DEBELINA_CRTE)));
		for (int i = 0; i <= Plosca.velikost; i++){
			g.drawLine((int)(i * dim + ZAMIK_X),
				    (int)(DEBELINA_CRTE * dim),
				    (int)(i * dim + ZAMIK_X),
				    (int)((Plosca.velikost) * dim + ZAMIK_Y));
			g.drawLine((int)(DEBELINA_CRTE * dim),
				    (int)(i * dim + ZAMIK_Y),
				    (int)((Plosca.velikost) * dim + ZAMIK_X),
				    (int)(i * dim + ZAMIK_Y));
		}
		
		// risanje ploščkov
		Plosca plosca = master.getPlosca();
		if (plosca != null) {
			for (int i = 0; i < Plosca.velikost; i++){
				for (int j = 0; j < Plosca.velikost; j++) {
					switch(plosca.polje[i][j]){
					case BLACK: narisiPloscek(g, Color.BLACK, i, j); break;
					case WHITE: narisiPloscek(g, Color.WHITE, i, j); break;
					default: break;
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		System.out.println("Clicked!");
		int x = event.getX(), y = event.getY();
		int dim = (int) this.stranicaPolja();
		int i = (y - ZAMIK_Y) / dim;
		double di = ((y - ZAMIK_Y) % dim) / this.stranicaPolja();
		int j = (x - ZAMIK_X) / dim;
		double dj = ((x - ZAMIK_X) % dim) / this.stranicaPolja();
		
		System.out.println("" + i + " " + j);
		
		if (0 <= i && i < Plosca.velikost &&
			0.5 * DEBELINA_CRTE < di && di < 1.0 - 0.5 * DEBELINA_CRTE &&
			0 <= j && j < Plosca.velikost && 
			0.5 * DEBELINA_CRTE < dj && dj < 1.0 - 0.5 * DEBELINA_CRTE) {
				master.klikniPolje(i, j);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
