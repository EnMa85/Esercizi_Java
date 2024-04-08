package app.gestore;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.time.LocalDate;

import app.database.DatabaseManager;
/**
 * Servlet implementation class ModificaServlet
 */
public class ModificaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {
        	// Definiamo alcune variabili comuni alle operazioni
        	String checkbox; // contiene l'id del libro corrente
        	int idLibro; // id inviato dalla checkbox con cast a int
	   		int inputCont = Integer.parseInt(request.getParameter("cont")); // totale righe input conteggiate nella jsp
	   		
        	String queryOperazione = ""; // query dell'operazione di modifica
        	
        	// Query che richiede un libro alla volta per salvarlo nella lista da restituire alla jsp
    	    String queryLibroModificato = "SELECT * FROM libri WHERE id = ?";
    	    // e variabile che contiene il libro da salvare in lista
    	    ArrayList<Map<String, Object>> libroModificato = new ArrayList<>();
    		
    		// Predisponiamo la mappa che conterrà i libri da stampare nella pagina di conferma
    		ArrayList<Map<String, Object>> mappaModifiche = new ArrayList<>();
    		
    		// Salviamo il nome del bottone che è stato premuto
            String azione = request.getParameter("azione");
            

            // Adesso definiamo un'azione diversa per ogni bottone
            switch (azione) {			 	
					 	
	            case "Elimina libri":
	            	
	            	// Iteriamo per il numero di righe del form
	        	    for (int i = 0; i < inputCont; i++) {
	        	    	
				   		// Recuperiamo i campi con la quantità da aggiornare e l'id
				 		checkbox = request.getParameter("checkbox_" + i);
				 		
				 		// Se il campo è stato selezionato
	        			if (checkbox != null) {
	        				
	        				// Aggiungiamo il libro alla lista, chiedendolo per id al db
	        				idLibro = Integer.parseInt(checkbox);
	        				libroModificato = DatabaseManager.eseguiQuery(queryLibroModificato, idLibro);
	        				mappaModifiche.add(libroModificato.get(0));
	        				
	        				// Effettuiamo l'operazione
			 				queryOperazione = "DELETE FROM libri WHERE id = ?";
			 				DatabaseManager.eseguiQuery(queryOperazione, idLibro);
					 	}
					 }
				 	 // Settiamo nella request il tipo di operazione effettuata
				 	 request.setAttribute("operazioneEffettuata", "eliminazione");
			 	    
				 	 break;
		 	    
            
			   	 case "Imposta disponibili":

	        	  	// Iteriamo per il numero di righe del form
	        	    for (int i = 0; i < inputCont; i++) {
	          			 
				   		// Recuperiamo i campi con la quantità da aggiornare e l'id
				 		checkbox = request.getParameter("checkbox_" + i);
				 		
				 		// Se il campo è stato selezionato
	        			if (checkbox != null) {
	        				
	        				idLibro = Integer.parseInt(checkbox);
	        				
	        				// Ricaviamo il record di prenotazione del libro e settiamo la data di rientro
	                    	LocalDate dataRientro = LocalDate.now();
	        				String queryRientroLibro = "UPDATE prenotazioni SET data_fine_effettiva = ? WHERE id_libro = ?";
	        				DatabaseManager.eseguiQuery(queryRientroLibro, dataRientro, idLibro);
	        				
	        				// Effettuiamo l'operazione
			 				queryOperazione = "UPDATE libri SET stato = 1 WHERE id = ?";
			 				DatabaseManager.eseguiQuery(queryOperazione, idLibro);
	        				
			 				// e chiediamo al db il libro aggiornato, per salvarlo in lista
	        				libroModificato = DatabaseManager.eseguiQuery(queryLibroModificato, idLibro);
	        				mappaModifiche.add(libroModificato.get(0));
					 	}
					 }
			 	    // Settiamo nella request il tipo di operazione effettuata
			 	    request.setAttribute("operazioneEffettuata", "impostaDisponibili");
		            
					break;
					
				
			   	 case "Imposta non disponibili":

		        	  	// Iteriamo per il numero di righe del form
		        	    for (int i = 0; i < inputCont; i++) {
		          			 
					   		// Recuperiamo i campi con la quantità da aggiornare e l'id
					 		checkbox = request.getParameter("checkbox_" + i);
					 		
					 		// Se il campo è stato selezionato
		        			if (checkbox != null) {
		        				
		        				idLibro = Integer.parseInt(checkbox);
		        				
		        				// Effettuiamo l'operazione
				 				queryOperazione = "UPDATE libri SET stato = 0 WHERE id = ?";
				 				DatabaseManager.eseguiQuery(queryOperazione, idLibro);
		        				
				 				// e chiediamo al db il libro aggiornato, per salvarlo in lista
		        				libroModificato = DatabaseManager.eseguiQuery(queryLibroModificato, idLibro);
		        				mappaModifiche.add(libroModificato.get(0));
						 	}
						 }
				 	    // Settiamo nella request il tipo di operazione effettuata
				 	    request.setAttribute("operazioneEffettuata", "impostaNonDisponibili");
			            
						break;
            }	
						
				            
	 	    // Inoltriamo alla jsp la lista dei prodotti modificati
	        request.setAttribute("mappaModifiche", mappaModifiche);
	 	    request.getRequestDispatcher("/WEB-INF/jsp/areaGestore/confermaOperazione.jsp").forward(request, response);
	 	    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
