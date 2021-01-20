<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/profile/edit/save" var="action_url"/>
<div class="card mb-5">
    <form:form name="modulo" class="form-signin" action="${action_url}" method="POST" modelAttribute="userToEdit" enctype="multipart/form-data" onsubmit="return false">
       	<div class="card-header">
       		<h1 class="h3 mb-3 font-weight-normal">Modifica i tuoi Dati</h1>
       	</div>
        
        <div class="card-body">
        	<p class="text-danger">* Campo obbligatorio.</p>
	        <div class="form-group">
	        	<label>Nome Utente</label>
	        	<input type="text" name="username" value="${userToEdit.username}" readonly="readonly" class="form-control mt-2" />
	        </div>
	       
	        <input type="hidden" name="password" value="${userToEdit.password}">
	
			<div class="form-group">
				<label>Nome*</label>
		        <input type="text" name="firstName" class="form-control mt-2" value="${userToEdit.firstName}" onblur = "controlloNome()" autofocus/>
				<p id=name_err></p>
			</div>
	        
			<div class="form-group">
		        <label>Cognome*</label>
		        <input type="text" name="lastName"  class="form-control mt-2" value="${userToEdit.lastName}" onblur = "controlloCognome()"/>
				<p id=lastname_err></p>
			</div>
			
			<div class="form-group">
		        <label>Immagine Profilo</label>
		        <input type="file" name="image" class="form-control mt-2"/>
		        <small><b>NOTA</b>: Se non viene caricata alcuna immagine del profilo rimarrà quella attualmente presente nel server. <b>MAX 1MB</b></small>
			</div>	
		
			<div class="d-flex justify-content-center">
				<input class="btn btn-primary col-3" type="submit" value="Applica" onclick="controlloForm()"/>
			</div>
	        <form:hidden path="imageProfile"/>
	        <form:hidden path="disabled"/>
	        <form:hidden path="admin"/>
	        <form:hidden path="posts"/>
        </div>
    </form:form>
</div>

<script type="text/javascript">


	 
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
			
			
			
			var nome = document.modulo.firstName.value;
			var cognome = document.modulo.lastName.value;
			var pattern_names = /^([a-zA-Z\xE0\xE8\xE9\xF9\xF2\xEC\x27]\s?)+$/; //no numeri
			
			if ( (!pattern_names.test(nome)) || (!pattern_names.test(cognome)) ) {
				//document.getElementById("invia").setAttribute("disabled",true);
				//document.modulo.invia.disabled.value == true;
				alert("Dati inseriti non validi");
				
			}
			else document.modulo.submit();
		}
</script>