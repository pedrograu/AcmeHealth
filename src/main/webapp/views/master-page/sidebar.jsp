<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


<aside>
 <div id="sidebar" class="nav-collapse ">
  <!-- sidebar menu start-->
  <ul class="sidebar-menu" id="nav-accordion">
   <security:authorize access="isAuthenticated()">

    <p class="centered">
     <!-- ADMINISTRATOR -->
     <security:authorize access="hasRole('ADMINISTRATOR')">
      <a href="customerArea/administrator/list.do"><i class="fa fa-user-secret fa-4x"></i></a>
      <h5 class="centered"><security:authentication      property="principal.username" /></h5>
     </security:authorize>
     <!-- PATIENT -->
     <security:authorize access="hasRole('PATIENT')">
      <a href="customerArea/patient/list.do"><i class="fa fa-user fa-4x"></i></a>
     <h5 class="centered"><security:authentication      property="principal.username" /></h5>
     </security:authorize>
     <!-- SPECIALIST -->
     <security:authorize access="hasRole('SPECIALIST')">
      <a href="customerArea/specialist/list.do"><i class="fa fa-user-md fa-4x"></i></a>
      <h5 class="centered"><security:authentication      property="principal.username" /></h5>
     </security:authorize>
    </p>
    <h5 class="centered"></h5>
   </security:authorize>

   <!-- ADMINISTRATOR -->
   <security:authorize access="hasRole('ADMINISTRATOR')">

    <%--     <li class="mt"><a href="register/specialist/edit.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.register.specialist" /></span>
    </a></li> --%>

    <li class="mt"><a href="specialty/administrator/list-all.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.specialty.listAll" /></span>
    </a></li>

    <%--     <li class="sub-menu"><a href="javascript:;"> <i class="fa fa-desktop"></i> <span><spring:message
        code="master.page.specialty" /></span>
    </a>
     <ul class="sub">

      <li><a href="specialty/administrator/list-all.do"><spring:message code="master.page.specialty.listAll" /></a></li>
      <li><a href="specialty/administrator/create.do"><spring:message code="master.page.specialty.create" /></a></li>

     </ul></li> --%>

    <li class="sub-menu"><a href="javascript:;"> <i class="fa fa-desktop"></i> <span><spring:message
        code="master.page.offer" /></span>
    </a>
     <ul class="sub">

      <li><a href="offer/administrator/list-own.do"><spring:message code="master.page.offer.listOwn" /></a></li>
      <li><a href="offer/administrator/list-not-finished.do"><spring:message
         code="master.page.offer.listNotFinished" /></a></li>
      <li><a href="offer/administrator/list-active.do"><spring:message code="master.page.offer.listActive" /></a></li>
      <%-- <li><a href="offer/administrator/list-order.do"><spring:message code="master.page.offer.listOrder" /></a></li> --%>
     </ul></li>

    <li class=""><a href="patient/administrator/list.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.patient.list" /></span>
    </a></li>
    <li class=""><a href="specialist/administrator/list.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.specialist.list" /></span>
    </a></li>

   </security:authorize>

   <!-- PATIENT -->
   <security:authorize access="hasRole('PATIENT')">

    <li class=""><a href="appointment/patient/list.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.appointment.list" /></span>
    </a></li>

    <li class=""><a href="prescription/patient/list-my-prescription.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.prescription.listMyPrescription" /></span>
    </a></li>
    <li class=""><a href="specialist/patient/list.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.specialist.listAll" /></span>
    </a></li>

    <li class="sub-menu"><a href="javascript:;"> <i class="fa fa-desktop"></i> <span><spring:message
        code="master.page.offer" /></span>
    </a>
     <ul class="sub">

      <li><a href="offer/patient/list-own.do"><spring:message code="master.page.offerOwn" /></a></li>
      <li><a href="offer/patient/list-not-finished.do"><spring:message code="master.page.offerNotFinish" /></a></li>
     </ul></li>


    <li class="sub-menu"><a href="javascript:;"> <i class="fa fa-cogs"></i> <span><spring:message
        code="master.page.message" /></span>
    </a>
     <ul class="sub">
      <li><a href="message/customer/list-outbox.do"><spring:message code="master.page.message.list.outbox" /></a></li>
      <li><a href="message/customer/list-inbox.do"><spring:message code="master.page.message.list.inbox" /></a></li>
      <li><a href="message/customer/create.do"><spring:message code="master.page.message.create" /></a></li>
     </ul></li>

   </security:authorize>

   <!-- SPECIALIST -->
   <security:authorize access="hasRole('SPECIALIST')">

    <li class=""><a href=appointment/specialist/listFinish.do> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.appointment.listFinish" /></span>
    </a></li>


    <li class="sub-menu"><a href="javascript:;"> <i class="fa fa-desktop"></i> <span><spring:message
        code="master.page.specialist.patient" /></span>
    </a>
     <ul class="sub">

      <li><a href="patient/specialist/list-own.do"><spring:message
         code="master.page.specialist.patient.list.own" /></a></li>
      <li><a href="patient/specialist/list-today.do"><spring:message
         code="master.page.specialist.patient.list.today" /></a></li>
     </ul></li>


    <li class=""><a href=timetable/specialist/list-own.do> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.specialist.timetable.list.own" /></span>
    </a></li>




    <li class=""><a href="offer/specialist/list-not-finished.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.specialist.listNotFinish" /></span>
    </a></li>

    <li class=""><a href="freeDay/specialist/list-own.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.specialist.freeDays.list-own" /></span>
    </a></li>


    <li class="sub-menu"><a href="javascript:;"> <i class="fa fa-desktop"></i> <span><spring:message
        code="master.page.message" /></span>
    </a>
     <ul class="sub">

      <li><a href="message/customer/list-outbox.do"><spring:message code="master.page.message.list.outbox" /></a></li>
      <li><a href="message/customer/list-inbox.do"><spring:message code="master.page.message.list.inbox" /></a></li>
      <li><a href="message/customer/create.do"><spring:message code="master.page.message.create" /></a></li>
     </ul></li>



   </security:authorize>

   <!-- ANONYMOUS -->
   <security:authorize access="isAnonymous()">

    <li class=""><a href="specialty/list-all.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.specialty.listAll" /></span>
    </a></li>

    <li class=""><a href="specialist/list.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.specialist.listAll" /></span>
    </a></li>

