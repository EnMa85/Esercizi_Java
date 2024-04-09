<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Map" %>
<%@ page import = "app.models.Prodotto" %>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Storico ordini</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<body>

		<div class="title">
			<h3>I tuoi ordini</h3>
		</div>
				
		<div class="table"> 
	
			<p>Di seguito l'elenco di tutti gli ordini da te effettuati.</p>
			
			<!--  con la notazione della libreria jstl stampiamo un if/else (qui choose-when-otherwise) 
				  per verificare se ci sono già ordini -->
			<c:choose>
			
				<c:when test = "${ mappaOrdini.isEmpty() }">
				
					<br><br>
					<p>Non hai ancora effettuato nessun ordine.</p>
					<br><br>
					
				</c:when>
				
		    	<c:otherwise>
		    	
		    		
		    		<!--  Se presenti ordini scorriamo ogni riga della mappa e stampiamo i dati ordine-->
					<c:forEach var="ordine" items="${ mappaOrdini }">
						<div id="storico">

					        <p>Ordine ID: ${ ordine.id } &emsp; del &emsp; ${ ordine.data }</p>
							
							<!--  Per ogni riga dell'array prodotti stampiamo i dettagli relativi ad ogni prodotto
								  (l'array prodotti è implicitamente incluso in mappa ordini in quanto la query 
								  la query al db prevedeva una join con la tabella prodotti) 
							-->
							<table id="storico">
							
								<tr>
									<td>Articolo</td>
									<td>Prezzo cdu</td>
									<td>Quantita'</td>
								</tr>
	
							    <c:forEach var="dettaglio" items="${ ordine.dettaglio }" varStatus="loop">	
						        <tr>
				                    <td>${ loop.index + 1 }. ${ dettaglio.nome_prodotto }</td>
	       							<td><fmt:formatNumber value="${ dettaglio.prezzo_prodotto }" type="currency" currencyCode="EUR" /></td>
	                   		        <td>${ dettaglio.quantita_ordinata }</td> 
						        </tr> 
					
								</c:forEach>
								
							</table>
							
							<!--  chiudiamo il for dei prodotti e stampiamo il totale  -->
					        <p>Totale spesa: <fmt:formatNumber value="${ ordine.totale }" type="currency" currencyCode="EUR" /></p>

					    </div>
				    </c:forEach>
				    
				</c:otherwise>
				
			</c:choose>
			
    	</div>   
    
		<div id="footer">
			<a href="UtenteServlet"><button class="button" id="back">Torna alla home</button></a>
			<a href="LogoutServlet"><button class="button" id="back">Esci</button></a>
		</div>	

	</body>
</html>
