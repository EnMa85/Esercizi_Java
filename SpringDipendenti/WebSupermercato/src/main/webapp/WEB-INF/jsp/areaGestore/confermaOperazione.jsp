<%@page import="java.text.Format"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "app.models.Prodotto" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Esito operazione</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<body>

		<div class="title">
			<h3>Sezione gestore - Esito operazione</h3>
		</div>
		
		<div class="table">
	<% 
	   // Legge il tipo di operazione effettuata e stampa la conferma
	   String operazione = (String)request.getAttribute("operazioneEffettuata");
	  
	   if (operazione != null) {
	        out.println("<p><br>Operazione effettuata con successo.<br><br></p>");

	        // Definisce una stampa diversa a seconda dell'operazione
	        switch (operazione){
	        
		        case "inserimento":      	
	%> 			
		 			<!--  Recuperiamo il prodotto inserito e lo stampiamo -->
					<p><br><br>E' stato inserito il seguente prodotto:<br><br></p>
					
					<p>
						${ prodotto.getNome() } - 
						Prezzo €: ${ String.format("%.2f", prodotto.getPrezzo()) } -
						Quantita': ${ prodotto.getQuantita() }
					</p>
	<%      	
		        	break;
	 			
	
		        case "eliminazione":
	%>
		        	<c:if test="${listaModifiche != null}">
       
		        		<!--  Stampiamo un elenco non ordinato dei prodotti eliminati -->
		        		<p>Sono stati eliminati i seguenti prodotti:</p>
						<ol>
							<c:forEach var="prodotto" items="${ listaModifiche }">
	                    		<li>
									${ prodotto.getNome() } - 
									Prezzo €: ${ String.format("%.2f", prodotto.getPrezzo()) } -
									Quantita': ${ prodotto.getQuantita() }
	                    		</li>
	               			</c:forEach>           
						</ol>
					</c:if>
	<%	        	
		        	break;
		        	
		        	
		        case "variazionePrezzo":
	%>       
					<c:if test="${listaModifiche != null}">
		        		<!--  Stampiamo un elenco non ordinato dei prodotti modificati -->
		        		<p>E' stato aggiornato il prezzo dei seguenti prodotti:</p>
						<ol>
						    <c:forEach var="prodotto" items="${ listaModifiche }">
	                    		<li>
      								${ prodotto.getNome() } - 
									Prezzo €: ${ String.format("%.2f", prodotto.getPrezzo()) } -
									Quantita': ${ prodotto.getQuantita() }
	                    		</li>
	               			</c:forEach>                
						</ol>
					</c:if>
	<%
		        	break;
		        	
		        	
		        case "variazioneQuantita":        	
	%>       
					<c:if test="${listaModifiche != null}">
		        		<!--  Stampiamo un elenco non ordinato dei prodotti modificati -->
		        		<p>E' stata aggiornata la quantita' dei seguenti prodotti:</p>
						<ol>
						    <c:forEach var="prodotto" items="${ listaModifiche }">
	                    		<li>
      								${ prodotto.getNome() } - 
									Prezzo €: ${ String.format("%.2f", prodotto.getPrezzo()) } -
									Quantita': ${ prodotto.getQuantita() }
	                    		</li>
	               			</c:forEach>                  
						</ol>
					</c:if>
	<%
		        	break;
		        	
		        	
		        	default:
	%>  
		        		<p>Tipo di operazione non riconosciuta.</p>
	<% 
	        }
		} 
	%>
	   </div> 		
	   
		<div id="footer">
			<a href="GestoreServlet"><button class="button" id="back">Torna indietro</button></a>
			<a href="LogoutServlet"><button class="button" id="back">Esci</button></a>
		</div>	
	    		
	</body>
</html>