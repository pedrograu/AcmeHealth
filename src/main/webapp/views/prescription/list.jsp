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
	<display:table name="prescriptions" id="row" requestURI="${requestURI}"
		pagesize="5" class="table table-hover" keepStatus="true">

		<jstl:if test="${isPatient==true}">
			<display:column>
				<a href="prescription/patient/details.do?prescriptionId=${row.id}"><spring:message
						code="prescription.details" /></a>
			</display:column>
		</jstl:if>
		<security:authorize access="hasRole('SPECIALIST')">
			<display:column>
				<a href="prescription/specialist/details.do?prescriptionId=${row.id}"><spring:message
						code="prescription.details" /></a>
			</display:column>
		</security:authorize>

		<spring:message code="prescription.title" var="title" />
		<display:column property="title" title="${title}" sortable="${true}" />

		<spring:message code="prescription.creationMoment"
			var="creationMoment" />
		<display:column property="creationMoment" title="${creationMoment}"
			sortable="${true}" format="{0,date,dd/MM/yyyy HH:mm}" />


	</display:table>
</div>
