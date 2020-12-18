<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/posts/save" var="action_url"/>
<div class="col">
    <%--@elvariable id="post" type="it.univpm.advprog.blog.model.entities.Post"--%>
    <form:form class="form-signin" action="${action_url}" method="POST" modelAttribute="post">
        <h1 class="h3 mb-3 font-weight-normal">${titlePageForm}</h1>

        <form:label path="title">Titolo</form:label>
        <form:input path="title" class="form-control mt-2"/><br>

        <form:label path="shortDescription">Descrizione breve</form:label>
        <form:input path="shortDescription" class="form-control mt-2"/></br>

        <form:label path="longDescription">Descrizione estesa</form:label>
        <form:textarea path="longDescription" class="form-control mt-2"/><br>

        <label>Seleziona il/i Tag
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
        <%--        <form:hidden path="author.username"/>--%>
        <form:hidden path="archive.name"/>

        <input type="submit" value="Submit" class="btn btn-lg btn-primary btn-block"/><br><br>
    </form:form>
</div>
