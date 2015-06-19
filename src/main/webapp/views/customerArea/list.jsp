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
  <security:authorize access="hasRole('ADMINISTRATOR')">
   <! -- 1st ROW OF PANELS -->
   <div class="row">
    <!-- ADMINISTRATOR -->


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
   <! --/END 1ST ROW OF PANELS -->
  </security:authorize>
  <! -- 2ND ROW OF PANELS -->
  <security:authorize access="hasRole('ADMINISTRATOR')">
  <div class="row">
   <! -- TODO PANEL -->
  <div class="col-lg-4 col-md-4 col-sm-4 mb">
     <div class="product-panel-2 pn">
      <a href="dashboard/administrator/dashboard.do"> <img src="assets/img/product.jpg" width="300" alt="">
      </a>
     </div>
    </div>
   <! --/col-md-4 -->
  </div>
  </security:authorize>
 </div>