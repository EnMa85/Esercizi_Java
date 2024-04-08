<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "app.models.Libro" %>

  
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>Home gestore</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
	
<body>

	<header class="flexOr">
			<a href="GestoreServlet">La tua home</a>
			<h2>Area gestore</h2>
			<a href="LogoutServlet">Logout</a>
	</header>


	<!-- Sezione per la modifica dei libri in elenco -->
	<div class="table">
		
		<h4>Modifica elenco</h4>	

		<form action='ModificaServlet' method='post'>
		
		  <c:choose>
		
			<c:when test = "${ mappaLibri.isEmpty() }">
			
				<br><br>
				<p>Nessun libro in elenco.</p>
				<br><br>
		
			</c:when>
					
			<c:otherwise>
			<!--  Stampiamo una tabella ed aggiungiamo ad ogni riga le checkbox -->
			<% int cont = 0; // Inizializziamo un conteggio che useremo per rendere univoci i nomi gli input %>
				
				<table>
				
					<tr>
						<th>ID</th>
						<th>Titolo</th>
						<th>Autore</th>
						<th>Anno</th>
						<th>Giorni prestito</th>
						<th>Stato</th>
						<th>Seleziona</th>
					</tr>
					
					<c:forEach var="libro" items="${ mappaLibri }">
					<tr>
						<td>${ libro.get("id") }</td>
						<td>${ libro.get("titolo") }</td>
						<td>${ libro.get("autore") }</td>
						<td>${ libro.get("anno") }</td>
						<td>${ libro.get("periodo_prestito") }</td>
						<td>${ libro.get("stato") ? "disponibile" : "non disponibile" }</td>
						
						<!--  aggiungiamo la checkbox assegnando un nome univoco e l'id-libro come valore -->
						<td><input type="checkbox" class="checkbox" name="checkbox_<%= cont %>" value="${ libro.get('id') }"></td>
					</tr>
					<% 	cont++; // incrementiamo il conteggio %> 
					</c:forEach>
					
				</table>
				
				<!--  inviamo il conteggio alla servlet per poterlo iterare -->
				<input type="hidden" name="cont" value="<%= cont %>">
				<input class="button" type="submit" name="azione" value="Elimina libri">
				<input class="button" type="submit" name="azione" value="Imposta disponibili">
				<input class="button" type="submit" name="azione" value="Imposta non disponibili">
		          
			</c:otherwise>
		  </c:choose>
		</form>

	</div>			
	<!-- Fine sezione modifica -->	


	<!-- Sezione inserimento prodotto -->	
	<div class="table">
		<h4>Inserisci nuovo libro</h4>
		<div>
		<br>
			<form action='InserisciServlet' method='post'>
	       		<div class="flexOr">
	       			<div>
			        	<label for="titolo">Titolo</label>
				        <input type="text" id="titolo" name="titolo">
					</div>
					<div>
						<label for="autore">Autore</label>
						<input type="text" id="autore" name="autore">
					</div>
					<div>
						<label for="anno">Anno</label>
						<input type="number" id="anno" name="anno" min="1500" max="">
					</div>
					<div>
						<label for="periodo">Giorni prestito</label>
						<input type="number" id="periodo" name="periodo" min="7" max="180">
					</div>
	    	
		        	<input class="button" type="submit" name="azione" value="Inserisci libro">
		        </div>
		    </form>
		</div>
	</div>
	<!-- Fine sezione inserimento -->	
	
	
	<script>
		// Otteniamo l'elemento input con l'id "anno"
		var inputAnno = document.getElementById("anno");
		
		// Otteniamo l'anno corrente
		var annoCorrente = new Date().getFullYear();
		
		// Impostiamo il valore massimo dell'input all'anno corrente
		inputAnno.setAttribute("max", annoCorrente);
	</script>

</body>
</html>