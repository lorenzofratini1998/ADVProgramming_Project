<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/sign_up/save" var="action_url"/>

<c:if test="${not empty message}">
    <div style="color: red; font-weight: bold; margin: 30px 0;">${message}</div>
</c:if>

<div class="col">
    <form:form class="form-signin" action="${action_url}" method="POST" modelAttribute="newUser" enctype="multipart/form-data">
        <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72"
             height="72">
        <h1 class="h3 mb-3 font-weight-normal">Inserisci i tuoi dati</h1>

        <label>Nome Utente*</label>
        <input type="text" name="username" class="form-control mt-2" placeholder="Username" required autofocus><br>
     

        <label>Password*</label>
        <input type="password" name="password" class="form-control mt-2" placeholder="Password" required><br>
        
        
        <label>Nome*</label>
        <input type="text" name="firstName" class="form-control mt-2" placeholder="Nome" required/><br>

        <label>Cognome*</label>
        <input type="text" name="lastName"  class="form-control mt-2" placeholder="Cognome" required/><br>

        <label>Immagine Profilo</label>
        <input type="file" name="image" class="form-control mt-2"/><br>
        <p>* Campo obbligatorio.</p>
        
        
        <input type="submit" class="btn btn-lg btn-primary btn-block" value="Registrati!"><br><br>
    </form:form>
</div>