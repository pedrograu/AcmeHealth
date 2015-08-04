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
     <h1><spring:message code="welcome.empastes" /></h1>
     <p></p>
     <p>
      <a class="btn btn-primary btn-responsive" href="/Acme-Health/offer/details.do?offerId=31"><spring:message code="welcome.button" /></a>
     </p>
    </div>
   </div>
   <div class="item">
    <img src="images/blanqueamiento2.png" style="width: 100%" class="img-responsive">
    <div class="carousel-caption">
    <h1><spring:message code="welcome.blanqueamiento" /></h1>
     <p></p>
     <p>
      <a class="btn btn-primary btn-responsive" href="/Acme-Health/offer/details.do?offerId=30"><spring:message code="welcome.button" /></a>
     </p>
     </div>
   </div>
   <div class="item">
    <img src="images/masaje2.png" style="width: 100%" class="img-responsive">
    <div class="carousel-caption">
    <h1><spring:message code="welcome.masajes" /></h1>
     <p></p>
     <p>
      <a class="btn btn-primary btn-responsive" href="/Acme-Health/offer/details.do?offerId=32"><spring:message code="welcome.button" /></a>
     </p>
     </div>
   </div>
   <div class="item">
    <img src="images/optica2.png" style="width: 100%" class="img-responsive">
    <div class="carousel-caption">
    <h1><spring:message code="welcome.optica" /></h1>
     <p></p>
     <p>
      <a class="btn btn-primary btn-responsive" href="/Acme-Health/offer/details.do?offerId=34"><spring:message code="welcome.button" /></a>
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

<!-- ADMINISTRATOR -->
<security:authorize access="hasRole('ADMINISTRATOR')">
<a href="customerArea/administrator/list.do"><spring:message code="welcome.personal.area"/></a>
</security:authorize>

<!-- PATIENT -->
<security:authorize access="hasRole('PATIENT')">
      <a href="customerArea/patient/list.do"><spring:message code="welcome.personal.area"/></a>
</security:authorize>

<!-- SPECIALIST -->
<security:authorize access="hasRole('SPECIALIST')">
<a href="customerArea/specialist/list.do"><spring:message code="welcome.personal.area"/></a>
</security:authorize>


