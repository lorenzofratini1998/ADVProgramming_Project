<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/profile/edit" var="url" />

<c:if test="${not empty message}">
    <div style="color: red; font-weight: bold; margin: 30px 0;">${message}</div>
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
         <input type="text" name="image" class="form-control mt-2" placeholder="${user.imageProfile}" disabled="disabled"><br>

        <a href="${url}"><div class="btn btn-lg btn-primary btn-block">Modifica</div></a><br><br>

    <c:if test="${not empty user.imageProfile}">
        <div class="md-form mt-4 mb-4">
            <h5 class="font-weight-normal"> Immagine del profilo attualmente caricata sul server: </h5>
            <div class="text-center">
                <img src="<c:url value="files/profile_pictures/${user.imageProfile}"/>" alt="Immagine profilo" class="img-thumbnail mx-auto w-50">
            </div>
        </div>
    </c:if>

</div>