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

<header class="header black-bg">
 <div class="sidebar-toggle-box">
  <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation" style="color: white;"></div>
 </div>
 <!--logo start-->

 <a href="#" class="logo"><img src="images/Apple-iconnaranja.png"  width="30" style="vertical-align=text-bottom;">    <b>Acme <b style="color:orange">Health</b></b></a>
 <!--logo end-->
 <div class="nav notify-row" id="top_menu">
 
  <!--  notification start -->
  <ul class="nav top-menu">


  </ul>
  <!--  notification end -->

  
 </div>





 <div class="top-menu">
 
  <ul class="nav pull-right top-menu">
  
   <!-- ANONYMOUS -->
   <security:authorize access="isAnonymous()">

    <li style="margin-top: 15px; margin-right: 15px;">
     <div class="btn-group ">
      <button type="button" class="btn btn-theme dropdown-toggle" data-toggle="dropdown">
       <i class="fa fa-sign-out"></i> <span class="caret"></span>
      </button>
      <ul class="dropdown-menu" role="menu" style="left: -180%;">
       <li><a href="security/login.do"><spring:message code="master.page.login" /></a></li>
       <li><a href="register/patient/select.do"><spring:message code="master.page.register.patient" /></a></li>
      </ul>
     </div>
    </li>

   </security:authorize>
   <!-- AUTHENTICATED -->
   <security:authorize access="isAuthenticated()">
    <li><a class="logout" href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
   </security:authorize>
  </ul>
  
 </div>

</header>

