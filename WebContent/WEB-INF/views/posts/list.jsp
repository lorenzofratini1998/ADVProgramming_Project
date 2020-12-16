<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()" var="isAuth"/>
<script type="text/javascript">
	$('#myModal').on('shown.bs.modal', function () {
		  $('#myInput').trigger('focus')
		})
</script>
<div class="col-md-12 mb-4">
        <div class="overflow-auto">
            <h5 class="text-center font-weight-bold mt-4 mb-4">Lista di tutti i post</h5>
            <div class="font-weight-bold">Numero post: ${numPosts }</div>
            <table class="table table-striped w-75 mx-auto">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Titolo</th>
                    <th scope="col">Descrizione breve</th>
                    <th scope="col">Archivio</th>
                    <th scope="col">Modifica</th>
                    <th scope="col">Elimina</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${posts}" var="post">
                    <tr>
                        <td>${post.id}</td>
                        <td>${post.title}</td>
                        <td>${post.shortDescription }</td>
                        <td>${post.getArchive().name }</td>
						<td>
							<div class="row">
								<div class="col-lg">
									<a class="btn btn-success"
                                       href="<c:url value="/posts/edit/${post.id}"/>"
                                       title="Modifica &quot;${post.title}&quot;">
										<i class="fa fa-pencil-square-o"></i>
									</a>
								</div>
							</div>
						</td>
						<td>
                            <div class="row">
                                <div class="col-lg">
									<a class="btn btn-danger"
											href="<c:url value="/posts/delete/${post.id}"/>"
                                           	title="Elimina &quot;${post.title}&quot;"> 
										<i class="fa fa-trash"></i>
									</a>
								</div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
	</div>
    </div>
