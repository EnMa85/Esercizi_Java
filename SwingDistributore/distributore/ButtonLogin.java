package distributore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ButtonLogin implements ActionListener {

	private Home interfaccia;
	private MainClass mainClass;
	
	
	public ButtonLogin (Home interfaccia, MainClass mainClass) {
		
		this.interfaccia = interfaccia;	
		this.mainClass = mainClass; 
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String id = interfaccia.campoID.getText();
		String password = interfaccia.campoPass.getText();
		
		boolean accesso = false;
		
        for (Chiavetta c : interfaccia.distributore.getChiavette()) {
            if (c.getID().equals(id) && c.getPassword().equals(password)) {
                accesso = true;
                break;
            }
        }
		
		
        if (accesso) {
            JOptionPane.showMessageDialog(null, "Login effettuato", "Login", JOptionPane.INFORMATION_MESSAGE);
            MainClass.avviaMenu(id);
        } else {
            JOptionPane.showMessageDialog(null, "Login fallito", "Login", JOptionPane.ERROR_MESSAGE);
        }
	}

}