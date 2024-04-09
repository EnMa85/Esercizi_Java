package distributore;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Home extends JFrame {
	
	
    protected Distributore distributore;
    private MainClass mainClass;
    
    
    protected JLabel labelID;
    protected JTextField campoID;
    protected JLabel labelPass;
    protected JTextField campoPass;
    protected JButton buttonCreaID;
    protected JButton buttonAccedi;
    
    
    public Home(String titolo, Distributore distributore, MainClass mainClass) {
    	
        super(titolo);
        this.distributore = distributore;
        this.mainClass = mainClass;

        ModelloFinestra.Stile();
        
        Color backgroundColor = new Color(255, 255, 255);
        JPanel panel = new JPanel((LayoutManager) new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(backgroundColor);
        
        panel.add(Box.createVerticalStrut(20)); 
        
        JLabel labelBenvenuto = new JLabel("Benvenuto!");
        panel.add(labelBenvenuto);

        panel.add(Box.createVerticalStrut(10)); 
        
        JLabel labelCredenziali = new JLabel("Inserisci credenziali oppure crea un nuovo ID");
        panel.add(labelCredenziali);

        panel.add(Box.createVerticalStrut(50)); 
        
        labelID = new JLabel("Inserisci l'ID della chiavetta");
        campoID = new JTextField(20);
        panel.add(labelID);
        panel.add(campoID);
        
        panel.add(Box.createVerticalStrut(40)); 

        labelPass = new JLabel("Inserisci la password");
        campoPass = new JTextField(20);
        panel.add(labelPass);
        panel.add(campoPass);

        
        panel.add(Box.createVerticalStrut(40)); 
        
        
        buttonCreaID = new JButton("Crea ID");
        panel.add(buttonCreaID);

        buttonAccedi = new JButton("Accedi");
        panel.add(buttonAccedi);

        buttonCreaID.addActionListener(new ButtonNewID(this));
        buttonAccedi.addActionListener(new ButtonLogin(this, mainClass));
        
        panel.add(Box.createVerticalStrut(10)); 
        
        this.add(panel);
        this.setSize(320, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}