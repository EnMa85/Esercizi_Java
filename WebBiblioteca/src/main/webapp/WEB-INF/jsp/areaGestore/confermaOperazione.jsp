<%@page import="java.text.Format"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.Map" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "app.models.Libro" %>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Esito operazione</title>
		<link rel="stylesheet" type="text/css" href="css/style.css">
	</head>
	
	<body>

		<header class="flexOr">
			<a href="GestoreServlet">La tua home</a>
			<h2>Area gestore</h2>
			<a href="LogoutServlet">Logout</a>
		</header>
		
		<div class="table">
		
		<c:set var="azione" value="${operazioneEffettuata}" />
		
		<!-- Se il campo azione inviato dalla request non è vuoto -->
		<c:if test="${not empty azione}">
		
		    <p>Operazione effettuata con successo.</p>
			
			<!--  definiamo una serie di operazioni in base all'azione -->
    		<c:choose>
		
				<c:when test="${azione eq 'inserimento'}">
					
		 			<!--  Recuperiamo il libro inserito e lo stampiamo -->
					<p><br><br>E' stato inserito il seguente libro:<br><br></p>

					<p>
						Titolo:<br>${ libro.getTitolo() }<br><br>
						Autore:<br>${ libro.getAutore() }<br><br>
						Anno:<br>${ libro.getAnno() }<br><br>
						Periodo di prestito:<br>${ libro.getPeriodoPrestito() } giorni
					</p>

	 			</c:when>
	
	
	
		        <c:when test="${azione eq 'eliminazione'}">

		        	<c:if test="${mappaModifiche != null}">
       
		        		<!--  Stampiamo un elenco non ordinato dei libri eliminati -->
		        		<p>Sono stati eliminati i seguenti libri:</p>
		        		
						<ol>
							<c:forEach var="libro" items="${ mappaModifiche }">
	                    		<li>
									Titolo: ${ libro.get("titolo") } - 
									Autore: ${ libro.get("autore") } - 
									Anno: ${ libro.get("anno") } - 
	                    		</li>
	               			</c:forEach>           
						</ol>
					</c:if>
					
				</c:when>

		        	
		        	
		       	<c:when test="${azione eq 'impostaDisponibili'}"> 	
		        	
		        	<c:if test="${mappaModifiche != null}">
		        	
		        		<!--  Stampiamo un elenco non ordinato dei libri modificati -->
		        		<p>I seguenti libri sono stati impostati come disponibili:</p>
		        		
						<ol>
						    <c:forEach var="libro" items="${ mappaModifiche }">
	                    		<li>
									Titolo: ${ libro.get("titolo") } - 
									Autore: ${ libro.get("autore") } - 
									Anno: ${ libro.get("anno") }
	                    		</li>
	               			</c:forEach>                
						</ol>
					</c:if>
		        	
		        </c:when>
		        	
		  
		        <c:when test="${azione eq 'impostaNonDisponibili'}"> 	
		
					<c:if test="${mappaModifiche != null}">
					
		        		<!--  Stampiamo un elenco non ordinato dei libri modificati -->
		        		<p>I seguenti libri sono stati impostati come non disponibili:</p>
		        		
						<ol>
						    <c:forEach var="libro" items="${ mappaModifiche }">
	                    		<li>
									Titolo: ${ libro.get("titolo") } - 
									Autore: ${ libro.get("autore") } - 
									Anno: ${ libro.get("anno") }
	                    		</li>
	               			</c:forEach>                  
						</ol>
					</c:if>

		        </c:when>

		
				<c:otherwise>
		        	 <p><br><br>Operazione non riconosciuta.<br><br></p>
		   		</c:otherwise>
				
			</c:choose>
		</c:if>
		
	   </div> 		
	   
		<div id="footer">
			<a href="GestoreServlet" id="back"><-- Torna indietro</a>
		</div>	
	    		
	</body>
</html>