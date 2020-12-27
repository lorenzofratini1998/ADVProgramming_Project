<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<sec:authorize access="isAuthenticated()" var="isAuth"/>

<c:url value="/blog/post/${post.id}/comment/new/save" var="new_comment_url"/>

<c:if test="${not empty message}">
    <div style="color: red; font-weight: bold; margin: 30px 0;">${message}</div>
</c:if>

<div class="inner">
    <div class="image fit">
        <img src="<c:url value="/immagini/pic.jpg"/>" alt="Copertina post" class="img-fluid"/>
    </div>
    <header>
        <h1>${post.title}</h1>
        <p class="info">Postato in <a href="<c:url value="/archive/${post.archive.name}"/>">${post.archive.name}</a> da
            <a href="<a href="<c:url value="/author/${post.author.username}"/>">${post.author.username}</a></p>
    </header>
    <blockquote class="blockquote">
        <p><b>${post.shortDescription}</b></p>
    </blockquote>
    <p>${post.longDescription}</p>

    <h4>Tags</h4>
    <div class="row">
        <div class="3u 12u$(small)">
            <ul class="pl-4">
                <c:forEach items="${post.tags}" var="tag">
                    <li style="display: inline;"><a href="<c:url value="/tag/${tag.name}"/>"
                                                    class="button special">${tag.name}</a></li>
                    &nbsp;&nbsp;&nbsp;
                </c:forEach>
            </ul>
        </div>
    </div>

    <h4>Allegati</h4> <!-- TODO: da migliorare -->
    <div class="row">
        <div class="3u 12u$(small)">
            <ul class="actions vertical">
                <c:forEach items="${post.attachments}" var="attachment">
                    <c:if test="${not attachment.hide}">
                        <li>${attachment.description}</li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>

    <h4>Commenti</h4>
    <div class="row">
        <div class="3u 12u$(small)">
            <ul class="actions vertical" style="list-style-type:none;">
                <c:forEach items="${post.comments}" var="comment">
                    <c:if test="${not comment.hide}">
                        <li><i class="fa fa-comment"></i>&nbsp;&nbsp;Scritto da <b>${comment.author.username}</b> -
                            <b>${comment.title}</b>
                            <div class="row pl-5">${comment.description}</div>
                        </li>
                        <br>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>

    <c:if test="${isAuth}">

        <!-- Form -->
        <h4>Scrivi un commento</h4>
        <form:form class="form-signin" action="${new_comment_url}" method="POST" modelAttribute="comment">
            <form:label path="title">Titolo</form:label>
            <form:input path="title" class="form-control mt-2" placeholder="Inserisci titolo commento"/><br>

            <form:label path="description">Descrizione</form:label>
            <form:textarea path="description" class="form-control mt-2"
                           placeholder="Inserisci descrizione commento"/><br>

            <div>
                <ul class="list-unstyled">
                    <li><input type="submit" value="Inserisci" class="mt-3 btn btn-lg btn-primary btn-block"/></li>
                    <li><input type="reset" value="Cancella" class="mt-3 btn btn-lg btn-primary btn-block"/></li>
                </ul>
            </div>
            <form:hidden path="id"/>
            <form:hidden path="hide"/>
        </form:form>
    </c:if>
</div>
