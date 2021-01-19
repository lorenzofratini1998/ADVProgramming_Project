<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/comments/edit/${comment.id }/save" var="action_url"/>
<div class="col">

    <form:form action="${action_url}" method="POST" modelAttribute="comment">
        <h1 class="h3 mb-3 font-weight-normals">Modifica il tuo commento</h1>

		<div class="form-group">
	        <label style="display: block;">Titolo Post
	            <input type="text" name="postTitle" value="${comment.getPost().title}" readonly="readonly" class="form-control mt-2" />
            </label>
        </div>
        <div class="form-group">
            <label style="display: block;">Titolo Commento
                <input name="title" class="form-control mt-2" value="${comment.title}">
            </label>
        </div>
       	<div class="form-group">
	        <label style="display: block;">Descrizione Commento
	            <textarea name="description" class="form-control mt-2" rows="4" autofocus style="resize:none">${comment.description}</textarea>
            </label>
        </div>

        <input class="btn btn-lg btn-primary btn-block" type="submit" value="Applica Modifiche!"/>
        <form:hidden path="id"/>
        <form:hidden path="hide"/>
        <form:hidden path="post.id"/>
        <form:hidden path="author.username"/>
    </form:form>
</div>