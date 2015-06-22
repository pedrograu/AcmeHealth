<%--
 * list.jsp
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

<div class="row mt">
 <div class="col-lg-12">
  <!-- ADMINISTRATOR -->
  <security:authorize access="hasRole('ADMINISTRATOR')">
   <!-- 1st ROW OF PANELS -->
   <div class="row">



    <!-- TWITTER PANEL -->
    <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="register/specialist/edit.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>

    <!-- /col-md-4 -->

    <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="specialty/administrator/create.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>
    <!-- /col-md-4 -->

    <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="offer/administrator/create.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>
    <!-- /col-md-4 -->
   </div>
   <!--/END 1ST ROW OF PANELS-->
  </security:authorize>


  <!--PATIENT-->
  <security:authorize access="hasRole('PATIENT')">
   <!-- 1st ROW OF PANELS -->
   <div class="row">



    <!--TWITTER PANEL-->
    <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="appointment/patient/calendar.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>

    <!-- /col-md-4 -->

    <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="appointment/patient/calendar.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>
    <!-- /col-md-4 -->


    <!-- /col-md-4 -->
   </div>
   <!--/END 1ST ROW OF PANELS-->
  </security:authorize>


  <!-- SPECIALIST -->
  <security:authorize access="hasRole('SPECIALIST')">
   <!--1st ROW OF PANELS-->
   <div class="row">

    <!-- TWITTER PANEL -->
    <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="profile/specialist/detail.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>

    <!-- /col-md-4 -->

    <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="timetable/specialist/create.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>
    <!-- /col-md-4 -->

    <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="freeDay/specialist/create.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>
    <!-- /col-md-4 -->
   </div>
   <!--/END 1ST ROW OF PANELS-->
  </security:authorize>


  <!-- 2ND ROW OF PANELS -->
  <!-- ADMINISTRATOR -->
  <security:authorize access="hasRole('ADMINISTRATOR')">
   <div class="row">
    <!-- TODO PANEL-->
    <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="dashboard/administrator/dashboard.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>
    <!--/col-md-4-->
   </div>
  </security:authorize>
  <!--PATIENT-->
  <security:authorize access="hasRole('PATIENT')">
   <div class="row">
    <!-- TODO PANEL-->
    <div class="col-lg-12 col-md-12 col-sm-12 mb">
     <div class="table-responsive">
      <display:table name="appointments" id="row" requestURI="${requestURI}" pagesize="5" class="table table-hover"
       keepStatus="true">

       <spring:message code="appointment.startMoment" var="startMoment" />
       <display:column property="startMoment" title="${startMoment}" sortable="${true}"
        format="{0,date,dd/MM/yyyy HH:mm}" />

       <spring:message code="appointment.specialist" var="specialist" />
       <display:column property="timetable.specialist.name" title="${specialist}" sortable="${true}" />

       <spring:message code="appointment.specialty" var="specialty" />
       <display:column property="timetable.specialist.specialty.name" title="${specialty}" sortable="${true}" />


      </display:table>
     </div>
    </div>
    <!--/col-md-4-->
   </div>
  </security:authorize>
  <!-- SPECIALIST -->
  <security:authorize access="hasRole('SPECIALIST')">
   <div class="row">
    <!-- TODO PANEL-->
    <div class="col-lg-12 col-md-12 col-sm-12 mb">
     <div class="table-responsive">
      <display:table name="appointments" id="row" requestURI="${requestURI}" pagesize="5" class="table table-hover"
       keepStatus="true">





       <spring:message code="appointment.startMoment" var="startMoment" />
       <display:column property="startMoment" title="${startMoment}" sortable="${true}"
        format="{0,date,dd/MM/yyyy HH:mm}" />

       <spring:message code="appointment.specialist" var="specialist" />
       <display:column property="timetable.specialist.name" title="${specialist}" sortable="${true}" />

       <spring:message code="appointment.specialty" var="specialty" />
       <display:column property="timetable.specialist.specialty.name" title="${specialty}" sortable="${true}" />
       
       <security:authorize access="hasRole('SPECIALIST')">
        <display:column>
        <a href="appointment/specialist/edit.do?appointmentId=${row.id}">
        <button class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i></button></a>
        </display:column>
        <display:column>
         <a href="message/customer/cancel.do?appointmentId=${row.id}">
         <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o "></i></button>
         </a>
        </display:column>

       </security:authorize>


      </display:table>
     </div>
    </div>
    <!--/col-md-4 -->
   </div>
  </security:authorize>
 </div>
</div>