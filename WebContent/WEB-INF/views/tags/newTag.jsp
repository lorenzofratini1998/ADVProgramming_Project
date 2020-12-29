<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/tags/new/save" var="action_url"/>
<div class="col">
    <%--@elvariable id="tag" type="it.univpm.advprog.blog.model.entities.Tag"--%>
    <form:form class="form-signin" action="${action_url}" method="POST" modelAttribute="tag">
        <h3 class="font-weight-bold text-center mb-3">Inserisci un nuovo tag</h3>

        <form:label path="name">Nome Tag</form:label>
        <form:input path="name" class="form-control mt-2"/><br>

        <input type="submit" value="Submit" class="mt-3 btn btn-lg btn-primary btn-block"/><br><br>
    </form:form>
</div>
