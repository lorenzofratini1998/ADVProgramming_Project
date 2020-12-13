<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty errorMessage}">
    <div style="color: red; font-weight: bold; margin: 30px 0;">${errorMessage}</div>
</c:if>

<div class="col">
    <form class="form-signin" action="<c:url value="/login" />" method="POST">
        <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72"
             height="72">
        <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>

        <label>
            <input type="text" name="username" class="form-control mt-2" placeholder="Username" required autofocus>
        </label>

        <label>
            <input type="password" name="password" class="form-control mt-2" placeholder="Password" required>
        </label>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</div>