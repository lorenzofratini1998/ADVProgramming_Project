<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('admin')" var="isAdmin"/>
<sec:authorize access="hasRole('user')" var="isUser"/>
<sec:authorize access="!isAuthenticated()" var="isNoAuth"/>
<sec:authorize access="isAuthenticated()" var="isAuth"/>


<c:url value="/" var="home_url"/>
<c:url value="/about" var="about_us_url"/>
<c:url value="/tags" var="tags_url"/>
<c:url value="/archives" var="archives_url"/>
<c:url value="/profile" var="profile_url"/>
<c:url value="/posts" var="my_posts_url"/>
<c:url value="/comments" var="my_comments_url"/>
<c:url value="/login" var="login_url"/>
<c:url value="/logout" var="logout_url"/>
<c:url value="/sign_up" var="sign_up_url"/>
<c:url value="/contacts" var="contacts_us_url"/>
<c:url value="/disclaimer" var="disclaimer_url"/>
<c:url value="/users" var="users_url"/>
<c:url value="/attachments" var="attachments_url"/>
<c:url value="/posts/manage" var="all_posts_url"/>
<c:url value="/comments/manage" var="all_comments_url"/>

<c:if test="${isNoAuth}">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${home_url}">AdvProgCommunity</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive1"
                    aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive1">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${home_url}">Home
                            <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${about_us_url}">Su di noi</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${contacts_us_url}">Contatti</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${disclaimer_url}">Disclaimer</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${login_url}">Login</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${sign_up_url}">Registrati</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <hr/>
</c:if>

<c:if test="${isUser}">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${home_url}">AdvProgCommunity</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive2"
                    aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive2">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${home_url}">Home
                            <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${about_us_url}">Su di noi</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${contacts_us_url}">Contatti</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${disclaimer_url}">Disclaimer</a>
                    </li>
                    <li class="navbar-item">
                        <div class="btn-group">
                            <a role="button" href="" class="btn nav-link dropdown-toggle"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">I tuoi contenuti</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="${my_comments_url}">I tuoi commenti</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="${my_posts_url}">I tuoi post</a>
                            </div>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${profile_url}"><sec:authentication property="principal.username"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${logout_url}">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <hr/>
</c:if>

<c:if test="${isAdmin}">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${home_url}">AdvProgCommunity</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive3"
                    aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive3">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${home_url}">Home
                            <span class="sr-only">(current)</span>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${about_us_url}">Su di noi</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${contacts_us_url}">Contatti</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${disclaimer_url}">Disclaimer</a>
                    </li>
                    <li class="navbar-item">
                        <div class="btn-group">
                            <a role="button" href="" class="btn nav-link dropdown-toggle"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">I tuoi contenuti</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="${my_comments_url}">I tuoi commenti</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="${my_posts_url}">I tuoi post</a>
                            </div>
                        </div>
                    </li>
                    <li class="navbar-item">
                        <div class="btn-group">
                            <a role="button" href="" class="btn nav-link dropdown-toggle"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestisci</a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="${attachments_url}">Gestisci Allegati</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="${archives_url}">Gestisci Archivi</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="${all_comments_url}">Gestisci Commenti</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="${all_posts_url}">Gestisci Post</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="${tags_url}">Gestisci Tag</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="${users_url}">Gestisci Utenti</a>
                            </div>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${profile_url}"><sec:authentication property="principal.username"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${logout_url}">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <hr/>
</c:if>

<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>