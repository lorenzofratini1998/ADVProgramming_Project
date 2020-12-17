<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()" var="isAuth"/>

<c:if test="${not empty message}">
	<div class="btn btn-success">${message}</div>
</c:if>
<div class="col-md-12 mb-4">
        <div class="overflow-auto">
            <h5 class="text-center font-weight-bold mt-4 mb-4">Lista di tutti i commenti</h5>
            <table class="table table-striped w-75 mx-auto">
                <thead>
                <tr>
                    <th scope="col">Commento</th>
                    <th scope="col">Post</th>
                    <th scope="col">Archivio</th>
                    <th scope="col">Modifica</th>
                    <th scope="col">Elimina</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${comments}" var="comment">
                    <tr>
                        <td>${comment.description}</td>
                        <td>${comment.getPost().title}</td>
                        <td>${comment.getPost().getArchive().name }</td>
						<td>
							<div class="row">
								<div class="col-lg">
									<a class="btn btn-success">
										<i class="fa fa-pencil-square-o"></i>
									</a>
								</div>
							</div>
						</td>
						<td>
                            <div class="row">
                                <div class="col-lg">
									<a class="btn btn-danger"
											href="<c:url value="/comments/delete/${comment.id}"/>"
                                           	title="Elimina &quot;${comment.description}&quot;"
                                       onclick='return confirm("Sei sicuro di voler eliminare il commento?");'>
										<i class="fa fa-trash"></i>
									</a>
								</div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
