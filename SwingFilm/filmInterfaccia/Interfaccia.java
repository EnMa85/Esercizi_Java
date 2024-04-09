package filmInterfaccia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Interfaccia extends JFrame {

	public Interfaccia(String titolo) {
		
	    super(titolo);
    
	    ModelloFinestra.Stile();
	    
		JLabel labelBenvenuto = new JLabel("Benvenuto nella cineteca!");     
        JButton buttonStampaFilm = new JButton("Stampa elenco film");  
        JButton buttonInserisciFilm = new JButton("Inserisci nuovo film");     
        JButton buttonRicercaGenere = new JButton("Ricerca per genere");       
        JButton buttonRicercaAnno = new JButton("Ricerca per anno");
        JButton buttonVariaAnno = new JButton("Modifica anno");
        JButton buttonEliminaFilm = new JButton("Elimina film"); 
        JButton buttonEsci = new JButton("Esci dal programma");   
   
        
        this.setLayout(new BorderLayout()); 
        
	    JPanel panel = new JPanel();
        
        ModelloFinestra.Stile();
        Color backgroundColor = new Color(255, 255, 255);
        panel.setBackground(backgroundColor);
        
        this.setSize(340, 280);
        this.setLocationRelativeTo(null);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        labelBenvenuto.setHorizontalAlignment(SwingConstants.CENTER);
        labelBenvenuto.setPreferredSize(new Dimension(300, 40));
        buttonStampaFilm.setPreferredSize(new Dimension(150, 40));
        buttonInserisciFilm.setPreferredSize(new Dimension(150, 40));
        buttonRicercaGenere.setPreferredSize(new Dimension(150, 40));
        buttonRicercaAnno.setPreferredSize(new Dimension(150, 40));
        buttonVariaAnno.setPreferredSize(new Dimension(150, 40));
		buttonEliminaFilm.setPreferredSize(new Dimension(150, 40));
		buttonEsci.setPreferredSize(new Dimension(300, 40));

		labelBenvenuto.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonStampaFilm.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonInserisciFilm.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buttonRicercaGenere.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonRicercaAnno.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buttonVariaAnno.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonEliminaFilm.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buttonEsci.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		panel.add(labelBenvenuto);
        panel.add(buttonStampaFilm);
	    panel.add(buttonInserisciFilm);
	    panel.add(buttonRicercaGenere);
	    panel.add(buttonRicercaAnno);
	    panel.add(buttonVariaAnno);     
	    panel.add(buttonEliminaFilm);
        panel.add(buttonEsci);
        
        
        this.add(panel);
        this.setVisible(true);
        
        
        buttonStampaFilm.addActionListener(e -> {
        	UsaFilmPerInterfaccia.stampaFilm();
	    });

        buttonInserisciFilm.addActionListener(e -> {
        	UsaFilmPerInterfaccia.inserisciFilm();
	    });
        
        buttonRicercaGenere.addActionListener(e -> {
        	UsaFilmPerInterfaccia.cercaGenere();
	    });
        
        buttonRicercaAnno.addActionListener(e -> {
        	UsaFilmPerInterfaccia.cercaAnno();
	    });
        
        buttonVariaAnno.addActionListener(e -> {
        	UsaFilmPerInterfaccia.modificaAnno();
	    });
        
        buttonEliminaFilm.addActionListener(e -> {
        	UsaFilmPerInterfaccia.cancellaFilm();
	    });
	    
        buttonEsci.addActionListener(e -> {
	        JOptionPane.showMessageDialog(null, "Grazie e arrivederci!", "Conferma uscita", JOptionPane.INFORMATION_MESSAGE);
	        // Chiusura di tutte le finestre
	        Frame[] frames = JFrame.getFrames();
	        for (Frame frame : frames) {
	            frame.dispose();
	        }
	    });
	}
	
	
    private Component spazio(int width, int height) {
        JPanel spazio = new JPanel();
        spazio.setPreferredSize(new Dimension(width, height));
        return spazio;
    }
    
    
	public static void main(String[] args) {
		Interfaccia interfaccia = new Interfaccia("I nostri film");
	}

}
