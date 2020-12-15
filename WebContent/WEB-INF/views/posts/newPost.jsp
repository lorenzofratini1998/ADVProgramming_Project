<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/posts/new/save" var="action_url"/>
<div class="col">
    <%--@elvariable id="post" type="it.univpm.advprog.blog.model.entities.Post"--%>
    <form:form class="form-signin" action="${action_url}" method="POST" modelAttribute="post">
        <h1 class="h3 mb-3 font-weight-normal">Inserisci un nuovo post</h1>

        <form:label path="title">Titolo</form:label>
        <form:input path="title"/>

        <form:label path="shortDescription">Descrizione breve</form:label>
        <form:input path="shortDescription"/>

        <form:label path="longDescription">Descrizione estesa</form:label>
        <form:input path="longDescription"/>

        <label>Seleziona il/i Tag
            <select name="tags" multiple size="5">
                <c:forEach items="${tags}" var="tag">
                    <option value="${tag.name}">${tag.name}</option>
                </c:forEach>
            </select>
        </label>

        <form:hidden path="id"/>
        <form:hidden path="author" value="${author}"/>
        <form:hidden path="archive" value="${archive}"/>

        <input type="submit" value="Submit"/>
    </form:form>
</div>
