<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/profile/edit/save" var="action_url"/>
<div class="col">
   
    <form:form name="modulo" class="form-signin" action="${action_url}" method="POST" modelAttribute="userToEdit" enctype="multipart/form-data" onsubmit="return false">
        <h1 class="h3 mb-3 font-weight-normal">Modifica i tuoi Dati</h1>

        <label>Nome Utente</label>
        <input type="text" name="username" value="${userToEdit.username}" readonly="readonly" class="form-control mt-2" /><br>
        
        <input type="hidden" name="password" value="${userToEdit.password}">

        <label>Nome*</label>
        <input type="text" name="firstName" class="form-control mt-2" value="${userToEdit.firstName}" onblur = "controlloNome()" autofocus/><br>
		<p id=name_err></p>
		
        <label>Cognome*</label>
        <input type="text" name="lastName"  class="form-control mt-2" value="${userToEdit.lastName}" onblur = "controlloCognome()"/><br>
		<p id=lastname_err></p>
		
        <label>Immagine Profilo</label>
        <input type="file" name="image" class="form-control mt-2"/><br>
        <p>NOTA: Se non viene caricata alcuna immagine del profilo rimarrà quella attualmente presente nel server.</p>
        <p>* Campo obbligatorio.</p>

        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Applica Modifiche!" onclick="controlloForm()"/><br><br>

        <form:hidden path="imageProfile"/>
        <form:hidden path="disabled"/>
        <form:hidden path="admin"/>
        <form:hidden path="posts"/>
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