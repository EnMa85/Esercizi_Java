<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "app.models.Prodotto" %>
 
<%
    if (session == null || session.getAttribute("utenteAttivo") == null) {
       // Se l'utente non è loggato, rimanda alla pagina iniziale
       ((HttpServletResponse) response).sendRedirect("index.jsp");
    }
%>      
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Verifica ordine</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<body>

		<div class="title">
			<h3>Riepilogo ordine</h3>
		</div>
		
		<p>Verifica i dati prima di confermare l'ordine.</p>
	
	<%
		// Recuperiamo il carrello dalla sessione per stamparlo
		ArrayList<Prodotto> carrello = (ArrayList) session.getAttribute("carrello");
		
		if (carrello == null){
			out.print("La lista è vuota");
		}
		
		else {
			// Se la lista non è vuota, recupera il totale dalla sessione
			double totaleOrdine = (double) session.getAttribute("totaleOrdine");
	%>			
					
			<div class="table">
			
				<p>Lista prodotti ordinati:</p>
				<br>
			
				<form action='ConfermaOrdineServlet' method='post'>
				
					<ol>
						<% for (Prodotto prodotto : carrello) { %>
	                    	<li>
			                    <%= prodotto.getNome() %>
			                    - prezzo unitario € <%= String.format("%.2f", prodotto.getPrezzo()) %>
			                    - quantità' ordinata: <%= prodotto.getQuantita() %>
			                    <br><br>
	                    	</li>
	               		<% } %>             
					</ol>
					
					<p><b>Totale ordine: € <%= String.format("%.2f", totaleOrdine) %></b></p> 
					<br>
					<input class="button" type="submit" value="Conferma ordine">
					
				</form>
			</div>
			
		<% } %>   
		
		<div id="footer">
			<a href="javascript:window.history.back()"><button class="button" id="back">Torna indietro</button></a>
		</div>	
	
	</body>
</html>