<%--
 * index.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- ANONYMOUS -->
<security:authorize access="isAnonymous()">
 <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
   <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
   <li data-target="#carousel-example-generic" data-slide-to="1"></li>
   <li data-target="#carousel-example-generic" data-slide-to="2"></li>
   <li data-target="#carousel-example-generic" data-slide-to="3"></li>
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
   <div class="item active">
    <img src="images/empaste2.png" style="width: 100%" class="img-responsive">
    <div class="carousel-caption">
     <h1>Oferta empastes</h1>
     <p></p>
     <p>
      <a class="btn btn-lg btn-primary" href="http://localhost:8080/Acme-Health/offer/details.do?offerId=23"><spring:message code="welcome.button" /></a>
     </p>
    </div>
   </div>
   <div class="item">
    <img src="images/blanqueamiento2.png" style="width: 100%" class="img-responsive">
    <div class="carousel-caption">
    <h1>Oferta blanqueamiento</h1>
     <p></p>
     <p>
      <a class="btn btn-lg btn-primary" href="http://localhost:8080/Acme-Health/offer/details.do?offerId=22"><spring:message code="welcome.button" /></a>
     </p>
     </div>
   </div>
   <div class="item">
    <img src="images/masaje2.png" style="width: 100%" class="img-responsive">
    <div class="carousel-caption">
    <h1>Oferta masajes</h1>
     <p></p>
     <p>
      <a class="btn btn-lg btn-primary" href="http://localhost:8080/Acme-Health/offer/details.do?offerId=24"><spring:message code="welcome.button" /></a>
     </p>
     </div>
   </div>
   <div class="item">
    <img src="images/optica2.png" style="width: 100%" class="img-responsive">
    <div class="carousel-caption">
    <h1>Oferta optica</h1>
     <p></p>
     <p>
      <a class="btn btn-lg btn-primary" href="http://localhost:8080/Acme-Health/offer/details.do?offerId=26"><spring:message code="welcome.button" /></a>
     </p>
     </div>
   </div>
  </div>

  <!-- Controls -->
  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev"> <span
   class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span>
  </a> <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next"> <span
   class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span>
  </a>
 </div>
</security:authorize>

<p>
 <spring:message code="welcome.greeting.prefix" />
 ${name}
 <spring:message code="welcome.greeting.suffix" />
</p>

<p>
 <spring:message code="welcome.greeting.current.time" />
 ${moment}
</p>
