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
   <!-- settings start -->
   <!-- settings end -->
   <!-- inbox dropdown start-->
   <!-- 
   <li id="header_inbox_bar" class="dropdown"><a data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
     <i class="fa fa-envelope-o"></i>
   </a>
    <ul class="dropdown-menu extended inbox">
     <div class="notify-arrow notify-arrow-green"></div>
     <li>
      <p class="green">You have 5 new messages</p>
     </li>
     <li><a href="index.html#"> <span class="photo"><img alt="avatar" src="assets/img/ui-zac.jpg"></span>
       <span class="subject"> <span class="from">Zac Snider</span> <span class="time">Just now</span>
      </span> <span class="message"> Hi mate, how is everything? </span>
     </a></li>
     <li><a href="index.html#"> <span class="photo"><img alt="avatar" src="assets/img/ui-divya.jpg"></span>
       <span class="subject"> <span class="from">Divya Manian</span> <span class="time">40 mins.</span>
      </span> <span class="message"> Hi, I need your help with this. </span>
     </a></li>
     <li><a href="index.html#"> <span class="photo"><img alt="avatar" src="assets/img/ui-danro.jpg"></span>
       <span class="subject"> <span class="from">Dan Rogers</span> <span class="time">2 hrs.</span>
      </span> <span class="message"> Love your new Dashboard. </span>
     </a></li>
     <li><a href="index.html#"> <span class="photo"><img alt="avatar" src="assets/img/ui-sherman.jpg"></span>
       <span class="subject"> <span class="from">Dj Sherman</span> <span class="time">4 hrs.</span>
      </span> <span class="message"> Please, answer asap. </span>
     </a></li>
     <li><a href="index.html#">See all messages</a></li>
    </ul></li> -->
   <!-- inbox dropdown end -->

  </ul>
  <!--  notification end -->
   <b><a href="?language=en" style="color: white;">en</a> | <a href="?language=es" style="color: white;">es</a> </b>
  
 </div>





 <div class="top-menu">
 
  <ul class="nav pull-right top-menu">
  
   <!-- ANONYMOUS -->
   <security:authorize access="isAnonymous()">
    <%--  <li><a class="logout" href="security/login.do"><spring:message code="master.page.login" /></a></li>
    <li><a class="logout" href="register/patient/edit.do"><spring:message code="master.page.register.patient" /></a></li> --%>
    <li style="margin-top: 15px; margin-right: 15px;">
     <div class="btn-group ">
      <button type="button" class="btn btn-theme dropdown-toggle" data-toggle="dropdown">
       <i class="fa fa-sign-out"></i> <span class="caret"></span>
      </button>
      <ul class="dropdown-menu" role="menu">
       <li><a href="security/login.do"><spring:message code="master.page.login" /></a></li>
       <li><a href="register/patient/mutua.do"><spring:message code="master.page.register.patient" /></a></li>
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

