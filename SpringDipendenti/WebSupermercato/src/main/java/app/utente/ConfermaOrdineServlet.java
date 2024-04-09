package app.utente;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import app.database.DatabaseManager;
import app.models.Prodotto;

/**
 * Servlet implementation class ConfermaOrdineServlet
 */
public class ConfermaOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfermaOrdineServlet() {
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
			// Recuperiamo i dati
			ArrayList <Prodotto> carrello = (ArrayList) session.getAttribute("carrello");
			double totaleOrdine = (double) session.getAttribute("totaleOrdine");
		    int utenteAttivo = (int) session.getAttribute("utenteAttivo");

		    
		/* La sequenza di operazioni da fare sul DB è questa:
		 * 
		 * 1. Salviamo l'id dell'utente ed il totale nella tabella 'ordini'.
		 * 
		 * 2. Chiediamo al db di restituirci l'id dell'ordine che ha appena generato (primary key autoincrement);
		 * 
		 * 3. Salviamo ogni prodotto ordinato dentro la tabella 'dettaglio_ordine'. 
		 * 	  Ogni prodotto avrà un record a sè stante ed un proprio id, ma l'id dell'ordine sarà uguale per tutti 
		 *    perchè è la chiave esterna (foreign key) che serve a collegare fra loro ordini e prodotti.
		 *    
		 * 4. Aggiorniamo la quantità residua nella tabella prodotti.
		 */
	
			// PRIMA QUERY  -  inseriamo dati generali ordine
		    // Otteniamo la data odierna
		    java.sql.Date dataOrdine = new java.sql.Date(System.currentTimeMillis());
			String queryDatiOrdine = "INSERT INTO ordini (data, id_utente, totale) VALUES (?, ?, ?)";
 	        DatabaseManager.eseguiQuery(queryDatiOrdine, dataOrdine, utenteAttivo, totaleOrdine);
 	        
			
			// SECONDA QUERY  -  recuperiamo l'id dell'ordine appena registrato
			String queryIdOrdine = "SELECT MAX(id) AS ultimo_ordine FROM ordini WHERE id_utente = ? LIMIT 1";
			ArrayList<Map<String, Object>> idOttenuto = DatabaseManager.eseguiQuery(queryIdOrdine, utenteAttivo);
		    Map<String, Object> record = idOttenuto.get(0);
		    // Recuperiamo l'id usando l'alias che gli abbiamo attribuito (AS, nella query) come chiave della mappa
		    int idUltimoOrdine = (int) record.get("ultimo_ordine");
		    
			
			// TERZA QUERY:  -  salviamo separatamente ogni prodotto ordinato, chiamandola tante volte quanti sono i prodotti
			String queryDettaglioProdotti = "INSERT INTO dettaglio_ordine (id_ordine, id_prodotto, nome_prodotto, prezzo_prodotto, quantita_ordinata) VALUES (?, ?, ?, ?, ?)";
			
			// QUARTA QUERY:  -  nello stesso ciclo della terza query, aggiorniamo anche la quantità su prodotti
			String queryAggiornaQuantita = "UPDATE prodotti SET quantita = quantita - ? WHERE id = ?";

			for (Prodotto prodotto : carrello) {
	      
	        	int quantitaOrdinata = prodotto.getQuantita();
	        	
 	        	// registra dettaglio ordine
	        	int idProdottoCorrente = prodotto.getID();
	        	String nomeProdottoCorrente = prodotto.getNome();
	        	double prezzoProdottoCorrente = prodotto.getPrezzo();
	        	DatabaseManager.eseguiQuery(queryDettaglioProdotti, idUltimoOrdine, idProdottoCorrente, nomeProdottoCorrente, prezzoProdottoCorrente, quantitaOrdinata);
	        	
	        	//aggiorna quantità residua nella tabella prodotti
	        	DatabaseManager.eseguiQuery(queryAggiornaQuantita, quantitaOrdinata, idProdottoCorrente);
 	        }

 	       request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/confermaOrdine.jsp").forward(request, response);
 	        
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
