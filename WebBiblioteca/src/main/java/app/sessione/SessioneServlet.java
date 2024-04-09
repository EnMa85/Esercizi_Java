package app.sessione;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class SessioneServlet
 */
public class SessioneServlet implements Filter {

    public void init(FilterConfig config) throws ServletException {
        // Inizializzazione del filtro
    }

    // Creiamo un'istanza della sessione e, tramite un filtro impostato nel file xml, verificheremo in tutte le pagine che l'utente sia loggato
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // Se sei nell'area utente e l'utente non è loggato, reindirizza ad index
        if (path.startsWith("/areaUtente") && (session == null || session.getAttribute("utenteAttivo") == null)) {
            response.sendRedirect(request.getContextPath() + "/index");
            
        // Se sei nell'area gestore e il gestore non è loggato, reindirizza ad index
        } else if (path.startsWith("/areaGestore") && (session == null || session.getAttribute("adminAttivo") == null)) {
            response.sendRedirect(request.getContextPath() + "/index");
            
        // Se non sei in nessuna delle due aree, prosegui
        } else {
            chain.doFilter(req, res);
        }
    }
}           