<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('admin')" var="isAdmin"/>

<div class="row">
    <c:if test="${fn:length(message) > 0}">
        <p class="mx-auto">${message}</p>
    </c:if>
</div>

<div class="row">
    <c:if test="${isAdmin}">
        <div class="col-md-4 mb-4 mx-auto">
            <div>
                <c:url value="/tags/new" var="newTag_url"/>
                <a type="button" class="btn btn-success" href="${newTag_url}">Crea un nuovo tag</a>
            </div>
        </div>
    </c:if>

    <div class="col-md-6 mb-4">
        <div class="overflow-auto">
            <h5 class="text-center font-weight-bold mt-4 mb-4">Lista di tutti i tag</h5>
            <table class="table table-striped w-75 mx-auto">
                <thead>
                <tr>
                    <c:if test="${isAdmin}">
                        <th scope="col">Elimina</th>
                    </c:if>
                    <th scope="col">Tag</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${tags}" var="tag">
                    <tr>
                        <c:if test="${isAdmin}">
                            <td>
                                <div class="row">
                                    <div class="col-lg">
                                        <a class="btn btn-danger"
                                           href="<c:url value="/tags/delete/${tag.name}"/>"
                                           title="Elimina &quot;${tag.name}&quot;"><i
                                                class="fa fa-trash"></i></a>
                                    </div>
                                </div>
                            </td>
                        </c:if>
                        <td>${tag.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>