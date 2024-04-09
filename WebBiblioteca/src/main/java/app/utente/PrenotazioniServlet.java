package app.utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import app.database.DatabaseManager;


/**
 * Servlet implementation class PrenotazioniServlet
 */
@WebServlet("/app/utente/StoricoOrdiniServlet")
public class PrenotazioniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrenotazioniServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Accediamo alla sessione inizializzata nella SessioneServlet
		HttpSession session = request.getSession();

		int utenteAttivo = (int) session.getAttribute("utenteAttivo");
		
		// Effettuiamo una query di tipo JOIN in cui incrociamo i dati di più tabelle tramite le foreign key
		String queryPrenotazioni = "SELECT prenotazioni.id, libri.titolo, libri.autore, libri.anno, "
				                 + "DATE_FORMAT(prenotazioni.data_inizio, '%d-%m-%Y') AS data_inizio, "
				                 + "DATE_FORMAT(prenotazioni.data_fine_massima, '%d-%m-%Y') AS data_fine_massima, "
				                 + "IFNULL(DATE_FORMAT(prenotazioni.data_fine_effettiva, '%d-%m-%Y'), 'Non ancora restituito') AS data_fine_effettiva "
				                 + "FROM prenotazioni "
				                 + "JOIN libri ON libri.id = prenotazioni.id_libro "
				                 + "WHERE prenotazioni.id_utente = ? ORDER BY data_inizio";

	 	ArrayList<Map<String, Object>> elencoPrenotazioni = DatabaseManager.eseguiQuery(queryPrenotazioni, utenteAttivo);
	 	
	 	request.setAttribute("elencoPrenotazioni", elencoPrenotazioni);
	 	request.getRequestDispatcher("/WEB-INF/jsp/areaUtente/elencoPrenotazioni.jsp").forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
