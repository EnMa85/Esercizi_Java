package app.gestore;

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
 * Servlet implementation class GestoreServlet
 */
@WebServlet("/app/gestore/GestoreServlet")
public class GestoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestoreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // l'utente arriva qui tramite href diretto e non tramite form con method post
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
    // Gestiamo qui l'accesso e la stampa della lista nella pagina gestore
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        try {
    		//Recupera credenziali dal form admin
            String user = request.getParameter("admin");
            String pw = request.getParameter("pwAdmin");
        	
	        // Se non sono stati compilati i campi, inoltra alla pagina di accesso invalido
	        if (user == null || pw == null) {
	        	request.getRequestDispatcher("/WEB-INF/jsp/accessoInvalido.jsp").forward(request, response);
    	    } else {
	        
		        // Altrimenti, procediamo con la verifica
		        ArrayList<Map<String, Object>> listaAdminMap = new ArrayList<>();
	    	    boolean accesso = false;
		        int adminAttivo;
	            String query = "";
	            
	        	// Chiediamo al DB le credenziali che corrispondono e, dato che sappiamo che sono univoche, 
	            // limitiamo la query al 1° risultato evitando che MySQL scorra inutilmente tutto l'elenco
	    	    query = "SELECT * FROM admins WHERE username = ? AND password = ? LIMIT 1";
	    	    listaAdminMap  = DatabaseManager.eseguiQuery(query, user, pw);
	    	    
	    	    // Inviamo le credenziali da confrontare al metodo checkLogin, che restituisce un booleano
	    	    accesso = Utility.checkLogin(listaAdminMap, user, pw);
	    	    
	    		// Se le credenziali corrispondono
		    	if (accesso) {
		    		
		    	    // recuperiamo l'id dell'admin all'indice 0 (perchè il db restituisce sempre una lista, anche per un risultato)
		    		adminAttivo = (int) listaAdminMap.get(0).get("id");
	        	    
		    		// Accediamo alla sessione inizializzata nella SessioneServlet
		    		HttpSession session = request.getSession();
		    		
	        	    // salviamo l'id utente nella sessione per poterlo recuperare in tutta l'app
	        	    session.setAttribute("adminAttivo", adminAttivo);
		    		
		    	    // carichiamo la sua home
	        	    caricaHome(request, response);
	        	    
		    	} else { 
	            	// Se non corrispondono, reindirizza alla pagina di errore    
	            	request.getRequestDispatcher("/WEB-INF/jsp/accessoInvalido.jsp").forward(request, response);
		    	}
    	    }
    	
    	// Gestisce gli eventuali errori derivanti dall'interazione col database (SQLException)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// Questo metodo viene chiamato sia dal doPost che dal doGet e carica la home se l'utente è loggato
	private void caricaHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		// Accediamo alla sessione inizializzata nella SessioneServlet
		HttpSession session = request.getSession();
		// Verifichiamo che l'admin sia ancora loggato
	    if (session == null || session.getAttribute("adminAttivo") == null) {
	        ((HttpServletResponse) response).sendRedirect("index.jsp");
	        
	     } else {
		    // Se è ancora loggato, proseguiamo con il caricamento
	    
		    // Chiediamo la lista dei libri ordinata per titolo
		    ArrayList<Map<String, Object>> mappaLibri = Utility.elencoCompletoLibri();
	        
		    // Setta l'array ottenuto nella request, per poterlo gestire nella jsp
		    request.setAttribute("mappaLibri", mappaLibri);   	    	
	        request.getRequestDispatcher("/WEB-INF/jsp/areaGestore/homeGestore.jsp").forward(request, response);
	     }
	}
}
