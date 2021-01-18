<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/posts/save" var="action_url"/>
<div class="col">

    <%--@elvariable id="post" type="it.univpm.advprog.blog.model.entities.Post"--%>
    <form:form name="modulo" class="form-signin" action="${action_url}" method="POST" modelAttribute="post" onsubmit="return false">
        <div class="text-center mb-3">
            <h3 class="font-weight-bold">${titlePageForm}</h3>           
        </div>
        <p>(* Campo obbligatorio)</p>

        <form:label path="title">Titolo*</form:label>
        <form:input id="titolo" path="title" class="form-control mt-2"/><br>

        <form:label path="shortDescription">Descrizione breve</form:label>
        <form:input path="shortDescription" class="form-control mt-2"/><br>

        <form:label path="longDescription">Descrizione estesa</form:label>
        <form:textarea path="longDescription" class="form-control mt-2"/><br>

        <label>Seleziona il/i Tag*
            <select name="tagsSelected" multiple size="5" class="form-control mt-2" >
                <c:choose>
                    <c:when test="${not empty postTags}">
                        <c:forEach items="${allTags}" var="tag">
                            <c:choose>
                                <c:when test="${postTags.contains(tag.name)}">
                                    <option value="${tag.name}" selected>${tag.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${tag.name}">${tag.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${allTags}" var="tag">
                            <option value="${tag.name}">${tag.name}</option>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </select><br>
        </label>
        
        
        <form:hidden path="id"/>
        <form:hidden path="hide"/>
        
		
        
        <form:hidden path="archive.name"/>
        <input type="submit" value="Submit" class="mt-3 btn btn-lg btn-primary btn-block" onclick="controlloForm()"/><br><br>
    </form:form>
</div>

 
<script type="text/javascript">


//Se vengono superati i controlli abilita l'inoltro del form (di default disabilitato)
function controlloForm() {
	
	var title = document.getElementById("titolo").value;
	if (title.length === 0) {
		
		alert("Post incompleto!");
		
		
	}
	else document.modulo.submit();
}


</script> 

