<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/tags/new/save" var="action_url"/>
<div class="col">
    <%--@elvariable id="tag" type="it.univpm.advprog.blog.model.entities.Tag"--%>
    <form:form class="form-signin" action="${action_url}" method="POST" modelAttribute="tag">
        <h1 class="h3 mb-3 font-weight-normal">Inserisci un nuovo tag</h1>

        <form:label path="name">Nome Tag</form:label>
        <form:input path="name"/>

        <input type="submit" value="Submit"/>
    </form:form>
</div>
