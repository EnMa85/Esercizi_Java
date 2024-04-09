package app.models;

import java.sql.SQLException;
import java.util.ArrayList;
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
                String password = (String) riga.get("password"); //password già cripata da js

                if (username.equals(user) && password.equals(pw)) {
                    return true; // se è stata trovata corrispondenza interrompi la ricerca e restituisci true
                }

            } catch (Exception e) {
                System.out.println("Errore durante il login: " + e.getMessage());
            }
        }
        return false; // se siamo arrivati qui vuole dire che non è stata trovata corrispondenza
    }
	
	
	// Aggiorna la lista dal database, e rimanda eventuali eccezioni alla classe chiamante
	public static ArrayList<Prodotto> aggiornaListaProdotti() throws SQLException {
		
		ArrayList<Map<String, Object>> listaMap = new ArrayList<>();
	    ArrayList<Prodotto> listaProdotti = new ArrayList<Prodotto>();
	    
	    // prende tutti i campi della tabella con quantità non 0, e li ordina per nome
		String query = "SELECT * FROM prodotti WHERE quantita != 0 ORDER BY nome";
	    listaMap = DatabaseManager.eseguiQuery(query); // invia la query al gestore DB
	    
        listaProdotti = creaListaDaMap(listaMap); // copia la mappa in un ArrayList
        // (semplicemente perchè stavamo lavorando con gli ArrayList)
        
        return listaProdotti;
	}

	
	// Riceve in ingresso la lista di mappe che genera il DatabaseManager e la trasforma in ArrayList di oggetti Prodotto
	public static ArrayList<Prodotto> creaListaDaMap (ArrayList<Map<String, Object>> listaMap) throws SQLException {
    	
		ArrayList<Prodotto> listaProdotti = new ArrayList<>();		
		
		 for (Map<String, Object> riga : listaMap) {
			try {
				int id = (int) riga.get("id");
	            String nome = (String) riga.get("nome");
	            double prezzo = (double) riga.get("prezzo");
	            int quantita = (int) riga.get("quantita");
					    
			    Prodotto prodotto = new Prodotto();
			    prodotto.setID(id);
			    prodotto.setNome(nome);
			    prodotto.setPrezzo(prezzo);
			    prodotto.setQuantita(quantita);
			    
			    listaProdotti.add(prodotto);
			    
			} catch (ClassCastException e) {
	            System.out.println("Errore di casting durante la creazione della lista di prodotti: " + e.getMessage());
	        }
		}
		return listaProdotti;
	}
}
