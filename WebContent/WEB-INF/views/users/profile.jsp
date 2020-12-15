<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/profile/edit" var="url" />

<c:if test="${not empty message}">
    <div style="font-weight: bold; margin: 30px 0;"><h2>${message}</h2></div>
</c:if>

<div class = "col">
	
        <h1 class="h3 mb-3 font-weight-normal">Profilo utente di ${user.username}</h1>

        <label>Nome Utente</label>
        <input type="text" name="username" class="form-control mt-2" placeholder="${user.username}" disabled="disabled"><br>
        

        <label>Nome</label>
        <input type="text" name="firstname" class="form-control mt-2" placeholder="${user.firstName}" disabled="disabled"><br>
        
        
        <label>Cognome</label>
        <input type="text" name="lastname" class="form-control mt-2" placeholder="${user.lastName}" disabled="disabled"><br>
        
        
         <label>Immagine Profilo</label>
         <input type="text" name="imageProfile" class="form-control mt-2" placeholder="${user.imageProfile}" disabled="disabled"><br>

        <a href="${url}"><div class="btn btn-lg btn-primary btn-block">Modifica</div></a><br><br>
    
</div>