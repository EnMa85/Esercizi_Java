package app.gestore;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import app.database.DatabaseManager;
import app.models.Libro;

/**
 * Servlet implementation class InserisciServlet
 */
@WebServlet("/SubmitFormServlet")
public class InserisciServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InserisciServlet() {
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

		// Inizializziamo un prodotto
		Libro libro = new Libro();

		// Recuperiamo i dati dal form ed effettuiamo il cast per i numeri
		String titolo = request.getParameter("titolo");
		String autore = request.getParameter("autore");
		String annoString = request.getParameter("anno");
		int anno =  Integer.parseInt(annoString);
		String stringPeriodo = request.getParameter("periodo");
		int periodo = Integer.parseInt(stringPeriodo);

		// Chiamiamo la quuery per l'inserimento
		String query = "INSERT INTO libri (titolo, autore, anno, periodo_prestito) VALUES (?, ?, ?, ?)";
		DatabaseManager.eseguiQuery(query, titolo, autore, anno, periodo);
		
		// Settiamo gli attributi al libro per stamparlo nella pagina di conferma
		libro.setTitolo(titolo);
		libro.setAutore(autore);
		libro.setAnno(anno);
		libro.setPeriodoPrestito(periodo);
		
		// Salviamo il libro ed il tipo di operazione nella request, per passarli alla jsp
		request.setAttribute("libro", libro);
		request.setAttribute("operazioneEffettuata", "inserimento");
		request.getRequestDispatcher("/WEB-INF/jsp/areaGestore/confermaOperazione.jsp").forward(request, response);
	}
}
