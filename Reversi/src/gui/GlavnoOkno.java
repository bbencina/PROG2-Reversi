package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igra;
import logika.Plosca;
import logika.Poteza;
import logika.Igralec;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {

	private IgralnoPolje polje;
	private Igra igra = null;
	
	private Okupator okupatorBlack = null, okupatorWhite = null;
	
	private JLabel status;
	
	private JMenuItem zapriOkno;
	private JMenuItem dvaIgralca;
	private JMenuItem igralecBeli;
	private JMenuItem igralecCrni;
	
	public GlavnoOkno() {
		this.setTitle("Reversi");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// menu okno
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu oknoMenu = new JMenu("Okno");
		menuBar.add(oknoMenu);
		
		zapriOkno = new JMenuItem("Zapri");
		oknoMenu.add(zapriOkno);
		zapriOkno.addActionListener(this);
		
		// menu nova igra
		JMenu igraMenu = new JMenu("Nova igra");
		menuBar.add(igraMenu);
		
		dvaIgralca = new JMenuItem("Dva igralca");
		igraMenu.add(dvaIgralca);
		dvaIgralca.addActionListener(this);
		
		JMenu izberiBarvo = new JMenu("Igralec proti računalniku");
		igraMenu.add(izberiBarvo);
		
		igralecCrni = new JMenuItem("Črni");
		izberiBarvo.add(igralecCrni);
		igralecCrni.addActionListener(this);
		
		igralecBeli = new JMenuItem("Beli");
		izberiBarvo.add(igralecBeli);
		igralecBeli.addActionListener(this);
		
		// igralno polje
		polje = new IgralnoPolje(this);
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(polje, polje_layout);
		
		// statusna vrstica za sporočila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    Font.BOLD,
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		/**
		 * Privzeto je, da sta oba igralca človeka.
		 */
		novaIgra(true, true);
	}
	
	public void novaIgra(boolean clovekBlack, boolean clovekWhite) {
		if (okupatorBlack != null) okupatorBlack.prekini();
		if (okupatorWhite != null) okupatorWhite.prekini();
		
		this.igra = new Igra();
		
		if (clovekBlack && clovekWhite) {
			okupatorBlack = new Clovek(this);
			okupatorWhite = new Clovek(this);
		}
		
		if (clovekBlack && !clovekWhite) {
			okupatorBlack = new Clovek(this);
			// okupatorWhite = racunalnik
		}
		
		if (!clovekBlack && clovekWhite) {
			// okupatorBlack = racunalnik
			okupatorWhite = new Clovek(this);
		}
		
		switch (igra.stanje()){
		case NA_POTEZI_BLACK: okupatorBlack.zacni_potezo(); break;
		case NA_POTEZI_WHITE: okupatorWhite.zacni_potezo(); break;
		default: break;
		}
		
		osveziGUI();
		polje.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == zapriOkno) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
		else if(e.getSource() == dvaIgralca) {
			// začnemo igro za dva igralca
			novaIgra(true, true);
		}
		
		else if(e.getSource() == igralecCrni) {
			// igra proti računalniku, igralec ima prvo potezo
			novaIgra(true, false);
			
		}
		
		else if(e.getSource() == igralecBeli) {
			// igra proti računalniku, računalnik ima prvo potezo
			novaIgra(false, true);
		}
		 
	}
	
	public void igraj(Poteza p) {
		this.igra.igrajPotezo(p);
		this.osveziGUI();
		
		switch (this.igra.stanje()) {
		case NA_POTEZI_BLACK: okupatorBlack.zacni_potezo(); break;
		case NA_POTEZI_WHITE: okupatorWhite.zacni_potezo(); break;
		case ZMAGA_BLACK: break;
		case ZMAGA_WHITE: break;
		case NEODLOCENO: break;
		}
	}
	
	public void osveziGUI() {
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(this.igra.stanje()) {
			case NA_POTEZI_BLACK: status.setText("Na potezi je črni"); break;
			case NA_POTEZI_WHITE: status.setText("Na potezi je beli"); break;
			case ZMAGA_BLACK: status.setText("Zmagal je črni"); break;
			case ZMAGA_WHITE: status.setText("Zmagal je beli"); break;
			case NEODLOCENO: status.setText("Neodločen izid"); break;
			}
		}
		polje.repaint();
	}
	
	public void klikniPolje(int i, int j){
		if (igra != null) {
			switch (igra.stanje()){
			case NA_POTEZI_BLACK:
				okupatorBlack.klik(i, j);
				break;
			case NA_POTEZI_WHITE:
				okupatorWhite.klik(i, j);
				break;
			default: break;
			}
		}
	}
	
	public Igra kopijaIgre() {
		return new Igra(this.igra);
	}
	
	public Plosca getPlosca() {
		return (igra == null ? null : igra.getPlosca());
	}
	
	public Igralec getIgralec() {
		return (igra == null ? null : igra.getIgralecNaPotezi());
	}

}
