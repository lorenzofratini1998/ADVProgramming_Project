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
        <h5 class="text-center font-weight-bold mt-4 mb-4">Lista di tutti gli archivi</h5>
        <div class="font-weight-bold text-center">Numero archivi: ${numArchives}</div>
        <table class="table table-striped w-75 mx-auto">
            <thead>
            <tr>
                <c:if test="${isAdmin}">
                    <th scope="col">Elimina</th>
                </c:if>
                <th scope="col">Archivi</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${archives}" var="archive">
                <tr>
                    <c:if test="${isAdmin}">
                        <td>
                            <div class="row">
                                <div class="col-lg">

                                    <a class="btn btn-danger"
                                       href="<c:url value="/archives/delete/${archive.name}"/>"
                                       title="Elimina &quot;${archive.name}&quot;"
                                       onclick='return confirm("Sei sicuro di voler eliminare \"${archive.name}\"?");'>
                                        <i class="fa fa-trash"></i></a>

                                </div>
                            </div>
                        </td>
                    </c:if>
                    <td><a href="<c:url value="/archive/${archive.name}"/>">${archive.name}</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>