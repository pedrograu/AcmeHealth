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

<div class="table-responsive">
	<display:table name="specialtys" id="row" requestURI="${requestURI}"
		pagesize="5" class="table table-hover" keepStatus="true">

		<security:authorize access="isAnonymous()">
			<display:column>
				<a href="specialty/detail.do?specialtyId=${row.id}"><spring:message
						code="specialty.edit" /></a>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('PATIENT')">
			<display:column>
				<a href="specialty/patient/detail.do?specialtyId=${row.id}"><spring:message
						code="specialty.edit" /></a>
			</display:column>
		</security:authorize>

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<display:column>
				<a href="specialty/administrator/detail.do?specialtyId=${row.id}"><spring:message
						code="specialty.edit" /></a>
			</display:column>
		</security:authorize>
		<spring:message code="specialty.name" var="name" />
		<display:column property="name" title="${name}" sortable="${true}" />

		<spring:message code="specialty.description" var="description" />
		<display:column property="description" title="${description}"
			sortable="${true}" />



	</display:table>
</div>
