<%--
 * action-1.jsp
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<display:table name="timetables" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag" keepStatus="true">


	<spring:message code="timetable.startShift" var="startShift" />
	<display:column property="startShift" title="${startShift}"
		sortable="${true}" format="{0,date,HH:mm}" />

	<spring:message code="timetable.endShift" var="endShift" />
	<display:column property="endShift" title="${endShift}"
		sortable="${true}" format="{0,date,HH:mm}" />

	<spring:message code="timetable.day" var="day" />
	<display:column  title="${day}" sortable="${false}" >
		<jstl:if test="${row.day == 1 }">
			<spring:message code="timetable.day.sunday" />
		</jstl:if>
		<jstl:if test="${row.day == 2 }">
			<spring:message code="timetable.day.monday" />
		</jstl:if>
		<jstl:if test="${row.day == 3 }">
			<spring:message code="timetable.day.tuesday" />
		</jstl:if>
		<jstl:if test="${row.day == 4 }">
			<spring:message code="timetable.day.wednesday" />
		</jstl:if>
		<jstl:if test="${row.day == 5 }">
			<spring:message code="timetable.day.thursday" />
		</jstl:if>
		<jstl:if test="${row.day == 6 }">
			<spring:message code="timetable.day.friday" />
		</jstl:if>
		<jstl:if test="${row.day == 7 }">
			<spring:message code="timetable.day.saturday" />
		</jstl:if>
	</display:column>
	
	

</display:table>
