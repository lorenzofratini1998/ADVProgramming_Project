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
    <div class="col-md-6 mb-4">
        <div class="overflow-auto">
            <h5 class="text-center font-weight-bold mt-4 mb-4">Lista di tutti gli archivi</h5>
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
                <c:forEach items="${archives}" var="archives">
                    <tr>
                        <td>
                            <div class="row">
                                <div class="col-lg">
                                <c:if test="${isAdmin}">
                                    <a class="btn btn-danger"
                                       href="<c:url value="/archives/delete/${archives.name}"/>"
                                       title="Elimina &quot;${archives.name}&quot;"><i
                                            class="fa fa-trash"></i></a>
                                </c:if>
                                </div>
                            </div>
                        </td>
                        <td>${archives.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>