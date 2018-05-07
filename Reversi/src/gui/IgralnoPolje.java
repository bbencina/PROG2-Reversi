package gui;

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
		return Math.min(getWidth(), getHeight()) / Plosca.velikost;
	}
	
	private void narisiBlack(Graphics2D g, int vrstica, int stolpec) {
		
	}
	
	private void narisiWhite(Graphics2D g, int vrstica, int stolpec) {
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
