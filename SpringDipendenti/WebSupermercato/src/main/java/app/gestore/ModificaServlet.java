package app.gestore;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import app.database.DatabaseManager;
import app.models.Prodotto;
import app.models.Utility;

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
	   		int inputCont = Integer.parseInt(request.getParameter("cont")); // totale righe input
	   		String idDaForm; // id del prodotto corrente ricevuto dal form
	   		int idProdotto; // id con cast a int
        	String queryOperazione = ""; // query dell'operazione di modifica

    		// e predisponiamo una lista che conterrà solo gli id dei prodotti modificati
    		String idModificati = "";
    		
    		// Predisponiamo le liste che contengono i dati da modificare
    		String[] inputDaForm;
    		ArrayList<Map<String, Object>> mappaModifiche = new ArrayList<>();
      		ArrayList<Prodotto> listaModifiche = new ArrayList<>();
    		
    		// Salviamo il nome del bottone che è stato premuto
            String azione = request.getParameter("azione");
            
            
            // Adesso definiamo un'azione diversa per ogni bottone
            switch (azione) {			 	
					 	
            	 case "Aggiorna prezzo":
            		 
                    // Questa query conterrà la lista dei prodotti modificati, ottenuta a partire dagli id
                   // La clausula IN funziona come un array ed inseriremo man mano i segnaposti dei valori
          	 		String queryPrezziVariati = "SELECT * FROM prodotti WHERE id IN(?)";
          	 		
          	 		String prezzoDaForm;
            		double prezzo;          		            		
                 	        	 		
            		for (int i = 0; i < inputCont; i++) {
            			 	 
            			// Recuperiamo i campi con il prezzo da aggiornare e l'id
            			prezzoDaForm = request.getParameter("newPrezzo_" + i);
            			idDaForm = request.getParameter("idProdotto_" + i);
					 	
              	    	// Se il campo è compilato
            			if (prezzoDaForm != "") {
					 			
            				// eseguiamo la modifica in db
			 				prezzo = Double.parseDouble(prezzoDaForm);
			 				idProdotto = Integer.parseInt(idDaForm);
					 				
			 				queryOperazione = "UPDATE prodotti SET prezzo = ? WHERE id = ?";
			 				DatabaseManager.eseguiQuery(queryOperazione, prezzo, idProdotto);
	
			 				// e memorizziamo l'id prodotto nella lista degli id
			 				idModificati += idProdotto; 
					 	}
            		}
		            // A partire dagli id dei prodotti modificati, ricaviamo la lista da stampare nella jsp
			 	    mappaModifiche = DatabaseManager.eseguiQuery(queryPrezziVariati, idModificati);
			 	    // Creiamo un array list dalla mappa
			 	    listaModifiche = Utility.creaListaDaMap(mappaModifiche);
			 	    
			 	    // Settiamo nella request il tipo di operazione effettuata
			 	    request.setAttribute("operazioneEffettuata", "variazioneQuantita");
			            
            		break;
		 	    
            
			   	 case "Aggiorna quantita'":
			   		 
			   		// Questa query chiede al DB una lista di tutti i prodotti che abbiamo modificato.
                 	// La clausula IN funziona come un array e conterrà gli id dei prodotti interessati.
         	 		String queryQuantitaVariate = "SELECT * FROM prodotti WHERE id IN(?)";;
			 		
         	 		String quantitaDaForm;
         	 		int quantita;

	           		for (int i = 0; i < inputCont; i++) {
           			 
				   		// Recuperiamo i campi con le quantità da aggiornare e l'id
				 		quantitaDaForm = request.getParameter("newQuantita_" + i);
				 		idDaForm = request.getParameter("idProdotto_" + i);
					 	
				 		// Se il campo è compilato
            			if (quantitaDaForm != "") {
					 		
            				// eseguiamo la modifica in db
            				quantita = Integer.parseInt(quantitaDaForm);
			 				idProdotto = Integer.parseInt(idDaForm);
					 				
			 				queryOperazione = "UPDATE prodotti SET quantita = ? WHERE id = ?";
			 				DatabaseManager.eseguiQuery(queryOperazione, quantita, idProdotto);
					 				
			 				// e memorizziamo l'id prodotto nella lista degli id
			 				idModificati += idProdotto; 
					 	}
	           		}
		            // A partire dagli id dei prodotti modificati, ricaviamo la lista da stampare nella jsp
			 	    mappaModifiche = DatabaseManager.eseguiQuery(queryQuantitaVariate, idModificati);
			 	    // Creiamo un array list dalla mappa
			 	    listaModifiche = Utility.creaListaDaMap(mappaModifiche);
			 	    
			 	    // Settiamo nella request il tipo di operazione effettuata
			 	    request.setAttribute("operazioneEffettuata", "variazioneQuantita");
		            
					break;
						
						
			   	case "Elimina selezionati":
			   		
			  		String checkbox;
			   		
            		// Predisponiamo una query per salvare il prodotto prima di eliminarlo
            	    String querySalvaInLista = "SELECT * FROM prodotti WHERE id = ?";
            	    ArrayList<Map<String, Object>> mappaProdottoCancellato = new ArrayList<>();
            	    ArrayList<Prodotto> prodottoCancellato = new ArrayList<>();
            	    
            	    for (int i = 0; i < inputCont; i++) {
              			 
				   		// Recuperiamo i campi con gli id dei libri da eliminare
				 		checkbox = request.getParameter("cancellaCheck_" + i);
				 		idDaForm = request.getParameter("idProdotto_" + i);
				 		
				 		// Se il campo è stato selezionato
            			if (checkbox != null) {
            				
            				idProdotto = Integer.parseInt(idDaForm);
            				mappaProdottoCancellato = DatabaseManager.eseguiQuery(querySalvaInLista, idProdotto);
            				prodottoCancellato = Utility.creaListaDaMap(mappaProdottoCancellato);	
            				listaModifiche.add(prodottoCancellato.get(0));
			 				queryOperazione = "DELETE FROM prodotti WHERE id = ?";
			 				DatabaseManager.eseguiQuery(queryOperazione, idProdotto);
					 	}
					 }
				 	 // Settiamo nella request il tipo di operazione effettuata
				 	 request.setAttribute("operazioneEffettuata", "eliminazione");
				 	    
					 break;
            }

            
	 	    // Inoltriamo alla jsp la lista dei prodotti modificati
	        request.setAttribute("listaModifiche", listaModifiche);
	 	    request.getRequestDispatcher("/WEB-INF/jsp/areaGestore/confermaOperazione.jsp").forward(request, response);
	 	    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
