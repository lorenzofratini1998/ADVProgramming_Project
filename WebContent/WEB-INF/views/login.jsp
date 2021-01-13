<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty errorMessage}">
	<div class="row">
		<div class="alert alert-danger mx-auto" role="alert">${errorMessage}
			<button type="button" class="close ml-3" data-dismiss="alert" aria-label="Close" style="font-size: 1.4rem;">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</div>
</c:if>

<div class="container row h-100 mx-auto">
	<div class="row h-100 justify-content-center align-items-center mx-auto">
	<div class="card">
	    <form action="<c:url value="/login" />" method="POST">
	        <!-- <img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72"
	             height="72">  -->
	        <div class="card-header">
	        	<h1 class="h3 font-weight-normal text-center">Login</h1>
	        </div>
	
			<div class="card-body">
		        <div class="input-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> <span class="fa fa-user"></span>
						</span>
					</div>
					<input type="text" name="username" class="form-control" placeholder="Username" required autofocus>
		        </div>
		    </div>
			<div class="card-body">
		       <div class="input-group">
		       		<div class="input-group-prepend">
						<span class="input-group-text"> <span class="fa fa-lock"></span>
						</span>
					</div>
		            <input type="password" name="password" class="form-control" placeholder="Password" required>
		        </div>
			</div>
			<div class="card-body">
	        	<button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
	        </div>
	    </form>
	</div>
</div>
</div>

