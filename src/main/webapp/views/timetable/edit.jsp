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

<form:form action="${requestURI}" modelAttribute="timetableForm">


	<form:hidden path="id" />
	<form:hidden path="version" />




	<acme:textbox code="timetable.startShift" path="startShift" />
	<acme:textbox code="timetable.endShift" path="endShift" />
	<%-- <!-- Del tag -->
		<form:label path="day">
			<spring:message code="timetable.day" />
		</form:label>
		<form:select id="${id}" path="${path}" onchange="${onchange}">
			<form:option value="0" label="----" />
			<form:options items="${items}" itemValue="id"
				itemLabel="${itemLabel}" />
		</form:select>
		<form:errors path="${path}" cssClass="error" />
	 --%>
	
	<form:label path="day">
		<spring:message code="timetable.day" />
	</form:label>
 	<form:select path="day">
		<form:option label="1"  value="1" >
			<spring:message code="timetable.day.sunday"/>
		</form:option>
		<form:option label="2"  value="2" >
			<spring:message code="timetable.day.monday"/>
		</form:option>
		<form:option label="3"  value="3" >
			<spring:message code="timetable.day.tuesday"/>
		</form:option>
		<form:option label="4"  value="4" >
			<spring:message code="timetable.day.wednesday"/>
		</form:option>
		<form:option label="5"  value="5" >
			<spring:message code="timetable.day.thursday"/>
		</form:option>
		<form:option label="6"  value="6" >
			<spring:message code="timetable.day.friday"/>
		</form:option>
		<form:option label="7"  value="7" >
			<spring:message code="timetable.day.saturday"/>
		</form:option>
		
		
		
		<%-- <form:option label="<spring:message code="timetable.day.monday" />" value="1" />
		<form:option label="<spring:message code="timetable.day.tuesday" />" value="2" />
		<form:option label="<spring:message code="timetable.day.wednesday" />" value="3" />
		<form:option label="<spring:message code="timetable.day.thursday" />" value="4" />
		<form:option label="<spring:message code="timetable.day.friday" />" value="5" />
		<form:option label="<spring:message code="timetable.day.saturday" />" value="6" />
		 --%>
		<%-- <form:options items="${days}" itemLabel="title" itemValue="id" /> --%>
	</form:select>
	<form:errors cssClass="error" path="day" />
	<br /> 
	<%-- 
	<!-- Del curriculim -->
	<form:label path="curriculum">
		<spring:message code="application.curriculum" />
	</form:label>
 	<form:select path="curriculum">
		<form:option label="----" value="0" />
		<form:options items="${curricula}" itemLabel="title" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="curriculum" />
	<br />  --%>
	

	<acme:submit code="timetable.save" name="save" />
	<acme:cancel url="timetable/specialist/list-own.do"	code="comment.cancel" />


</form:form>

