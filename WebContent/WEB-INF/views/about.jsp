<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>

<body>
  <!-- Page Content -->
  <div class="container">

    <!-- Heading Row -->
    <div class="row align-items-center my-5">
      <div class="col-lg-7">
        <img class="img-fluid rounded mb-4 mb-lg-0" src="<c:url value="/immagini/blog.jpg"/>" alt="">
      </div>
      <!-- /.col-lg-8 -->
      <div class="col-lg-5">
        <h1 class="font-weight-light">Chi siamo</h1>
        <p>AdvProgCommunity è un blog creato nel 2020 da quattro ragazzi studenti dell'Università Politecnica delle Marche. L'idea è quella di sviluppare una community in cui gli utenti, una volta registrati, possono scrivere dei post e anche dei commenti in modo da scambiarsi informazioni e creare un dialogo. Il blog sarà gestito da un admin che ha la funzione di moderatore.</p>
      </div>
      <!-- /.col-md-4 -->
    </div>
    <!-- /.row -->

    <!-- Call to Action Well -->
    <div class="card text-white bg-secondary my-5 py-4 text-center">
      <div class="card-body">
        <p class="text-white m-0"> <b> Partecipa alle discussioni ed esprimi le tue opinioni! </b></p>
      </div>
    </div>
  </div>
  <!-- /.container -->
</body>
</html>