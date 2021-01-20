<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<sec:authorize access="isAuthenticated()" var="isAuth"/>

<c:url value="/post/${post.id}/comment/new/save" var="new_comment_url"/>

<div class="inner">
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

    <div class="image fit">
        <img src="<c:url value="/immagini/pic.jpg"/>" alt="Copertina post" class="img-fluid"/>
    </div>
    <header>
        <h1>${post.title}</h1>
        <p class="info">Postato in <a href="<c:url value="/archive/${post.archive.name}"/>">${post.archive.name}</a> da
            <a href="<c:url value="/author/${post.author.username}"/>">${post.author.username}</a></p>
    </header>
    <blockquote class="blockquote">
        <p><b>${post.shortDescription}</b></p>
    </blockquote>
    <p>${post.longDescription}</p>
	<hr>
    <h4>Tags</h4>
    <div class="row">
        <div class="3u 12u$(small)">
            <ul class="pl-4">
                <c:forEach items="${post.tags}" var="tag">
                    <li class="mr-3" style="display: inline;"><a href="<c:url value="/tag/${tag.name}"/>"
                                                                 class="button special">${tag.name}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
	<hr>
    <h4>Allegati</h4>
    <div class="row">
	    <c:if test="${empty filesList && empty linksList}">
	    		<div class="col">Non sono presenti allegati. </div>
	    </c:if>
        <div class="3u 12u$(small)">
            <ul class="actions vertical">
                <c:forEach items="${filesList}" var="file">
                    <c:choose>
                        <c:when test="${not file.noDownloadable}">
                            <i class="fa fa-file">
                                <a href="<c:url value="/files/post_attachments/${file.name}"/>"
                                   title="Scarica ${file.description}" download>${file.description}</a>
                            </i><br>
                        </c:when>
                        <c:otherwise>
                            <i class="fa fa-file">
                                <p style="display:inline;">${file.description}</p>
                            </i><br>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:forEach items="${linksList}" var="link">
                    <i class="fa fa-link">
                        <a href="${link.link}" title="Vai a ${link.description}" target="_blank">${link.description}</a>
                    </i><br>
                </c:forEach>
            </ul>
        </div>
    </div>
	<hr>
    <h4>Commenti</h4>
    <div class="row">
    	<c:if test="${empty post.comments }">
    		<div class="col">Non sono presenti commenti.</div>
    	</c:if>
        <div class="3u 12u$(small)">
            <ul class="actions vertical" style="list-style-type:none;">
                <c:forEach items="${post.comments}" var="comment">
                    <c:if test="${not comment.hide}">
                        <li><i class="fa fa-comment"></i>&nbsp;&nbsp;Scritto da <b>${comment.author.username}</b> -
                            <b>${comment.title}</b>
                            <div class="row pl-5">${comment.description}</div>
                        </li>
                        <br>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
	<hr>
    <c:if test="${isAuth}">

        <!-- Form -->
     	<div class="card mb-3">
     		<div class="card-header">
     			<h4>Scrivi un commento</h4>
     		</div>
     		<div class="card-body">
     		<form:form name="modulo" class="form-signin" action="${new_comment_url}" method="POST" modelAttribute="comment" onsubmit="return false">
            	<form:label path="title">Titolo</form:label>
            	<form:input id="titolo" path="title" class="form-control mt-2" placeholder="Inserisci titolo commento"/><br>

            	<form:label path="description">Descrizione</form:label>
            	<form:textarea id="testo" path="description" class="form-control mt-2" rows="4" style="resize:none" placeholder="Inserisci descrizione commento"/><br>
	            <div class="d-flex justify-content-center">
	            	<input type="submit" value="Inserisci" class="btn btn-primary col-4 mr-2" onclick="controlloCommento()"/>
	            	<input type="reset" value="Cancella" class="btn btn-danger col-4"/>
	            </div>
            	<form:hidden path="id"/>
            	<form:hidden path="hide"/>
        	</form:form>
     		</div>
     	
     	</div>
    </c:if>
</div>

<script type="text/javascript">


//Se vengono superati i controlli abilita l'inoltro del form (di default disabilitato)
function controlloCommento() {
	
	var title = document.getElementById("titolo").value;
	var text = document.getElementById("testo").value;
	if (title.length === 0 || text.length ===0) {
		
		alert("Commento non valido!");
		
		
	}
	else document.modulo.submit();
}


</script> 
