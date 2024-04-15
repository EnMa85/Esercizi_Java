<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="app.models.Prodotto"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina ordini</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>

	<div class="title">
		<h4>Sei nella tua area riservata</h4>
	</div>

	<div id="prod">
		<p>
			Seleziona i prodotti che vuoi ordinare o consulta il tuo <a
				href="StoricoOrdiniServlet">storico ordini</a>
		</p>

		<%
			// Recuperiamo la lista prodotti dalla request e li stampiamo in un elenco
			ArrayList<Prodotto> listaProdotti = (ArrayList)request.getAttribute("listaProdotti");
			
			if (listaProdotti == null){
				out.print("La lista è vuota");
			}
			
			else {
	%>
		<!--  Se l'array non è vuoto, stampiamo una tabella di prodotti e relative checkbox -->


		<div class="table">
			<caption>ELENCO PRODOTTI</caption>

			<form action='OrdinaServlet' method='post'>

				<table>
					<tr>
						<th>prodotto</th>
						<th>prezzo<br>€
						</th>
						<th>unità<br>disponibili
						</th>
						<th>quantità<br>ordine
						</th>
					</tr>

					<% for (Prodotto prodotto : listaProdotti) { %>

					<tr>
						<td><%= prodotto.getNome() %></td>
						<td><%= String.format("%.2f", prodotto.getPrezzo()) %></td>
						<td><%= prodotto.getQuantita() %></td>
						<td><input class="number" type="number"
							max="<%= prodotto.getQuantita() %>" min="1"
							name="quantita_<%= prodotto.getID() %>"></td>
					</tr>
					<% } %>

				</table>

				<input class="button" type="submit" value="Invia ordine">

			</form>
			<% } %>
		</div>

		<br> <br>
	</div>

	<div id="footer">
		<a href="LogoutServlet"><button class="button" id="back">Esci</button></a>
	</div>

</body>
</html>