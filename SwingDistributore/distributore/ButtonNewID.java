package distributore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class ButtonNewID implements ActionListener {
    
    private Home interfaccia;
    
    public ButtonNewID(Home interfaccia) {
        this.interfaccia = interfaccia;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String newID = interfaccia.distributore.creaChiavetta();
        String password = interfaccia.distributore.mappaChiavette.get(newID).getPassword();
        JOptionPane.showMessageDialog(null, "Nuovo ID generato: " + newID + "\nPassword: " + password, "Nuovo ID Creato", JOptionPane.INFORMATION_MESSAGE);
    }
}