<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Prenotazione non valida</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<body>
	
		<header class="flexOr">
			<a href="UtenteServlet">La tua home</a>
			<h2>Area riservata</h2>
			<a href="LogoutServlet">Logout</a>
		</header>
	
		<br><br><br><br>
		
		<p>
			C'è stato un errore nell'inoltro della prenotazione o il libro potrebbe non essere più disponibile.
			<br><br><br> 
			Si prega di riprovare.
		</p>	
				
		<div id="footer">
			<a href="UtenteServlet" id="back"><-- Torna indietro</a>
		</div>	
		
	</body>
</html>