<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "app.models.Prodotto" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Conferma ordine</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<body>

		<div class="title">
			<h3>Ordine confermato</h3>
		</div>
		
		<div class="flexVer">
		
			<p>Consegneremo a breve la tua spesa...</p>
			
			<br><br>
			
			<p>Grazie per averci scelto!!!</p>
			
		</div>
		
		<div class="flexOr" id="img">
			<img width="25%" src="img/borsa.png">
			<img width="25%" src="img/cibo.png">
		</div>
			
		<div id="footer">
			<a href="UtenteServlet"><button class="button" id="back">Torna alla home</button></a>
			<a href="LogoutServlet"><button class="button" id="back">Esci</button></a>
		</div>	
			
	</body>
</html>