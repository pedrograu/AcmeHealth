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
	<display:table name="listPatientsNotAccepted" id="row" requestURI="${requestURI}" pagesize="5" class="table table-hover"
		keepStatus="true">


		<display:column>
			<a href="patient/administrator/discharge.do?patientNotAcceptedId=${row.id}"><spring:message
					code="patientNotAccepted.discharge" /></a>
		</display:column>


		<spring:message code="patientNotAccepted.name" var="name" />
		<display:column property="name" title="${name}" sortable="${true}" />

		<spring:message code="patientNotAccepted.surname" var="surname" />
		<display:column property="surname" title="${surname}" sortable="${true}" />

		<spring:message code="patientNotAccepted.address" var="address" />
		<display:column property="address" title="${address}" sortable="${true}" />

		<spring:message code="patientNotAccepted.emailAddress" var="emailAddress" />
		<display:column property="emailAddress" title="${emailAddress}" sortable="${true}" />
		
		<spring:message code="patientNotAccepted.phone" var="phone" />
		<display:column property="phone" title="${phone}" sortable="${true}" />


	</display:table>
</div>
