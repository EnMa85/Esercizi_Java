package app.utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

import app.database.DatabaseManager;
import app.models.Libro;


/**
 * Servlet implementation class PrenotaServlet
 */
public class PrenotaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrenotaServlet() {
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
	    
	    // Se l'utente è loggato, procediamo
		try {	       
			// Salva id utente nella sessione
			int idUtente = (int) session.getAttribute("utenteAttivo");
			
	   		// Recupera l'id del libro dalla radiobox
        	String idDaRadiobox = request.getParameter("radiobox");
        	int idLibro = Integer.parseInt(idDaRadiobox);
        	
        	// Chiediamo i dati aggiornati al db e creiamo un oggetto libro dal risultato della query
           	String queryLibroAggiornato = "SELECT * FROM libri WHERE id = ? LIMIT 1";
        	ArrayList<Map<String, Object>> mappaLibro = DatabaseManager.eseguiQuery(queryLibroAggiornato, idLibro);
        	
        	Libro libroRichiesto = new Libro();
        	
        	// Recuperiamo i parametri con le chiavi
        	String titolo = (String) mappaLibro.get(0).get("titolo");
        	String autore = (String) mappaLibro.get(0).get("autore");
        	int anno = (int) mappaLibro.get(0).get("anno");
        	int periodo = (int) mappaLibro.get(0).get("periodo_prestito");
        	boolean stato = (boolean) mappaLibro.get(0).get("stato");
        	
        	// Settiamo l'oggetto con i parametri ricavati
        	libroRichiesto.setId(idLibro);
        	libroRichiesto.setTitolo(titolo);
        	libroRichiesto.setAutore(autore);
        	libroRichiesto.setAnno(anno);
        	libroRichiesto.setPeriodoPrestito(periodo);
        	libroRichiesto.setStato(stato);
        	
        	// Se è ancora disponibile settiamo le date e ed eseguiamo la prenotazione
        	if (libroRichiesto.getStato().equals("disponibile")) {
        	        		
        		// Ricaviamo la data di oggi e la impostiamo come data di inizio
            	LocalDate dataInizio = LocalDate.now();
            	// Calcoliamo la data di fine aggiungendo i giorni del prestito alla data di inizio
            	LocalDate dataFine = dataInizio.plusDays(periodo);
            	
            	// Inserisci prenotazione
            	String queryPrenota = "INSERT INTO prenotazioni (id_libro, id_utente, data_inizio, data_fine_massima) VALUES (?, ?, ?, ?)";
               	DatabaseManager.eseguiQuery(queryPrenota, idLibro, idUtente, dataInizio, dataFine);
                // Aggiorna disponibilità libro
               	String queryVariaStato = "UPDATE libri SET stato = 0 WHERE id = ?";
               	DatabaseManager.eseguiQuery(queryVariaStato, idLibro);
        	
               	// Passa alla request i dati del libro prenotato
	        	request.setAttribute("libroRichiesto", libroRichiesto);
	        	request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/confermaPrenotazione.jsp").forward(request, response);
            
	        // Se nel frattempo il libro non è più disponibile, blocca la prenotazione e ritorna una pagina di errore
        	} else {
	        	request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/errorePrenotazione.jsp").forward(request, response);
        	}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}