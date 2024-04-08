<!DOCTYPE html>
<html>
	<head>
	    <title>Benvenuti</title> 
	    <link rel="stylesheet" type="text/css" href="css/style.css">
	   	<style>
		    body {
		    	background-image: url("img/home6.jpg");
		    	background-size: cover;
		    	background-repeat: no-repeat;
		    }
	    </style>
	</head>
	    
	<body>
	
		<div class="title">
			<h2>Biblioteca Talentform</h2>
		</div>

					
		<div class="flexVer" id="index">
				
			<div class="flexOr" id="areaAccesso">
			    <button onclick="showUserForm()">Vai su<br>area cliente</button>
			    <button onclick="showAdminForm()">Vai su<br>area gestore</button>
			</div>
	
			<!--  Form per accedere all'area utente -->
			<form id="userForm" class="hidden" onsubmit="return verificaCF()" action="UtenteServlet" method="post">
		    
		    <h4>Accedi come cliente</h4>
		   
				<div class="flexOr">
					<div class="flexVer">
						<label for="cf">Codice fiscale</label>
						<input type="text" id="cf" name="cf" minlength="16" maxlength="16" required>
					</div>

					<div class="flexVer">
						<label for="pw">Password</label>
						<input type="password" id="pw" name="pw" required>
					</div>
				</div>
				<br>
				<div class="flexOr">
					<input class="button" type="submit" name="azione" value="Accedi">
					<input class="button" type="submit" name="azione" value="Registrati">
				</div>
		    </form>
			
			
	    	<!--  Form per accedere all'area gestore -->
		    <form id="adminForm" class="hidden" action="GestoreServlet" method="post">
		    
		    	<h4>A gestore</h4>
		    
				<div class="flexOr">
					<div class="flexVer">
						<label for="admin">Nome utente</label>
						<input type="text" id="admin" name="admin" required>
					</div>

					<div class="flexVer">
						<label for="pwAdmin">Password</label>
						<input type="password" id="pwAdmin" name="pwAdmin" required>
					</div>
		        </div>
		        <br>
				<div>
					<input class="button" type="submit" value="Accedi">
				</div>

		    </form>
    	</div>
    	
    	
	   	<!-- Script JavaScript per criptare la password prima di inviarla -->
	   	<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/crypto-js.min.js"></script>
		<script>
			function showUserForm() {
		        document.getElementById("userForm").classList.remove("hidden");
		        document.getElementById("adminForm").classList.add("hidden");
		    }
	
		    function showAdminForm() {
		        document.getElementById("adminForm").classList.remove("hidden");
		        document.getElementById("userForm").classList.add("hidden");
		    }
	    
			document.getElementById("userForm").addEventListener("submit", function(event) {
				// Ottiene il valore della password
			    var pw = document.getElementById("pw").value;
			    // Esegue l'hash della password
			    var hashedPw = CryptoJS.SHA256(pw).toString();
			    // Sovrascrive la password con il nuovo valore
			    document.getElementById("pw").value = hashedPw;
			    // Invia il modulo
			    this.submit();
		    });
		
			document.getElementById("adminForm").addEventListener("submit", function(event) {
			    var pw = document.getElementById("pwAdmin").value;
			    var hashedPw = CryptoJS.SHA256(pw).toString();
			    document.getElementById("pwAdmin").value = hashedPw;
			    this.submit();
		    });
			
			
			 function verificaCF() {
		            var cf = document.getElementById("cf").value.toUpperCase();
		            var regexCF = /^[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;

		            if (regexCF.test(cf)) {
		            	return true;
		            } else {
		            	return false;
		            }
		      }
		</script>
    
    </body>
</html>
