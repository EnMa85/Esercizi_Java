package app.utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import app.database.DatabaseManager;
import app.models.Prodotto;
import app.models.Utility;

/**
 * Servlet implementation class UtenteServlet
 */
@WebServlet("/app/utente/UtenteServlet")
public class UtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UtenteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	// L'utente arriva qui dal link href inserito nei bottoni "Torna alla home"
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			caricaHome(request, response);
			
		} catch (ServletException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Accediamo alla sessione inizializzata nella SessioneServlet
		HttpSession session = request.getSession();

		 try {		 
			// Rileviamo le credenziali inserite
	        String user = request.getParameter("user");
	        String pw = request.getParameter("pw"); // password cripatata da js
	
	        // Se non sono stati compilati i campi, inoltra alla pagina di accesso invalido
	        if (user == null || pw == null) {
	        	request.getRequestDispatcher("/WEB-INF/jsp/accessoInvalido.jsp").forward(request, response);
    	    }
	        
	        // Inizializziamo le variabili che useremo
	        ArrayList<Map<String, Object>> listaUtenteMap = new ArrayList<>();
	        String query = "";
	               
            // Verifichiamo se l'utente ha premuto il tasto "Accedi" o "Registrati"
            String azione = request.getParameter("azione");
              	     
    	    /* Se ha premuto accedi inoltriamo la query al DB che salverà su una lista 
    	     * l'utente che corrisponde a quelle credenziali. Anche se ogni utente è univoco,
    	     * il DB restituisce sempre liste e scorre tutta la tabella anche se ha già trovato l'utente.
    	     * Per evitare questo si può usare la clausola LIMIT (in questo caso LIMIT 1) che blocca la
    	     * ricerca al primo risultato trovato.
    	     * Attenzione però, restituirà comunque una lista e per accedere all'elemento possiamo usare
    	     * direttamente l'indice zero, dato che sappiamo che sarà l'unico.
    	     */
	    	if (azione.equals("Accedi")) {

		        int utenteAttivo;
	    	    boolean accesso = false;
	    	    
    	    	query = "SELECT * FROM utenti WHERE username = ? AND password = ? LIMIT 1";
    	    	listaUtenteMap = DatabaseManager.eseguiQuery(query, user, pw);
    	    	accesso = Utility.checkLogin(listaUtenteMap, user, pw);

    	    	// Se l'accesso è avvenuto
    	    	if (accesso) {
    
    	    	    // recuperiamo l'utente all'indice 0
            	    utenteAttivo = (int) listaUtenteMap.get(0).get("id");
            	    
            	    // salviamo l'id utente nella sessione per poterlo recuperare in tutta l'app
            	    session.setAttribute("utenteAttivo", utenteAttivo);
            	    
    	    	    // carichiamo la sua home
            	    caricaHome(request, response);

    	      	// Gestisce il caso di credenziali invalide e rimanda alla relativa pagina
        	    } else {
                	request.getRequestDispatcher("/WEB-INF/jsp/accessoInvalido.jsp").forward(request, response);
                }
    	    	
    	    // Se l'utente ha premuto Registrati, verifichiamo che l'utente non esista già
	    	} else if (azione.equals("Registrati")) {
	    		
    	      	query = "SELECT * FROM utenti WHERE username = ?";
    	      	listaUtenteMap  = DatabaseManager.eseguiQuery(query, user);
        	    
        	    // e se non esiste, lo aggiungiamo alla tabella utenti del DB e rimandiamo alla pagina di conferma registrazione
    	    	if (listaUtenteMap.isEmpty()) {
	    	    	query = "INSERT INTO utenti (username, password) VALUES (?, ?)";
	    	    	DatabaseManager.eseguiQuery(query, user, pw);
	    	    	request.getRequestDispatcher("/WEB-INF/jsp/confermaRegistrazione.jsp").forward(request, response);  
	    	    	
	    	    // Se invece esiste, segnaliamo all'utente che è già registrato.	
    	    	} else {
    	    		request.getRequestDispatcher("/WEB-INF/jsp/utenteGiaRegistrato.jsp").forward(request, response);  		
    	    	} 
	    	}
      		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// Questo metodo viene chiamato sia dal doPost che dal doGet e carica la home se l'utente è loggato
	private void caricaHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
	
		// Accediamo alla sessione inizializzata nella SessioneServlet
		HttpSession session = request.getSession();
		// Verifichiamo che l'utente sia ancora loggato
	    if (session == null || session.getAttribute("utenteAttivo") == null) {
	        ((HttpServletResponse) response).sendRedirect("index.jsp");
	        
	    } else {
		    // Se è ancora loggato, proseguiamo con il caricamento
		        
		    // recuperiamo la lista prodotti aggiornata e la aggiungiamo alla request
	        ArrayList<Prodotto> listaProdotti = Utility.aggiornaListaProdotti();
	        request.setAttribute("listaProdotti", listaProdotti);
	
		    // inviamo la lista prodotti alla jsp
	        request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/homeUtente.jsp").forward(request, response);
	    }
	}
}
