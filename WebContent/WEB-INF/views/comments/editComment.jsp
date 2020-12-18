<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/comments/edit/${comment.id }/save" var="action_url"/>
<div class="col">
   
    <form:form action="${action_url}" method="POST" modelAttribute="comment">
        <h1 class="h3 mb-3 font-weight-normals">Modifica il tuo commento</h1>
		
		<div class="form-group">
	        <label>Titolo Post</label>
	        <input type="text" name="post" value="${comment.getPost().title}" readonly="readonly" class="form-control mt-2" />
        </div>
       
       	<div class="form-group">
	        <label>Commento</label>
	        <textarea name="comment" class="form-control mt-2" autofocus>${comment.description}</textarea>
        </div>

        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Applica Modifiche!"/>
    </form:form>
</div>