<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="col-md-12 mb-4">
        <div class="overflow-auto">
            <h5 class="text-center font-weight-bold mt-4 mb-4">${post.title }</h5>
            <table class="table table-striped w-75 mx-auto">
                <thead>
                <tr><td align="center"> <b> Descrizione</b> </td>
				<tr><td>${post.longDescription }</td>
				<tr><td align="center"> <b> Archivio </b> </td>
				<tr><td>${post.getArchive().name }</td>
				<tr><td align="center"> <b> Autore del post </b> </td>
				<tr><td>${post.getAuthor().username }</td>
				<tr><td align="center"> <b> Tags Associati </b> </td>
				<c:forEach items="${post.getTags()}" var="tag">
				<tr><td>${tag.name}</td>				
				</c:forEach>
				<tr><td align="center"> <b> Commenti Associati </b> </td>
				<c:forEach items="${post.getComments()}" var="comments">
				<tr><td>${comments.description}</td>
				</c:forEach>
				<tr><td align="center"> <b> Allegati </b> </td>
				<c:forEach items="${post.getAttachments()}" var="attachments">
				<tr><td>${attachments.description}</td>
				</c:forEach>
				</table>				
                </thead>
                <tbody>
		</div>
</div>
