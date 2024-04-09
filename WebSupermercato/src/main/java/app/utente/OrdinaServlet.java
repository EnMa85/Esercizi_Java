package app.utente;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import app.database.DatabaseManager;
import app.models.Prodotto;
import app.models.Utility;

/**
 * Servlet implementation class OrdinaServlet
 */
public class OrdinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdinaServlet() {
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
		
		// Accediamo alla sessione inizializzata nella SessioneServlet
		HttpSession session = request.getSession();
		// Verifichiamo che l'utente sia ancora loggato
	    if (session == null || session.getAttribute("utenteAttivo") == null) {
	        ((HttpServletResponse) response).sendRedirect("index.jsp");
	     }
	    
		try {				
			// Crea una mappa per memorizzare ID e quantità dei prodotti ordinati
	        Map<Integer, Integer> mappaQuantita = new HashMap<>();
	        
	        /* Recuperiamo la lista con id e quantità. 
	         * Dal form riceviamo, per ogni prodotto, un'unica stringa 
	         * in formato quantità_id (l'underscore serve da separatore)
	         * 
	         * Prima dobbiamo ricavare la lista dei parametri con la classe Enumeration,
	         * poi splitteremo le stringhe al '_' ricavando l'id contenuto nella seconda parte
	         */
	        Enumeration<String> parametri = request.getParameterNames();
	        
	        while (parametri.hasMoreElements()) {
	            String parametro = parametri.nextElement();

	            // Recuperiamo l'ID del prodotto dal parametro, escludento la stringa "quantita_"
	            int idProdotto = Integer.parseInt(parametro.substring("quantita_".length()));
	            
	         	// La quantità va pescata dalla request tramite il nome del parametro
	            String quantitaString = request.getParameter(parametro);
	            
	            // Se non è vuota, effettiamo il cast a int
	            if (quantitaString != null && !quantitaString.isEmpty()) {
	                int quantitaOrdinata = Integer.parseInt(quantitaString);
	                
	                // Se la quantità è maggiore di 0, aggiungiamo id e quantità alla mappa, in forma chiave-valore
		            if (quantitaOrdinata > 0) {
		                mappaQuantita.put(idProdotto, quantitaOrdinata);
		            }
	            }
	        }
	        
	        // Se non è stato selezionato alcun prodotto rimanda alla home Utente
	        if (mappaQuantita.isEmpty()) {     
	        	request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/erroreOrdine.jsp").forward(request, response);
	        }
               
	        
	        
	        // Se non siamo stati reindirizzati, la mappa non è vuota, procediamo
	        
	        
	        
	        // Ci serve un ArrayList che contenga i prodotti ordinati
	        
	        // Per farlo creiamo una select che contenga una lista di parametri 
	        // Usiamo l'operatore IN(?) che funziona come una specie di array
	        String query = "SELECT * FROM prodotti WHERE id IN (";
	
	        // Per ottenere gli ID da mandare a IN(), ricaviamo tutte le chiavi della mappaQuantità 
	        Set<Integer> idProdotti = mappaQuantita.keySet();
	        
	        // Trasformiamo il set ottenuto in un array di oggetti per poterlo inviare al DatabaseManager
	        Object[] idProdottiArray = idProdotti.toArray(new Integer[0]);
	        
	        int count = 0;
	        
	        // Continuiamo a compilare la query aggiungendo iterativamente i segnaposti ?, seguiti da virgola
	        while (count < idProdottiArray.length-1) {
	        	
		        	query += "?, ";
		        	count++;
	        }
	        query += "?)"; // ultimo segnaposto
		    
	        // Inoltriamo la query e ricaviamo le liste
		    ArrayList<Map<String, Object>> mappaProdottiOrdinati = new ArrayList<>();
		    mappaProdottiOrdinati =	DatabaseManager.eseguiQuery(query, idProdottiArray);
		    ArrayList<Prodotto> listaProdottiOrdinati = Utility.creaListaDaMap(mappaProdottiOrdinati);

          /* Ora che abbiamo una lista dei prodotti ordinati possiamo verificare le disponibilità.
           * 
           * Per procedere settiamo una variabile booleana e poi scorriamo la lista prodotti,
           * confrontando la quantità del prodotto in magazzino con quella presente nella mappa.
          */
	        boolean disponibilità = true;
	        double totaleOrdine = 0;
	       
	        for (int i = 0; i < listaProdottiOrdinati.size(); i++) {
	            // Recupera la quantità ordinata dai parametri del form, usando l'id come riferimento
	        	int idProdotto = listaProdottiOrdinati.get(i).getID();
	        	Integer quantitaRichiesta = mappaQuantita.get(idProdotto);

	        	int quantitaDisponibile = listaProdottiOrdinati.get(i).getQuantita();
     	
	        	// Se almeno uno dei prodotti non ha quantità sufficiente, interrompe il ciclo
	        	// e setta la variabile a false, bloccando tutto l'ordine
	        	if (quantitaRichiesta > quantitaDisponibile) {
	                disponibilità = false;
	                break;
	                
	        	} else {
	        		// Sommiamo al totale ogni prezzo, moltiplicato per la quantità ordinata 
	        	    totaleOrdine += listaProdottiOrdinati.get(i).getPrezzo() * quantitaRichiesta;
	        	    
	        	    // Settiamo la quantità ordinata nella lista, sostituendo la quantità originale
	        	    listaProdottiOrdinati.get(i).setQuantita(quantitaRichiesta);
	        	} 
	        }

	        
	        // Ora abbiamo i dati che ci servono e possiamo inoltrare i dati alla pagina di conferma (o rifiuto) dell'ordine
	        if (disponibilità) {
	        	
	        	session.setAttribute("carrello", listaProdottiOrdinati);
	        	session.setAttribute("totaleOrdine", totaleOrdine);
	        	request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/verificaOrdine.jsp").forward(request, response);
	        	
			} else {
				request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/erroreOrdine.jsp").forward(request, response);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}