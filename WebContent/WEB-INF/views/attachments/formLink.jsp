<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<c:url value="/posts/edit/${postID}/attachments/link/save" var="link_action_url"/>

<%--@elvariable id="link" type="it.univpm.advprog.blog.model.entities.Link"--%>
<form:form class="form-signin" action="${link_action_url}" method="POST" modelAttribute="link">

    <div class="text-center mb-3">
        <h3 class="font-weight-bold">${pageTitle}</h3>
    </div>

    <form:label path="description">Descrizione</form:label>
    <form:input path="description" class="form-control mt-2"/><br>

    <form:label path="link">Link</form:label>
    <form:input path="link" class="form-control mt-2"/><br>

    <form:hidden path="id"/>
    <form:hidden path="hide"/>
    <%--    <form:hidden path="post.id"/>--%>

    <input type="submit" value="Submit" class="mt-3 btn btn-lg btn-primary btn-block"/><br><br>
</form:form>