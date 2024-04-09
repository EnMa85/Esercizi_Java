<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "app.models.Libro" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Conferma prenotazione</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<body>

		<header class="flexOr">
			<a href="UtenteServlet">La tua home</a>
			<h2>Area riservata</h2>
			<a href="LogoutServlet">Logout</a>
		</header>
		
		<br><br><br><br>
		
		<div class="flexVer">
			
			<h3>Prenotazione confermata</h3>
		
			<br><br>
		
			<p>
				Ti ringraziamo per la tua prenotazione.
				<br><br>
				Ricorda che il libro dovrà essere riconsegnato entro ${ libroRichiesto.getPeriodoPrestito() } giorni.</p>
			</p>
			
			<br><br>
			
			<p>Ti auguriamo buona lettura!</p>
			
		</div>
			
		<div id="footer">
			<a href="UtenteServlet" id="back"><-- Torna alla tua home</a>
		</div>	
			
	</body>
</html>