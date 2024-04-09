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
		<title>La tua home</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<body>

		<header class="flexOr">
				<a href="UtenteServlet">La tua home</a>
				<h2>Area riservata</h2>
				<a href="LogoutServlet">Logout</a>
		</header>

		<div class="table">
		
			<p>Seleziona il libro che desideri prenotare o consulta <a href="PrenotazioniServlet">le tue prenotazioni</a></p>

			<!--  Recuperiamo l'elenco dei libri dalla request e verifichiamo che sia popolato -->
			<c:choose>
		
				<c:when test = "${ mappaLibri.isEmpty() }">
				
					<br><br>
					<p>Nessun libro disponibile in questo momento. Si prega di riprovare.</p>
					<br><br>
		
				</c:when>
			
				<c:otherwise>
				<!--  Se non è vuoto, stampiamo i libri in formato tabella con checkbox in ogni riga -->
					<form action='PrenotaServlet' method='post'>
						
						<table>
							<caption>Elenco libri disponibili</caption>
							
							<tr>
								<th>Titolo</th>
								<th>Autore</th>
								<th>Anno</th>
								<th>Periodo di prestito</th>
								<th>Seleziona</th>
							</tr>
							
							<c:forEach var="libro" items="${ mappaLibri }">
							<tr>
								<td>${ libro.get("titolo") }</td>
								<td>${ libro.get("autore") }</td>
								<td>${ libro.get("anno") }</td>
								<td>${ libro.get("periodo_prestito") } giorni</td>
								
								<!--  aggiungiamo una radiobox assegnando l'id-libro come valore -->
								<td><input type="radio" class="check" name="radiobox" value="${ libro.get('id') }"></td>
							</tr>
							</c:forEach>
						</table>
						<input class="button" type="submit" value="Prenota">
						
	
					</form>
				</c:otherwise>
				
			</c:choose>
		</div>

	</body>
</html>