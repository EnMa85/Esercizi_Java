<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="app.models.Prodotto"%>


<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Elenco prodotti</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>

	<div class="title">
		<h3>Sezione gestore</h3>
	</div>


	<!-- Sezione inserimento prodotto -->
	<div class="table">
		<h4>INSERISCI PRODOTTI</h4>
		<div>
			<br>
			<form action='InserisciServlet' method='post'>
				<div class="flexOr">
					<div>
						<label for="nome">Nome:</label> <input type="text" id="nome"
							name="nome">
					</div>
					<div>
						<label for="prezzo">Prezzo:</label> <input type="number"
							id="prezzo" name="prezzo" step="0.01" min="0">
					</div>
					<div>
						<label for="quantita">Quantità:</label> <input type="number"
							id="quantita" name="quantita" min="0">
					</div>
				</div>
				<br> <br> <input class="button" type="submit"
					value="Inserisci prodotto">
			</form>
		</div>
	</div>
	<!-- Fine sezione inserimento -->

	<div class="table">
		<!-- Sezione per la modifica dei prodotti inseriti -->
		<h4>MODIFICA PRODOTTI</h4>

		<%
  		ArrayList<Prodotto> listaProdotti = (ArrayList<Prodotto>) request.getAttribute("listaProdotti");
		
		if (listaProdotti == null){
			out.print("La lista è vuota");
		} else {
	%>

		<form action='ModificaServlet' method='post'>

			<table>

				<tr>
					<th>id</th>
					<th>nome<br>prodotto
					</th>
					<th>prezzo €</th>
					<th>quantita'</th>
					<th>elimina</th>
					<th>varia<br>prezzo
					</th>
					<th>varia<br>quantita'
					</th>
				</tr>

				<%  
				int cont = 0; // Inizializziamo un conteggio per rendere univoci gli input
				for (Prodotto prodotto : listaProdotti) {
			%>

				<tr>
					<td><%= prodotto.getID() %></td>
					<td><%= prodotto.getNome() %></td>
					<td><%= String.format("%.2f", prodotto.getPrezzo()) %></td>
					<td><%= prodotto.getQuantita() %></td>
					<td><input type="checkbox" class="number"
						name="cancellaCheck_<%= cont %>"></td>
					<td><input type="number" name="newPrezzo_<%= cont %>"
						step="0.01" min="0"></td>
					<td><input type="number" name="newQuantita_<%= cont %>"
						min="0"></td>
				</tr>

				<!-- Per ogni riga inviamo l'id prodotto come campo hidden. 
                	 Non verrà visualizzato dall'utente ma arriverà alla Servlet -->
				<input type="hidden" name="idProdotto_<%= cont %>"
					value="<%= prodotto.getID() %>">

				<% 
             	cont++;
             	} 
             %>

			</table>

			<!--  inviamo il conteggio alla servlet per poterlo iterare -->
			<input type="hidden" name="cont" value="<%= cont %>"> <input
				class="button" type="submit" name="azione"
				value="Elimina selezionati"> <input class="button"
				type="submit" name="azione" value="Aggiorna prezzo"> <input
				class="button" type="submit" name="azione"
				value="Aggiorna quantita'">

		</form>

		<% } %>

	</div>
	<!-- Fine sezione modifica -->

	<br>

	<div id="footer">
		<a href="LogoutServlet"><button class="button" id="back">Esci</button></a>
	</div>

</body>
</html>