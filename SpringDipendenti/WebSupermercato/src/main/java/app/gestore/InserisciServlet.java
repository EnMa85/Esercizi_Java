package app.gestore;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import app.database.DatabaseManager;
import app.models.Prodotto;

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
		Prodotto prod = new Prodotto();

		// Recuperiamo i dati dal form ed effettuiamo il cast per i numeri
		String nome = request.getParameter("nome");
		String prezzoString = request.getParameter("prezzo");
		double prezzo =  Double.parseDouble(prezzoString);
		String quantitaString = request.getParameter("quantita");
		int quantita =  Integer.parseInt(quantitaString);

		// Chiamiamo la quuery per l'inserimento
		String query = "INSERT INTO prodotti (nome, prezzo, quantita) VALUES (?, ?, ?)";
		DatabaseManager.eseguiQuery(query, nome, prezzo, quantita);
		
		// Settiamo gli attributi al prodotto
		prod.setNome(nome);
		prod.setPrezzo(prezzo);
		prod.setQuantita(quantita);
		
		// Salviamo il prodotto ed il tipo di operazione nella request, per passarli alla jsp
		request.setAttribute("prodotto", prod);
		request.setAttribute("operazioneEffettuata", "inserimento");
		request.getRequestDispatcher("/WEB-INF/jsp/areaGestore/confermaOperazione.jsp").forward(request, response);
	}
}
