package app.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.database.DatabaseManager;


// In questa classe raccogliamo alcuni metodi di utilità comune a più classi, in modo 
// da non dover riscrivere per intero questa parte di codice tutte le volte che ci serve

public class Utility {
	
	// Riceve in ingresso la mappa di credenziali recuperate dalla query al DB e le verifica
	public static boolean checkLogin (ArrayList<Map<String, Object>> listaMap, String user, String pw) {
		
		for (Map<String, Object> riga : listaMap) {
            try {
            	String username = (String) riga.get("username");
                String password = (String) riga.get("password");
                
                if (username.equals(user) && password.equals(pw)) {
                    return true; // se è stata trovata corrispondenza interrompi la ricerca e restituisci true
                }

            } catch (ClassCastException e) {
            	System.out.println("Errore durante il login: " + e.getMessage());
            }
        }
        return false; // se siamo arrivati qui vuole dire che non è stata trovata corrispondenza
    }
	
	
	// Restituisce elenco di tutti i libri ordinati per titolo, anche quelli non disponibili
	public static ArrayList<Map<String, Object>> elencoCompletoLibri() throws SQLException {
		
		ArrayList<Map<String, Object>> elencoLibri = new ArrayList<>();
	    
		String query = "SELECT * FROM libri ORDER BY titolo";
	    elencoLibri = DatabaseManager.eseguiQuery(query); // invia la query al gestore DB
        
        return elencoLibri;
	}
	
	
	// Restituisce elenco ordinato per titolo dei soli libri disponibili
	public static ArrayList<Map<String, Object>> elencoLibriDisponibili() throws SQLException {
		
		ArrayList<Map<String, Object>> elencoLibri = new ArrayList<>();
	    
		String query = "SELECT * FROM libri WHERE stato = 1 ORDER BY titolo";
		elencoLibri = DatabaseManager.eseguiQuery(query); // invia la query al gestore DB
        
        return elencoLibri;
	}

	
	// Riceve in ingresso la lista di mappe che genera il DatabaseManager e la trasforma in ArrayList di oggetti Libro
	public static ArrayList<Libro> creaListaDaMap (ArrayList<Map<String, Object>> listaMap) throws SQLException {
    	
		ArrayList<Libro> listaLibri = new ArrayList<>();		
		
		 for (Map<String, Object> riga : listaMap) {
			try {
				int id = (int) riga.get("id");
	            String titolo = (String) riga.get("titolo");
	            String autore = (String) riga.get("autore");
	            int anno = (int) riga.get("anno");
	            int periodoPrestito = (int) riga.get("periodo_prestito");
	            boolean stato = (boolean) riga.get("stato");
					    
	            Libro libro = new Libro();
	            libro.setId(id);
	            libro.setTitolo(titolo);
	            libro.setAutore(autore);
	            libro.setAnno(anno);
	            libro.setPeriodoPrestito(periodoPrestito);
	            libro.setStato(stato);
			    
			    listaLibri.add(libro);
			    
			} catch (ClassCastException e) {
	            System.out.println("Errore di casting durante la creazione della lista di libri: " + e.getMessage());
	        }
		}
		return listaLibri;
	}
}
