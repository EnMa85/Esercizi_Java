package distributore;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Distributore implements Serializable {
	
	private DecimalFormat decimalFormat = new DecimalFormat("0.00");
	HashMap<String, Chiavetta> mappaChiavette;
	
	
	public Distributore() {
		 mappaChiavette = new HashMap<>();
	}
	
	public boolean checkID(String id, String password) {
        Chiavetta chiavetta = mappaChiavette.get(id);
        return chiavetta != null && chiavetta.getPassword().equals(password);
	}

	
	public ArrayList<Chiavetta> getChiavette() {
		return new ArrayList<>(mappaChiavette.values());
    }
	
	
    public String creaChiavetta() {
    	int newID = mappaChiavette.size() + 1;
        String id = String.format("%05d", newID);
        
        Random random = new Random();
        int num = random.nextInt(100000);
        String password = String.format("%05d", num);
        
        Chiavetta chiavetta = new Chiavetta(id, password);
        mappaChiavette.put(chiavetta.getID(), chiavetta);
        return id;
    }
    
	

	public void erogaCaffè(String id) {
		 mappaChiavette.get(id).saldo -= 1;
	}
	
	public void erogaThe(String id) {
		mappaChiavette.get(id).saldo -= 2;
	}
	
	public void erogaCioccolata(String id) {
		mappaChiavette.get(id).saldo -= 2;
	}
	
	public void erogaAcqua(String id) {
		mappaChiavette.get(id).saldo -= 1;
	}
	
	public void ricaricaChiavetta(String id, double importo) {
		mappaChiavette.get(id).versa(importo);
	}
	
	public String visualizzaSaldo(String id) {
		return decimalFormat.format(mappaChiavette.get(id).saldo());
	}
}
