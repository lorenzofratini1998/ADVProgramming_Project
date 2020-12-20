<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/posts/edit/${postID}/attachments/link/new" var="new_link_url"/>
<c:url value="/posts/edit/${postID}/attachments/file/new" var="new_file_url"/>

<div class="col">
    <c:if test="${empty filesPost and empty linksPost}">
        <div class="text-center">
            <h3 class="font-weight-bold mx-auto">Il post non contiene alcun allegato!</h3>
        </div>
    </c:if>
    <c:if test="${not empty filesPost}">
        <div class="text-center">
            <h3 class="mb-3 font-weight-bold">Allegati di tipo file del post</h3>
        </div>
        <table class="table table-striped w-75 mx-auto">
            <thead>
            <tr>
                <th scope="col">File</th>
                <th scope="col">Modifica</th>
                <th scope="col">Elimina</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${filesPost}" var="file">
                <tr>
                    <td><i class="fa fa-file"> <!-- TODO: cambiare attributo href qui sotto, non funziona il download...  -->
                        <a href="/files/post_attachments/${file.name}"
                           title="Scarica ${file.description}" download>${file.description}</a>
                    </i></td>
                    <td><a class="btn btn-success"
                           href="<c:url value="/posts/edit/${postID}/attachments/file/${file.id}/edit"/>"
                           title="Modifica &quot;${file.description}&quot;">
                        <i class="fa fa-pencil-square-o"></i>
                    </a></td>
                    <td><a class="btn btn-danger"
                           href="<c:url value="/attachments/${file.id}/delete"/>"
                           title="Elimina &quot;${file.description}&quot;"
                           onclick='return confirm("Sei sicuro di voler eliminare \"${file.description}\"?");'>
                        <i class="fa fa-trash"></i></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${not empty linksPost}">
        <div class="text-center">
            <h3 class="mt-5 mb-3 font-weight-bold">Allegati di tipo link del post</h3>
        </div>
        <table class="table table-striped w-75 mx-auto">
            <thead>
            <tr>
                <th scope="col">Link</th>
                <th scope="col">Modifica</th>
                <th scope="col">Elimina</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${linksPost}" var="link">
                <tr>
                    <td><i class="fa fa-link"><a href="${link.link}"
                                                 title="Vai a ${link.description}"
                                                 target="_blank">${link.description}</a></i></td>
                    <td><a class="btn btn-success"
                           href="<c:url value="/posts/edit/${postID}/attachments/link/${link.id}/edit"/>"
                           title="Modifica &quot;${link.description}&quot;">
                        <i class="fa fa-pencil-square-o"></i>
                    </a></td>
                    <td><a class="btn btn-danger"
                           href="<c:url value="/attachments/${link.id}/delete"/>"
                           title="Elimina &quot;${link.description}&quot;"
                           onclick='return confirm("Sei sicuro di voler eliminare \"${link.description}\"?");'>
                        <i class="fa fa-trash"></i></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <div class="text-center mb-4">
        <h4 class="mt-5 mb-3 font-weight-bold">Aggiungi allegati</h4>
        <a class="btn btn-success mr-3" href="${new_link_url}" role="button">Aggiungi un nuovo link</a>
        <a class="btn btn-success ml-3" href="${new_file_url}" role="button">Aggiungi un nuovo file</a>
    </div>

</div>
