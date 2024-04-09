package app.utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.database.DatabaseManager;


/**
 * Servlet implementation class StoricoOrdiniServlet
 */
@WebServlet("/app/utente/StoricoOrdiniServlet")
public class StoricoOrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoricoOrdiniServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Accediamo alla sessione inizializzata nella SessioneServlet
		HttpSession session = request.getSession();
		// Verifichiamo che l'utente sia ancora loggato
	    if (session == null || session.getAttribute("utenteAttivo") == null) {
	        ((HttpServletResponse) response).sendRedirect("index.jsp");
	     }

		int utenteAttivo = (int) session.getAttribute("utenteAttivo");
		
		// Effettuiamo una query di tipo JOIN in cui incrociamo i dati di più tabelle tramite le foreign key
		String queryOrdini = "SELECT " +
						        "ordini.id, " +
						        "DATE_FORMAT(ordini.data, '%d-%m-%Y') AS data, " +
						        "ordini.totale, " +
						        "GROUP_CONCAT(" +
						            "CONCAT(" +
						                "dettaglio_ordine.nome_prodotto, ' - ', " +
						                "dettaglio_ordine.prezzo_prodotto, ' - ', " +
						                "dettaglio_ordine.quantita_ordinata" +
						            ")" +
						            " SEPARATOR ', '" +
						        ") AS dettaglio " +
						    "FROM " +
						        "ordini " +
						    "JOIN " +
						        "dettaglio_ordine ON dettaglio_ordine.id_ordine = ordini.id " +
						    "WHERE " +
						        "ordini.id_utente = ? " +
						    "GROUP BY " +
						        "ordini.id, ordini.data, ordini.totale " +
						    "ORDER BY " +
						        "ordini.id DESC";
		
		// Otterremo un arraylist di mappe che per ogni riga avrà le variabili con i dati dell'ordine
		// ed una stringa che contiene i dettagli prodotto concatenati
	 	ArrayList<Map<String, Object>> mappaOrdini = DatabaseManager.eseguiQuery(queryOrdini, utenteAttivo);
	 	
	 	// Dovremo leggere questa stringa e scomporla per ottenere un array di prodotti
	 	for (Map<String, Object> ordine : mappaOrdini) {

	 	    String dettaglioOrdine = ordine.get("dettaglio").toString();
	 	    // Splittiamo la stringa del dettaglio in più sottostringhe
	 	    String[] elencoProdotti = dettaglioOrdine.split(", ");
	 	    
	 	   // Creiamo una nuova lista per contenere i dettagli dei prodotti per questo ordine
	 	    ArrayList<Map<String, String>> dettagliProdotti = new ArrayList<>();

	 	    // Dividiamo ogni stringa per ottenere i campi della tabella
	 	    for (String datiProdotto : elencoProdotti) {
	 	         String[] partiProdotto = datiProdotto.split(" - ");
	 	         String nomeProdotto = partiProdotto[0];
	 	         String prezzoProdotto = partiProdotto[1];
	 	         String quantitaOrdinata = partiProdotto[2];
	 	        
	 	        // Inseriamo i campi ottenuti in una nuova mappa
	 	        Map<String, String> dettaglioProdotto = new HashMap<>();
	 	        dettaglioProdotto.put("nome_prodotto", nomeProdotto);
	 	        dettaglioProdotto.put("prezzo_prodotto", prezzoProdotto);
	 	        dettaglioProdotto.put("quantita_ordinata", quantitaOrdinata);

	 	        // Aggiungiamo questa mappa alla lista di dettagli prodotti per questo ordine
	 	        dettagliProdotti.add(dettaglioProdotto);
	 	    }

	 	    // Sostituiamo la stringa del dettaglio con la lista dei dettagli prodotti nella mappa dell'ordine
	 	    ordine.put("dettaglio", dettagliProdotti);
	 	}
	 	
	 	request.setAttribute("mappaOrdini", mappaOrdini);
	 	request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/storicoOrdini.jsp").forward(request, response);
}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
