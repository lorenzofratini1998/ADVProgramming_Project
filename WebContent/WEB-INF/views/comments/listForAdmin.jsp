<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('admin')" var="isAdmin"/>

<div class="col-md-12 mb-4">
        <div class="overflow-auto">
            <h5 class="text-center font-weight-bold mt-4 mb-4">Lista di tutti i commenti di tutti gli autori</h5>
			<c:if test="${not empty showMessage}">
				<div class="btn btn-success">${showMessage}</div>
			</c:if>
		<table class="table table-striped w-75 mx-auto">
                <thead>
                <tr>
                	<th scope="col">Id</th>
                    <th scope="col">Commento</th>
                    <th scope="col">Post</th>
                    <th scope="col">Archivio</th>
                    <th scope="col">Autore</th>
                    <th scope="col">Nascondi</th>
                    <th scope="col">Mostra</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${comments}" var="comment">
                    <tr>
                    	<td>${comment.id}</td>
                        <td>${comment.description}</td>
                        <td>${comment.getPost().title}</td>
                        <td>${comment.getPost().getArchive().name }</td>
                        <td>${comment.getAuthor().username }</td>
						<td>
                            <div class="row">
                                <div class="col-lg">
									<c:if test="${!comment.isHide() }">
										<a class="btn btn-warning"
											href="<c:url value="/comments/manage/hide/${comment.id}"/>"
											title="Nascondi &quot;${comment.description}&quot;"
                                           onclick='return confirm("Sei sicuro di voler nascondere il commento?");'> <i
											class="fa fa-eye-slash"></i>
										</a>
									</c:if>
								</div>
                            </div>
                        </td>
                        <td>
                            <div class="row">
                                <div class="col-lg">
									<c:if test="${comment.isHide() }">
										<a class="btn btn-success"
											href="<c:url value="/comments/manage/show/${comment.id}"/>"
											title="Mostra &quot;${comment.description}&quot;">
                                            <i class="fa fa-eye"></i>
										</a>
									</c:if>
								</div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>