<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false"%>

<sec:authorize access="isAuthenticated()" var="isAuth"/>

 <div class="container">
    <div class="row">

      <!-- Blog Entries Column -->
      <div class="col-md-8">

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

		  <h2 class="my-4 text-center">${postsTitle}</h2>
		  <c:choose>
			  <c:when test="${numPosts != 0}">
				  <div class="font-weight-bold text-center">Numero post: ${numPosts}</div>
			  </c:when>
			  <c:otherwise>
				  <div class="font-weight-bold text-center">Nessun post presente!</div>
			  </c:otherwise>
		  </c:choose>

		<div>
			<c:forEach items="${posts}" var="p">
				<div class="card mb-4">
				<img class="card-img-top" src="<c:url value="/immagini/pic.jpg"/>" alt="Card image cap">
				<div class="card-body">
					<h2 class="card-title">${p.title}</h2>
					<p class="card-text">${p.shortDescription}</p>
					 <a href="<c:url value="/post/${p.id}"/>" class="btn btn-primary">Dettagli post &rarr;</a>
				</div>
				<div class="card-footer text-muted">
	    			Postato in <a href="<c:url value="/archive/${p.getArchive().name}"/>">${p.getArchive().name}</a> da <a href="<c:url value="/author/${p.getAuthor().username}"/>">${p.getAuthor().username}</a>
	          	</div>
				</div>
			</c:forEach>
		</div>

		  <c:if test="${numPosts != 0}">
			<div id="pagination" class="pagination justify-content-center mb-4">

				<c:url value="/" var="prev">
					<c:param name="page" value="${page-1}" />
				</c:url>
				<c:if test="${page > 1}">
					<li class="page-item">
						<a href="<c:out value="${prev}" />" class="pn prev page-link">Prev</a>
					</li>
				</c:if>

				<c:forEach begin="1" end="${maxPages}" step="1" varStatus="i">
					<c:choose>
						<c:when test="${page == i.index}">
						<li class="page-item disabled">
							<span class="page-link">${i.index}</span>
						</li>
						</c:when>
						<c:otherwise>
							<c:url value="/" var="url">
								<c:param name="page" value="${i.index}" />
							</c:url>
							<li class="page-item">
								<a href='<c:out value="${url}" />' class="page-link">${i.index}</a>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:url value="/" var="next">
					<c:param name="page" value="${page + 1}" />
				</c:url>
				<c:if test="${page + 1 <= maxPages}">
					<li class="page-item">
						<a href='<c:out value="${next}" />' class="pn next page-link">Next</a>
					<li class="page-item">
				</c:if>
			</div>
		  </c:if>

			<!-- Pagination 
        <ul class="pagination justify-content-center mb-4">
          <li class="page-item">
            <a class="page-link" href="#">&larr; Older</a>
          </li>
          <li class="page-item disabled">
            <a class="page-link" href="#">Newer &rarr;</a>
          </li>
        </ul>
        -->

      </div>

      <!-- Sidebar Widgets Column -->
      <div class="col-md-4">
		<c:if test="${isAuth}">
			<a type="button" class="btn btn-success" href="<c:url value="/posts/new"/>">Scrivi un nuovo post</a>
		</c:if>
        <!-- Achieves Widget -->
        
        <div class="card my-4">
          <h5 class="card-header">Archivi</h5>
          <div class="card-body">
            <div class="row">
             <c:forEach items="${archives}" var="a">
            	<div class="col-lg-12">
            		<ul class="list-unstyled mb-0">
	            		<li>
	                    	<a href="<c:url value="/archive/${a.name}"/>">${a.name}</a>
	                  	</li>
	                </ul>
                </div>
            </c:forEach>
            </div>
			  <div class="row">
				  <a href="<c:url value="/archives"/>" class="mt-2 mx-auto" style="font-size: small;">Vai alla lista degli archivi</a>
			  </div>
           </div>
		</div>
        
        <!-- Tags Widget -->
        <div class="card my-4">
          <h5 class="card-header">Tags</h5>
          <div class="card-body">
            <div class="row">
            <c:forEach items="${tags}" var="t">
            	<div class="col-lg-12">
            		<ul class="list-unstyled mb-0">
	            		<li>
	                    	<a href="<c:url value="/tag/${t.name}"/>">${t.name}</a>
	                  	</li>
	                </ul>
                </div>
            </c:forEach>
            </div>
			  <div class="row">
				  <a href="<c:url value="/tags"/>" class="mt-2 mx-auto" style="font-size: small;">Vai alla lista dei tag</a>
			  </div>
          </div>
        </div>

      </div>

    </div>
    <!-- /.row -->

</div>
