package distributore;

import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Menu extends JFrame {
	
	 public Distributore distributore;
	 String currentID;
	   
	    
	 public Menu(String titolo, Distributore distributore, String currentID) {     
		
		super(titolo);
        this.distributore = distributore;
        this.currentID = currentID;
              
                
        JLabel label = new JLabel("Benvenuto!");
        JButton buttonErogaCaffe = new JButton("Caffè");
        JButton buttonErogaThe = new JButton("The");
        JButton buttonErogaCioccolata = new JButton("Cioccolata");
        JButton buttonErogaAcqua = new JButton("Acqua");
        JButton buttonRicarica = new JButton("Ricarica chiavetta");
        JButton buttonSaldo = new JButton("Visualizza saldo");
        JButton buttonEsci = new JButton("Esci");
        
        
        buttonErogaCaffe.addActionListener(e -> {
	        distributore.erogaCaffè(this.currentID);
	        erogaPopup();
	    });
	
	    
	    buttonErogaThe.addActionListener(e -> {
	        distributore.erogaThe(this.currentID);
	        erogaPopup();
	    });

	    
	    buttonErogaCioccolata.addActionListener(e -> {
	        distributore.erogaCioccolata(this.currentID);
	        erogaPopup();
	    });
	    
	    
	    buttonErogaAcqua.addActionListener(e -> {
	        distributore.erogaAcqua(this.currentID);
	        erogaPopup();
	    });
	    
	    	
	    buttonRicarica.addActionListener(e -> {
	        String importoString = JOptionPane.showInputDialog(null, "Inserisci l'importo");
	        try {
	            double importo = Double.valueOf(importoString);
	            distributore.ricaricaChiavetta(this.currentID, importo);
	            JOptionPane.showMessageDialog(null, "Ricarica effettuata", "Conferma ricarica", JOptionPane.INFORMATION_MESSAGE);
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(null, "Importo non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
	        }
	    });
	    
	    
	    buttonSaldo.addActionListener(e -> {
	        String saldo = distributore.visualizzaSaldo(this.currentID);
	        JOptionPane.showMessageDialog(null, "Il saldo attuale è: € " + saldo, "Visualizza saldo", JOptionPane.INFORMATION_MESSAGE);
	    });
	    
	    
	    buttonEsci.addActionListener(e -> {

	        MainClass.setCurrentID(null);

	        MainClass.salvaDistributore(distributore);
	        JOptionPane.showMessageDialog(null, "Grazie e arrivederci!", "Visualizza saldo", JOptionPane.INFORMATION_MESSAGE);
	        // Chiusura di tutte le finestre
	        Frame[] frames = JFrame.getFrames();
	        for (Frame frame : frames) {
	            frame.dispose();
	        }
	    });
	          
	    
	    // Definizione elementi finestra
        this.setLayout(new BorderLayout()); 
        
	    JPanel panel = new JPanel();
        
        ModelloFinestra.Stile();
        Color backgroundColor = new Color(255, 255, 255);
        panel.setBackground(backgroundColor);
               
		this.setSize(340, 280);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(300, 40));
		buttonErogaCaffe.setPreferredSize(new Dimension(150, 40));
		buttonErogaThe.setPreferredSize(new Dimension(150, 40));
		buttonErogaCioccolata.setPreferredSize(new Dimension(150, 40));
		buttonErogaAcqua.setPreferredSize(new Dimension(150, 40));
		buttonRicarica.setPreferredSize(new Dimension(150, 40));
		buttonSaldo.setPreferredSize(new Dimension(150, 40));
		buttonEsci.setPreferredSize(new Dimension(300, 40));
		
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		buttonErogaCaffe.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonErogaThe.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buttonErogaCioccolata.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonErogaAcqua.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buttonRicarica.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonSaldo.setAlignmentX(Component.RIGHT_ALIGNMENT);
		buttonEsci.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panel.add(label);
        panel.add(buttonErogaCaffe);
	    panel.add(buttonErogaThe);
	    panel.add(buttonErogaCioccolata);
	    panel.add(buttonErogaAcqua);
	    panel.add(buttonRicarica);     
	    panel.add(buttonSaldo);
        panel.add(buttonEsci);

		this.add(panel);	
		
	    this.setVisible(true);
	}
	
	
	private void erogaPopup() {
	    JOptionPane pane = new JOptionPane("Erogazione in corso...\n\nAttendere prego.", JOptionPane.INFORMATION_MESSAGE);
	    JDialog dialog = pane.createDialog(null, "Messaggio");
	    dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

	    TimerTask task = new TimerTask() {
	        public void run() {
	            dialog.setVisible(false);
	            dialog.dispose();
	            JOptionPane.showMessageDialog(null, "Erogazione effettuata.", "Messaggio", JOptionPane.INFORMATION_MESSAGE);
	        }
	    };

	    Timer timer = new Timer();
	    timer.schedule(task, 3000);
	    
	    dialog.setVisible(true);
	}
	
}
