<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('admin')" var="isAdmin"/>

<div class="col-md-12 mb-4">
        <div class="overflow-auto">
            <h5 class="text-center font-weight-bold mt-4 mb-4">Lista di tutti gli utenti</h5>
		<table class="table table-striped w-75 mx-auto">
                <thead>
                <tr>
                	<th scope="col">Username</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col">Disabilita</th>
                    <th scope="col">Abilita</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                    	<td>${user.username}</td>
                        <td>${user.getFirstName()}</td>
                        <td>${user.getLastName()}</td>
						<td>
                            <div class="row">
                                <div class="col-lg">
									<c:if test="${!user.disabled }">
										<a class="btn btn-danger"
											href="<c:url value="/users/disable/${user.username}"/>"
											title="Disabilita &quot;${user.username}&quot;"
                                           onclick='return confirm("Sei sicuro di voler disabilitare ${user.username}?");'> <i
											class="fa fa-eye-slash"></i>
										</a>
									</c:if>
								</div>
                            </div>
                        </td>
                        <td>
                            <div class="row">
                                <div class="col-lg">
									<c:if test="${user.disabled }">
										<a class="btn btn-success"
											href="<c:url value="/users/enable/${user.username}"/>"
											title="Mostra &quot;${user.username}&quot;">
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