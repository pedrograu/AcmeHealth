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

 <form:form action="${requestURI}" modelAttribute="freeDayForm">


	<form:hidden path="id" />
	<form:hidden path="version" />


	<script>
	$(function(){
		$('#startMoment').datepicker({
			dateFormat : 'dd/mm/yy',
		});
	});
	</script>

	
	
	<script>
	$(function(){
		$('#finishMoment').datepicker({
			dateFormat : 'dd/mm/yy',
		});
	});
	</script>

	
	<acme:textbox code="freeDay.startMoment" path="startMoment" />
	<acme:textbox code="freeDay.finishMoment" path="finishMoment" />
	<acme:textarea code="freeDay.description" path="description" />


	<acme:submit code="comment.save" name="save" />
	<acme:cancel url="freeDay/specialist/list-own.do" code="comment.cancel" />


</form:form>  

