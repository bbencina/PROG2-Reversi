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
import logika.Polje;
import logika.Poteza;
import logika.Stanje;
import logika.Igralec;

@SuppressWarnings("serial")
public class GlavnoOkno extends JFrame implements ActionListener {

	private IgralnoPolje polje;
	private Igra igra = null;
	
	private Okupator okupatorBlack = null, okupatorWhite = null;
	
	private JLabel status;
	
	private JMenuItem zapri_okno;
	private JMenuItem dva_igralca;
	private JMenuItem igralec_beli;
	private JMenuItem igralec_crni;
	
	public GlavnoOkno() {
		this.setTitle("Reversi");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// menu okno
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		
		JMenu okno_menu = new JMenu("Okno");
		menu_bar.add(okno_menu);
		
		zapri_okno = new JMenuItem("Zapri");
		okno_menu.add(zapri_okno);
		zapri_okno.addActionListener(this);
		
		// menu nova igra
		JMenu igra_menu = new JMenu("Nova igra");
		menu_bar.add(igra_menu);
		
		dva_igralca = new JMenuItem("Dva igralca");
		igra_menu.add(dva_igralca);
		dva_igralca.addActionListener(this);
		
		JMenu izberi_barvo = new JMenu("Igralec proti računalniku");
		igra_menu.add(izberi_barvo);
		
		igralec_crni = new JMenuItem("Črni");
		izberi_barvo.add(igralec_crni);
		igralec_crni.addActionListener(this);
		
		igralec_beli = new JMenuItem("Beli");
		izberi_barvo.add(igralec_beli);
		igralec_beli.addActionListener(this);
		
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
		 * Privzeto je, da sta oba igralca cloveka.
		 */
		nova_igra(true, true);
	}
	
	public void nova_igra(boolean clovekBlack, boolean clovekWhite) {
		if (okupatorBlack != null) okupatorBlack.prekini();
		if (okupatorWhite != null) okupatorWhite.prekini();
		
		this.igra = new Igra();
		
		if (clovekBlack && clovekWhite){
			okupatorBlack = new Clovek(this);
			okupatorWhite = new Clovek(this);
		}
		
		switch (igra.stanjeIgre){
		case NA_POTEZI_BLACK: okupatorBlack.zacni_potezo(); break;
		case NA_POTEZI_WHITE: okupatorWhite.zacni_potezo(); break;
		default: break;
		}
		
		osveziGUI();
		polje.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == zapri_okno) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
		else if(e.getSource() == dva_igralca) {
			// začnemo igro za dva igralca
			nova_igra(true, true);
		}
		
		else if(e.getSource() == igralec_crni) {
			// igra proti računalniku, igralec ima prvo potezo
		}
		
		else if(e.getSource() == igralec_beli) {
			// igra proti računalniku, računalnik ima prvo potezo
		}
		 
	}
	
	public void igraj(Poteza p) {
		this.igra.igrajPotezo(p);
		this.osveziGUI();
		 
		// Potrebno dopolnitve, ko bodo ustvarjeni razredi za igralce.
		switch (this.igra.stanjeIgre) {
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
			switch(this.igra.stanjeIgre) {
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
			switch (igra.stanjeIgre){
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
