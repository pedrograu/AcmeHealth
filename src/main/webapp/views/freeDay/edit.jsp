<%--
 * action-2.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">


<div class="table-responsive">
 <display:table name="freeDays" id="row" requestURI="${requestURI}" pagesize="5" class="table table-hover"
  keepStatus="true">




  <spring:message code="freeDay.startMoment" var="startMoment" />
  <display:column property="startMoment" title="${startMoment}" sortable="${true}" format="{0,date,dd/MM/yyyy}" />

  <spring:message code="freeDay.finishMoment" var="finishMoment" />
  <display:column property="finishMoment" title="${finishMoment}" sortable="${true}" format="{0,date,dd/MM/yyyy}" />

  <spring:message code="freeDay.description" var="description" />
  <display:column property="description" title="${description}" sortable="${true}" />
  
  <display:column>
   <a href="freeDay/specialist/delete.do?freeDayId=${row.id}"><button class="btn btn-danger btn-xs">
     <i class="fa fa-trash-o "></i>
    </button></a>
  </display:column>

 </display:table>
</div>

	<h3>
		<spring:message code="freeDay.createFreeday" />
	</h3>


<div class="col-md-7  col-md-offset-2" style="margin-bottom: 100px;">
	<form:form action="${requestURI}" modelAttribute="freeDayForm">


		<form:hidden path="id" />
		<form:hidden path="version" />



		<acme:textbox code="freeDay.startMoment" path="startMoment" placeholder="dd/MM/yyyy 00:01" />
		<acme:textbox code="freeDay.finishMoment" path="finishMoment" placeholder="dd/MM/yyyy 23:59" />
		<acme:textarea code="freeDay.description" path="description" />


		<acme:submit code="comment.save" name="save" />


	</form:form>
</div>
