<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('admin')" var="isAdmin"/>
<c:url value="/tags/new" var="newTag_url"/>

<div class="col-md-12 mb-4">
    <div class="row">
        <c:if test="${fn:length(successMessage) > 0}">
            <div class="alert alert-success mx-auto" role="alert">${successMessage}
                <button type="button" class="close ml-3" data-dismiss="alert" aria-label="Close" style="font-size: 1.4rem;">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <c:if test="${fn:length(errorMessage) > 0}">
            <div class="alert alert-danger mx-auto" role="alert">${errorMessage}
                <button type="button" class="close ml-3" data-dismiss="alert" aria-label="Close" style="font-size: 1.4rem;">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
    </div>
    <div class="overflow-auto">
        <h5 class="text-center font-weight-bold mt-4 mb-4">Lista di tutti i tag</h5>
        <div class="font-weight-bold text-center">Numero tag: ${numTags}</div>
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
                                       title="Elimina &quot;${tag.name}&quot;"
                                       onclick='return confirm("Sei sicuro di voler eliminare \"${tag.name}\"?");'>
                                        <i class="fa fa-trash"></i></a>
                                </div>
                            </div>
                        </td>
                    </c:if>
                    <td><a href="<c:url value="/tag/${tag.name}"/>">${tag.name}</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <c:if test="${isAdmin}">
        <div class="text-center mb-4">
            <a type="button" class="btn btn-success" href="${newTag_url}">Crea un nuovo tag</a>
        </div>
    </c:if>
</div>