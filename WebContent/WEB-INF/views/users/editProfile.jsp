<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/profile/edit/save" var="action_url"/>
<div class="col">
   
    <form:form class="form-signin" action="${action_url}" method="POST" modelAttribute="user" enctype="multipart/form-data">
        <h1 class="h3 mb-3 font-weight-normal">Modifica i tuoi Dati</h1>

        <label>Nome Utente</label>
        <input type="text" name="username" value="${user.username}" readonly="readonly" class="form-control mt-2" /><br>
        
        <input type="hidden" name="password" value="${user.password}">

        <label>Nome*</label>
        <input type="text" name="firstName" class="form-control mt-2" value="${user.firstName}" autofocus/><br>

        <label>Cognome*</label>
        <input type="text" name="lastName"  class="form-control mt-2" value="${user.lastName}"/><br>

        <label>Immagine Profilo</label>
        <input type="file" name="image" class="form-control mt-2"/><br>
        <p>NOTA: Se non viene caricata alcuna immagine del profilo rimarrà quella attualmente presente nel server.</p>
        <p>* Campo obbligatorio.</p>

        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Applica Modifiche!"/><br><br>

        <form:hidden path="imageProfile"/>
        <form:hidden path="admin"/>
        <form:hidden path="posts"/>
    </form:form>
</div>