<%--     <li class=""><a href="offer/list-not-finish.do"> <i class="fa fa-dashboard"></i> <span><spring:message
        code="master.page.offer.listNotFinished" /></span>
    </a></li> --%>


   </security:authorize>




  </ul>
  <!-- sidebar menu end-->
 </div>
</aside>
<%-- 
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
 <div class="container-fluid">

  <!-- HEADER-NAVBAR -->
  <div class="navbar-header">
   <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
    aria-expanded="false" aria-controls="navbar">
    <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span>
   </button>
   <a class="navbar-brand" href="#"> Logotipo </a>
  </div>

  <!-- BODY-NAVBAR -->
  <div id="navbar" class="collapse navbar-collapse">
   <ul class="nav navbar-nav">


    <!-- ANONYMOUS -->
    <security:authorize access="isAnonymous()">

     <li><a href="specialty/list-all.do"><spring:message code="master.page.specialty.listAll" /></a></li>
     <li><a href="specialist/list.do"><spring:message code="master.page.specialist.listAll" /></a></li>
     <li><a href="offer/list-not-finish.do"><spring:message code="master.page.offer.listNotFinished" /></a></li>
    </security:authorize>


   </ul>
  </div>

 </div>
</nav>

<div>
 <img src="images/logo.png" alt="Acme-Health Co., Inc." />
</div>

<div>
 <a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

 --%>