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



<jstl:if test="${createSpecialty==true}">

	<form:form action="${requestURI}" modelAttribute="specialty">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="administrator" />
		<form:hidden path="specialists" />


		<acme:textbox code="specialty.name" path="name" />
		<acme:textarea code="specialty.description" path="description" />


		<acme:submit code="specialty.save" name="save" />
		<acme:cancel url="specialty/administrator/list-all.do"
			code="specialty.cancel" />


	</form:form>


</jstl:if>

<jstl:if test="${detailsSpecialty==true}">

	<b><spring:message code="specialty.details" /></b>
	<br />

	<ul>
		<li><b><spring:message code="specialty.name" /></b> <jstl:out
				value="${specialty.name}"></jstl:out></li>
		<li><b><spring:message code="specialty.description" /></b> <jstl:out
				value="${specialty.description}"></jstl:out></li>
	</ul>

	<b><spring:message code="specialists.list" /></b>
	<br />

	<display:table name="specialists" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag" keepStatus="true">

		<security:authorize access="isAnonymous()">
			<display:column>
				<a href="profile/details.do?specialistId=${row.id}"><spring:message
						code="specialist.profile" /></a>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('PATIENT')">
			<display:column>
				<a href="profile/pacient/details.do?specialistId=${row.id}"><spring:message
						code="specialist.profile" /></a>
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


</jstl:if>


