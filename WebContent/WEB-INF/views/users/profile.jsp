<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:url value="/profile/edit" var="url" />
<c:url value="/profile/delete" var="delete_url" />

<div class="row">
    <c:if test="${fn:length(successMessage) > 0}">
        <div class="alert alert-success mx-auto" role="alert">${successMessage}
			<button type="button" class="close ml-3" data-dismiss="alert" aria-label="Close" style="font-size: 1.4rem;">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
    </c:if>
    <c:if test="${fn:length(errorMessage) > 0}">
        <div class="alert alert-danger mx-auto" role="alert">${errorMessage}
			<button type="button" class="close ml-3" data-dismiss="alert" aria-label="Close" style="font-size: 1.4rem;">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
    </c:if>
</div>

<div class="card mb-5">
	<div class="card-header">
		<h1 class="h3 mb-3 font-weight-normal text-center">Profilo utente di ${user.username}</h1>
	</div>
	<div class="card-body">
	<div class="row">
		<div class="col-4 d-flex align-items-center">
			<c:if test="${not empty user.imageProfile}">
						<img
							src="<c:url value="/files/profile_pictures/${user.imageProfile}"/>"
							alt="Immagine profilo" class="img-thumbnail mx-auto">
			</c:if>
			<c:if test="${empty user.imageProfile}">
						<img
							src="<c:url value="/files/profile_pictures/default.jpg"/>"
							alt="Immagine profilo" class="img-thumbnail mx-auto">
			</c:if>
		</div>
		<div class="col-8">
			<div class="form-group">
				<label>Nome Utente</label>
	        	<input type="text" name="username" class="form-control mt-2" placeholder="${user.username}" disabled="disabled">
        	</div>

			<div class="form-group">
		        <label>Nome</label>
		        <input type="text" name="firstname" class="form-control mt-2" placeholder="${user.firstName}" disabled="disabled">
	        </div>
	        
	        <div class="form-group">
		        <label>Cognome</label>
		        <input type="text" name="lastname" class="form-control mt-2" placeholder="${user.lastName}" disabled="disabled">
	        </div>
	        
	        <div class="form-group">
		         <label>Immagine Profilo</label>
		         <input type="text" name="image" class="form-control mt-2" placeholder="${user.imageProfile}" disabled="disabled"><br>
	        </div>
	        
	         <div class="d-flex justify-content-between">
	        	<a href="${url}" class="btn btn-primary col-6" role="button">Modifica</a>
	        	<a href="${delete_url}" class="btn btn-danger col-2" role="button" onclick='return confirm("Sei sicuro di eliminare il tuo account?");'>Elimina</a>
			</div>
		</div>
	</div>
	</div>
</div>