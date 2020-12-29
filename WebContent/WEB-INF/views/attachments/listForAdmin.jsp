<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('admin')" var="isAdmin"/>

<div class="col-md-12 mb-4">
    <div class="row">
        <c:if test="${fn:length(successMessage) > 0}">
            <div class="alert alert-success mx-auto" role="alert">${successMessage}</div>
        </c:if>
        <c:if test="${fn:length(errorMessage) > 0}">
            <div class="alert alert-danger mx-auto" role="alert">${errorMessage}</div>
        </c:if>
    </div>

        <div class="overflow-auto">
            <h5 class="text-center font-weight-bold mt-4 mb-4">Lista di tutti gli allegati degli utenti non admin</h5>
            <div class="font-weight-bold text-center">Numero allegati: ${numAttachments}</div>

		<table class="table table-striped w-75 mx-auto">
                <thead>
                <tr>
                	<th scope="col">Id</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Post</th>
                    <th scope="col">Anteprima</th>
                    <th scope="col">Nascondi</th>
                    <th scope="col">Mostra</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${attachments}" var="attachment">
                    <tr>
                    	<td>${attachment.id}</td>
                        <td>${attachment.description}</td>
                        <td>${attachment.getPost().title}</td>
                        <td></td>
						<td>
                            <div class="row">
                                <div class="col-lg">
									<c:if test="${!attachment.isHide() }">
										<a class="btn btn-warning"
											href="<c:url value="/attachments/hide/${attachment.id}"/>"
											title="Nascondi &quot;${attachment.description}&quot;"
                                           onclick='return confirm("Sei sicuro di volerlo nascondere?");'> <i
											class="fa fa-eye-slash"></i>
										</a>
									</c:if>
								</div>
                            </div>
                        </td>
                        <td>
                            <div class="row">
                                <div class="col-lg">
									<c:if test="${attachment.isHide() }">
										<a class="btn btn-success"
											href="<c:url value="/attachments/show/${attachment.id}"/>"
											title="Mostra &quot;${attachment.description}&quot;">
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