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


<display:table name="specialists" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag" keepStatus="true">

	<security:authorize access="isAnonymous()">
		<display:column>
			<a href="profile/details.do?specialistId=${row.id}"><spring:message
					code="specialist.profile" /></a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="profile/administrator/details.do?specialistId=${row.id}"><spring:message
					code="specialist.profile" /></a>
		</display:column>
<%-- 
		<display:column>
			<a href="specialist/administrator/delete.do?specialistId=${row.id}"><spring:message
					code="administrator.deleteSpecialist" /></a>
		</display:column> --%>
	</security:authorize>
	<security:authorize access="hasRole('PATIENT')">
		<display:column>
			<a href="profile/patient/details.do?specialistId=${row.id}"><spring:message
					code="specialist.profile" /></a>
		</display:column>
		<display:column>
			<a href="timetable/patient/list.do?specialistId=${row.id}"><spring:message
					code="specialist.timetables" /></a>
		</display:column>
	</security:authorize>

	<spring:message code="specialist.name" var="name" />
	<display:column property="name" title="${name}" sortable="${true}" />

	<spring:message code="specialist.surname" var="surname" />
	<display:column property="surname" title="${surname}"
		sortable="${true}" />

	<spring:message code="specialist.specialty" var="specialty" />
	<display:column property="specialty.name" title="${specialty}"
		sortable="${true}" />


</display:table>

