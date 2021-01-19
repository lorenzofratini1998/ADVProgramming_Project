<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>



	


<c:url value="/sign_up/save" var="action_url"/>

<c:if test="${not empty message}">
    <div class="row">
        <div class="alert alert-danger mx-auto" role="alert">${message}
            <button type="button" class="close ml-3" data-dismiss="alert" aria-label="Close" style="font-size: 1.4rem;">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </div>
</c:if>
<div class="row justify-content-center">
	<div class="card col-6">
	    <form name="modulo" class="form-signin" action="${action_url}"method="POST" modelAttribute="newUser" enctype="multipart/form-data" onsubmit="return false" >
	       	<div class="card-header">
	        	<h1 class="h3 mb-3 font-weight-normal text-center"> <b>Inserisci i tuoi dati</b></h1>
	        </div>
	        <p class="text-danger">(* Campo obbligatorio)</p>
	
	        <label>Nome Utente*</label>
	        <input type="text" name="username" class="form-control mt-2" placeholder="Username"  required autofocus>
	     	<p id="err_user"></p>
	
	        <label>Password* <sup id="apice"></sup></label>
	        <input type="password" name="password" class="form-control mt-2" placeholder="6-15 caratteri, alfanumerici, simboli speciali (_ * – + ! ? , : ; .)"  onkeyup="passwordRobusta()" required>
	        <p id=nogood_pass></p>
	        
	        <label>Conferma Password*</label>
	        <input type="password" name="conferma_password" class="form-control mt-2" placeholder="Conferma Password" onkeyup = "verificaPasswordUguali()" required>
	        <p id=err_pass></p>
	        
	        <label>Nome*</label>
	        <input type="text" name="firstName" class="form-control mt-2" placeholder="Mario" onfocus = "verificaPasswordUguali()" onblur = "controlloNome()"  required/>
			<p id=name_err></p>
			
	        <label>Cognome*</label>
	        <input type="text" name="lastName"  class="form-control mt-2" placeholder="Rossi" onfocus = "verificaPasswordUguali()" onblur = "controlloCognome()"  required/>
			<p id=lastname_err></p>
			
	        <label>Immagine Profilo</label>
	        <input type="file" name="image" class="form-control mt-2"/><br>
	        
	        
	        
	        <input id="invia" type="submit" class="btn btn-lg btn-primary btn-block" value="Registrati!" onclick="controlloForm()"><br><br>
	    </form>
	</div>
</div>


<script type="text/javascript">

//Handlers per segnalare gli errori nel form (password inserite non uguali, password non conforme, Nome o Cognome con numero)



	function verificaPasswordUguali() {
		document.modulo.password.removeAttribute("style");
		document.modulo.conferma_password.removeAttribute("style");
		document.getElementById("err_pass").innerHTML = "";
		
		if (document.modulo.password.value != document.modulo.conferma_password.value) {
			
			document.modulo.password.style.backgroundColor = "#FFB6C1";
			document.modulo.password.style.borderColor = "red";
			document.modulo.conferma_password.style.backgroundColor = "#FFB6C1";
			document.modulo.conferma_password.style.borderColor = "red";
			var err_msg = document.getElementById("err_pass");
			err_msg.innerHTML = "Le password immesse non corrispondono!";
			err_msg.style.color = "red";
			
	}
		 
}	
	
	
	 
	 
	 function passwordRobusta() {
		 document.modulo.password.removeAttribute("style");
		 document.getElementById("apice").innerHTML = "";
		 
		 var pass = document.modulo.password.value;
		 var length = document.modulo.password.value.length;
		 
		 if (length < 6) {
			    document.modulo.password.style.backgroundColor = "#FFB6C1";
				document.modulo.password.style.borderColor = "red";
				var msg = document.getElementById("apice");
				msg.innerHTML = "Insufficiente!";
				msg.style.color = "red";
		 }
		 
		 if (length > 5 && !(/\d/.test(pass))) {
			document.modulo.password.style.backgroundColor = "#f0e68c";
			document.modulo.password.style.borderColor = "orange";
			var msg = document.getElementById("apice");
			msg.innerHTML = "Mediocre!";
			msg.style.color = "orange";
		 }
			
		 if (length > 7 && /\d/.test(pass)) {
			document.modulo.password.style.backgroundColor = "#8fbc8f";
			document.modulo.password.style.borderColor = "green";
			var msg = document.getElementById("apice");
			msg.innerHTML = "Buona!";
			msg.style.color = "green";
		 }
	 }
	 
	 function controlloNome() {
		 document.modulo.firstName.removeAttribute("style");
		 document.getElementById("name_err").innerHTML = "";
		 var pattern = /^([a-zA-Z\xE0\xE8\xE9\xF9\xF2\xEC\x27]\s?)+$/;
		 if (!pattern.test(document.modulo.firstName.value)) {
			 	document.modulo.firstName.style.backgroundColor = "#FFB6C1";
				document.modulo.firstName.style.borderColor = "red";
				var msg = document.getElementById("name_err");
				msg.innerHTML = "Nome non valido!";
				msg.style.color = "red";
				
		 }
		 

	 }
	 
	 function controlloCognome() {
		 document.modulo.lastName.removeAttribute("style");
		 document.getElementById("lastname_err").innerHTML = "";
		 var pattern = /^([a-zA-Z\xE0\xE8\xE9\xF9\xF2\xEC\x27]\s?)+$/;
		 if (!pattern.test(document.modulo.lastName.value)) {
			 	document.modulo.lastName.style.backgroundColor = "#FFB6C1";
				document.modulo.lastName.style.borderColor = "red";
				var msg = document.getElementById("lastname_err");
				msg.innerHTML = "Cognome non valido!";
				msg.style.color = "red";
				
		 }
		 
	 }
	
	
		
		
		//Se vengono superati i controlli abilita l'inoltro del form (di default disabilitato)
		function controlloForm() {
			
			
			var pass = document.modulo.password.value;
			var repeatpass = document.modulo.conferma_password.value;
			var nome = document.modulo.firstName.value;
			var cognome = document.modulo.lastName.value;
			var pattern_pass = /^[a-zA-Z0-9\_\*\-\+\!\?\,\:\;\.\xE0\xE8\xE9\xF9\xF2\xEC\x27]{6,15}/; //min 6, max 15, alfanumerici, simboli speciali (_ * – + ! ? , : ; .)
			var pattern_names = /^([a-zA-Z\xE0\xE8\xE9\xF9\xF2\xEC\x27]\s?)+$/; //no numeri
			
			if ((pass != repeatpass) || (!pattern_pass.test(pass)) || (!pattern_names.test(nome)) || (!pattern_names.test(cognome)) ) {
				//document.getElementById("invia").setAttribute("disabled",true);
				//document.modulo.invia.disabled.value == true;
				alert("Ricontrolla i dati inseriti!");
				
			}
			else document.modulo.submit();
		}
</script>