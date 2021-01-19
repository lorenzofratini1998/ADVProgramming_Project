<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/tags/new/save" var="action_url"/>

<div class="row justify-content-center">
	<div class="col-6">
	    <%--@elvariable id="tag" type="it.univpm.advprog.blog.model.entities.Tag"--%>
	    <form:form class="form-signin" action="${action_url}" method="POST" modelAttribute="tag">
	        <h3 class="font-weight-bold text-center mb-3">Inserisci un nuovo tag</h3>
	
	        <form:label path="name">Nome Tag</form:label>
	        <form:input path="name" class="form-control mt-2"/>
	
			<div class="d-flex justify-content-center">
				<input type="submit" value="Inserisci" class="mt-3 btn btn-primary col-4"/>
			</div>
	    </form:form>
	</div>
</div>
