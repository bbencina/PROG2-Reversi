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

public class GlavnoOkno extends JFrame implements ActionListener {

	private IgralnoPolje polje;
	
	private JLabel status;
	
	private Igra igra = null;
	
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
		
		// poženemo novo igro
		// privzeta igra bo igralec proti računalniku, igralec bo prvi na potezi
		
		// tukaj samo začasno
		osveziGUI();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == zapri_okno) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		
		else if(e.getSource() == dva_igralca) {
			 // začnemo igro za dva igralca
		}
		
		else if(e.getSource() == igralec_crni) {
			// igra proti računalniku, igralec ima prvo potezo
		}
		
		else if(e.getSource() == igralec_beli) {
			// igra proti računalniku, računalnik ima prvo potezo
		}
		 
	}
	
	public void osveziGUI() {
		if (igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(igra.stanje()) {
			case NA_POTEZI_BLACK: status.setText("Na potezi je črni"); break;
			case NA_POTEZI_WHITE: status.setText("Na potezi je beli"); break;
			case ZMAGA_BLACK: status.setText("Zmagal je črni"); break;
			case ZMAGA_WHITE: status.setText("Zmagal je beli"); break;
			case NEODLOCENO: status.setText("Neodločen izid"); break;
			}
		}
		polje.repaint();
	}

}
