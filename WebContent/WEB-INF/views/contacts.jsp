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
        <img class="img-fluid rounded mb-4 mb-lg-0" src="<c:url value="/immagini/contatti.jpg"/>" alt="">
      </div>
      <!-- /.col-lg-8 -->
      <div class="col-lg-5">
        <h1 class="font-weight-light">Come contattarci</h1>
        <div>
        <p>Se hai riscontrato qualche problema o semplicemente vuoi chiedere qualche informazione contattaci pure ai seguenti indirizzi!</p>
        </div>
        <div>
        <b> Telefono:</b>
        <div>
         <i> 3336756349</i>
        </div>
        <div style='margin-top:20px'>
        <b> E-mail:</b>
        </div>
        <div>
        <i> advprogcommunity@gmail.com</i>
        </div>
        <div style='margin-top:20px'>
        <b>Seguici anche sui nostri social</b>
        </div>
        <div style='margin-top:5px'>
        <img class="img-fluid rounded mb-4 mb-lg-0" src="<c:url value="/immagini/facebook.png"/>" width="33" height="33" style="float:left;margin:5px" >
        <p>AdvProgCommunity</p>
        </div>
        <div>
        <img class="img-fluid rounded mb-4 mb-lg-0" src="<c:url value="/immagini/instagram.jpg"/>" width="40" height="40" style="float:left;margin:3px" >
        <p>AdvProgCommunity</p>
        </div>
        </div>
      </div>
      <!-- /.col-md-4 -->
    </div>
    <!-- /.row -->

    <!-- Call to Action Well -->
<%--    <div class="card text-white bg-secondary my-5 py-4 text-center">--%>
<%--      <div class="card-body">--%>
<%--        <p class="text-white m-0">Qualcosa qui in mezzo ci si metterà</p>--%>
<%--      </div>--%>
<%--    </div>--%>
    </div>
  <!-- /.container -->
</body>
</html>