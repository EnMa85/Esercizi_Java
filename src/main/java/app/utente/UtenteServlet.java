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
		
		 try {		 
			// Rileviamo le credenziali inserite
	        String cf = request.getParameter("cf");
	        String pw = request.getParameter("pw"); // password cripatata da js
	
	        // Se non sono stati compilati i campi, inoltra alla pagina di accesso invalido
	        if (cf == null || pw == null) {
	        	request.getRequestDispatcher("/WEB-INF/jsp/accessoInvalido.jsp").forward(request, response);
    	    }
	               
            // Verifichiamo se l'utente ha premuto il tasto "Accedi" o "Registrati"
            String azione = request.getParameter("azione");
              	     
	    	if (azione.equals("Accedi")) {

		        accedi(cf, pw, request, response);
    	  
	    	} else if (azione.equals("Registrati")) {
	    		
	    		registrati(cf, pw, request, response);
	    	}
      		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	private void accedi(String cf, String pw, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		int utenteAttivo;
	    boolean accesso = false;
	    
    	String query = "SELECT * FROM utenti WHERE username = ? AND password = ? LIMIT 1";
    	ArrayList<Map<String, Object>> listaUtenteMap = DatabaseManager.eseguiQuery(query, cf, pw);
    	accesso = Utility.checkLogin(listaUtenteMap, cf, pw);

    	// Se l'accesso è avvenuto
    	if (accesso) {
    		boolean accountAttivo = (boolean) listaUtenteMap.get(0).get("stato_account");
    		
    		// Controlla se l'account è bloccato ed in caso rimanda ad una pagina di errore
    		if (!accountAttivo) {
    	    	request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/accountBloccato.jsp").forward(request, response);  
    		}
    		
    	    // Altrimenti recuperiamo l'utente all'indice 0 e lo salviamo nella sessione
    		HttpSession session = request.getSession();
    	    utenteAttivo = (int) listaUtenteMap.get(0).get("id");
    	    session.setAttribute("utenteAttivo", utenteAttivo);
    	    
    	    // Chiamiamo il metodo per caricare la home con i libri disponibili
    	    caricaHome(request, response);

      	// Gestisce il caso di credenziali invalide e rimanda alla relativa pagina
	    } else {
        	request.getRequestDispatcher("/WEB-INF/jsp/accessoInvalido.jsp").forward(request, response);
        }
	}
	
	
	private void registrati(String cf, String pw, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		// Verifichiamo che l'utente non esista già
		String query = "SELECT * FROM utenti WHERE username = ?";
		ArrayList<Map<String, Object>> listaUtenteMap = DatabaseManager.eseguiQuery(query, cf);
	    
	    // e se non esiste, lo aggiungiamo alla tabella utenti del DB e rimandiamo alla pagina di conferma registrazione
    	if (listaUtenteMap.isEmpty()) {
	    	query = "INSERT INTO utenti (username, password) VALUES (?, ?)";
	    	DatabaseManager.eseguiQuery(query, cf, pw);
	    	request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/confermaRegistrazione.jsp").forward(request, response);  
	    	
	    // Se invece esiste, segnaliamo all'utente che è già registrato.	
    	} else {
    		request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/utenteGiaRegistrato.jsp").forward(request, response);  		
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
	        ArrayList<Map<String, Object>> mappaLibri = Utility.elencoLibriDisponibili();
	        request.setAttribute("mappaLibri", mappaLibri);

		    // inviamo la lista prodotti alla jsp
	        request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/homeUtente.jsp").forward(request, response);
	    }
	}
}
