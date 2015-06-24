<%--
 * footer.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="date" class="java.util.Date" />

<!--footer start-->
<footer class="site-footer">
 <div class="text-center">
<!--     <b><a href="?language=en" style="color: white;">en</a> | <a href="?language=es" style="color: white;">es</a> </b>
    <br> -->
  <b><a href="laws/list.do" style="color: white;">Legal Info / Privacy Policy</a></b> &nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;
  <b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Acme-Health Co., Inc. 
  </b> 
 </div>
</footer>
<!--footer end-->

