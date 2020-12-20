<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/posts/edit/${postID}/attachments/file/save" var="file_action_url"/>

<%--@elvariable id="file" type="it.univpm.advprog.blog.model.entities.File"--%>
<form:form class="form-signin" action="${file_action_url}" method="POST" modelAttribute="file">

    <div class="text-center mb-3">
        <h3 class="font-weight-bold">Modifica file</h3>
    </div>

    <form:label path="name">Nome</form:label>
    <form:input path="name" class="form-control mt-2" readonly="true"/><br>

    <form:label path="description">Descrizione</form:label>
    <form:input path="description" class="form-control mt-2"/><br>

    <c:if test="${file.noDownloadable}">
        <div class="form-check">
            <input class="form-check-input" type="radio" name="noDownloadable" id="false" value="false">
            <label class="form-check-label" for="false">
                Scaricabile
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="noDownloadable" id="true" value="true" checked>
            <label class="form-check-label" for="true">
                Non scaricabile
            </label>
        </div>
    </c:if>

    <c:if test="${not file.noDownloadable}">
        <div class="form-check">
            <input class="form-check-input" type="radio" name="noDownloadable" id="false" value="false" checked>
            <label class="form-check-label" for="false">
                Scaricabile
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="noDownloadable" id="true" value="true">
            <label class="form-check-label" for="true">
                Non scaricabile
            </label>
        </div>
    </c:if>

    <form:hidden path="id"/>
    <form:hidden path="hide"/>
    <%--    <form:hidden path="post.id"/>--%>
    <input type="submit" value="Submit" class="mt-3 btn btn-lg btn-primary btn-block"/><br><br>
</form:form>