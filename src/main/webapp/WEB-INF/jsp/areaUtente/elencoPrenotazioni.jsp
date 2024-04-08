<%@ page language = "java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Map" %>
<%@ page import = "app.models.Libro" %>
     

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Elenco prenotazioni</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<body>

		<header class="flexOr">
				<a href="UtenteServlet">La tua home</a>
				<h2>Area riservata</h2>
				<a href="LogoutServlet">Logout</a>
		</header>
				
		<div class="table"> 
	
			<p>Di seguito l'elenco di tutti i libri da te presi in prestito.</p>
			
			<!--  con la notazione della libreria jstl stampiamo un if/else (qui choose-when-otherwise) 
				  per verificare se ci sono già ordini -->
			<c:choose>
			
				<c:when test = "${ elencoPrenotazioni.isEmpty() }">
				
					<br><br>
					<p>Non hai ancora preso in prestito nessun libro.</p>
					<br><br>
					
				</c:when>
				
		    	<c:otherwise>

		    		<!--  Se presenti prenotazioni creiamo una tabella in cui stampiamo i libri presi in prestito -->
					<table>
						<caption>Le tue prenotazioni</caption>
								
							<tr>
								<th>Titolo</th>
								<th>Autore</th>
								<th>Anno</th>
								<th>Data prestito</th>
								<th>Data restituzione prevista</th>
								<th>Data restituzione effettiva</th>
							</tr>
							
						
							<c:forEach var="libro" items="${ elencoPrenotazioni }">
							<tr>
								<td>${ libro.get("titolo") }</td>
								<td>${ libro.get("autore") }</td>
								<td>${ libro.get("anno") }</td>
								<td>${ libro.get("data_inizio") }</td>
								<td>${ libro.get("data_fine_massima") }</td>
								<td>${ libro.get("data_fine_effettiva") }</td>

						    </tr>
							</c:forEach>
							
				    </table>
				</c:otherwise>
				
			</c:choose>
			
    	</div>   
    
		<div id="footer">
			<a href="UtenteServlet" id="back"><-- Torna alla tua home</a>
		</div>	

	</body>
</html>
