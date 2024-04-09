package distributore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.SwingUtilities;

public class MainClass {

	
	private static final String FILE_PATH = "distributore.ser";
	
	private static Distributore distributore;
	static String currentID;
	static Home home;
    
	public static void main(String[] args) {

        distributore = caricaDistributore();
        MainClass mainClass = new MainClass();
        
        mainClass.avviaInterfaccia();
	}
        
        
    private void avviaInterfaccia() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                home = new Home("Login", distributore, MainClass.this);
                home.setLocationRelativeTo(null);
                home.setVisible(true);
            }
        });
    }
    
    
    static void avviaMenu(String id) {
    	currentID = id;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	home.dispose();
                Menu menu = new Menu("Menu", distributore, currentID);
                menu.setLocationRelativeTo(null);
                menu.setVisible(true);
            }
        });
    }
    
   
	 private static Distributore caricaDistributore() {
	     Distributore distributore = null;

	     try (FileInputStream fileIn = new FileInputStream(FILE_PATH);
	          ObjectInputStream in = new ObjectInputStream(fileIn)) {

	         distributore = (Distributore) in.readObject();
	         System.out.println("Distributore caricato.\n");
	         
	     } catch (IOException | ClassNotFoundException e) {
	         distributore = new Distributore();
	         System.out.println("Nessun file trovato. Creazione di un nuovo distributore.\n\n");
	     }
	     
	     return distributore;
	 }
 
 
 
	static void salvaDistributore(Distributore distributore) {
	 	
	    try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
	    
	        out.writeObject(distributore);
	        System.out.println("\n\nDistributore salvato.");
	     
	    } catch (IOException e) {
	        System.out.println("Errore durante il salvataggio del distributore: " + e.getMessage());
	    }
	 }
	 
	 
    public static String getCurrentID() {
        return currentID;
    }
    
    
    public static void setCurrentID(String id) {
        currentID = id;
    }
}
