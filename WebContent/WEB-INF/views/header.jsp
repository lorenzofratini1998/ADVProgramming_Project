<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('admin')" var="isAdmin"/>
<sec:authorize access="hasRole('user')" var="isUser"/>
<sec:authorize access="!isAuthenticated()" var="isNoAuth"/>

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

<c:if test="${isNoAuth}">
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${home_url}">Start Bootstrap</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive1"
                    aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"
                    onclick="$('.navbar-collapse').toggleClass('collapse');">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive1">
                <ul class="navbar-nav ml-auto">
                    <li class="">
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
            <a class="navbar-brand" href="${home_url}">Start Bootstrap</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive2"
                    aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"
                    onclick="$('.navbar-collapse').toggleClass('collapse');">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive2">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
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
                        <a class="nav-link" href="${my_posts_url}">Miei Post</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${my_comments_url}">Miei Commenti</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${profile_url}">User</a>
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
            <a class="navbar-brand" href="${home_url}">Start Bootstrap</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive3"
                    aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"
                    onclick="$('.navbar-collapse').toggleClass('collapse');">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive3">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
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
                        <a class="nav-link" href="${my_posts_url}">Miei Post</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${my_comments_url}">Miei Commenti</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${archives_url}">Gestione Archivi</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${tags_url}">Gestione Tag</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${profile_url}">Admin</a>
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