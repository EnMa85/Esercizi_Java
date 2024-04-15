<!DOCTYPE html>
<html>
<head>
<title>Benvenuti</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<style>
body {
	background-image: url("img/home.jpg");
	background-size: 85%;
	background-position: top;
	background-repeat: no-repeat;
}
</style>
</head>

<body>

	<div class="title">
		<h2>Benvenuti nel nostro supermercato!</h2>
	</div>


	<div class="flexOr" id="index">

		<!--  Form per accedere all'area utente -->
		<form id="userForm" action="UtenteServlet" method="post">

			<h4>Area cliente</h4>

			<div class="flexOr">
				<div class="flexVer">
					<label for="user">Nome utente</label> <input type="text" id="user"
						name="user" required>
				</div>

				<div class="flexVer">
					<label for="pw">Password</label> <input type="password" id="pw"
						name="pw" required>
				</div>
			</div>
			<br>
			<div class="flexOr">
				<input class="button" type="submit" name="azione" value="Accedi">
				<input class="button" type="submit" name="azione" value="Registrati">
			</div>
		</form>


		<!--  Form per accedere all'area gestore -->
		<form id="adminForm" action="GestoreServlet" method="post">

			<h4>Area gestore</h4>

			<div class="flexOr">
				<div class="flexVer">
					<label for="admin">Nome utente</label> <input type="text"
						id="admin" name="admin" required>
				</div>

				<div class="flexVer">
					<label for="pwAdmin">Password</label> <input type="password"
						id="pwAdmin" name="pwAdmin" required>
				</div>
			</div>
			<br>
			<div>
				<input class="button" type="submit" value="Accedi">
			</div>

		</form>
	</div>

	<a id="footer"
		href="https://it.freepik.com/vettori-gratuito/illustrazione-del-concetto
    						-di-acquisto-di-generi-alimentari_25182796.htm#fromView=search&page
    						=1&position=0&uuid=dee1206b-3c9c-4c76-9a10-4194757e146d">
		Immagine di storyset su Freepik </a>


	<!-- Script JavaScript per criptare la password prima di inviarla -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/crypto-js.min.js"></script>
	<script>
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
		</script>

</body>
</html>
