<%--
 * list.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
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

<div class="table-responsive">
	<ul>
		<br/>
		<h3><li><spring:message code="dashboard.numberOfPatients" />
			<jstl:out value="${numberOfPatients}"></jstl:out></li></h3>

		<br/>
		<h3><li><spring:message code="dashboard.bestSpecialist" /></li></h3>
		<display:table name="bestSpecialist" id="row"
			requestURI="${requestURI}" pagesize="5"
			class="table table-hover table-condensed" keepStatus="true">

			<spring:message code="dashboard.name" var="name" />
			<display:column property="specialist.name" title="${name}"
				sortable="${true}" />

			<spring:message code="dashboard.surname" var="surname" />
			<display:column property="specialist.surname" title="${surname}"
				sortable="${true}" />

			<spring:message code="dashboard.specialty.name" var="name" />
			<display:column property="specialist.specialty.name" title="${name}"
				sortable="${true}" />



		</display:table>
		<br/>
		<li><h3><spring:message
					code="dashboard.specialistWithMoreAppointment" /></h3></li>
		<display:table name="specialistWithMoreAppointment" id="row"
			requestURI="${requestURI}" pagesize="5"
			class="table table-hover table-condensed" keepStatus="true">

			<spring:message code="dashboard.name" var="name" />
			<display:column property="name" title="${name}" sortable="${true}" />

			<spring:message code="dashboard.surname" var="surname" />
			<display:column property="surname" title="${surname}"
				sortable="${true}" />

			<spring:message code="dashboard.specialty.name" var="name" />
			<display:column property="specialty.name" title="${name}"
				sortable="${true}" />

		</display:table>
		<br/>
		<li><h3><spring:message
					code="dashboard.patientWithMoreAppointment" /></h3></li>
		<display:table name="patientWithMoreAppointment" id="row"
			requestURI="${requestURI}" pagesize="5"
			class="table table-hover table-condensed" keepStatus="true">

			<spring:message code="dashboard.name" var="name" />
			<display:column property="name" title="${name}" sortable="${true}" />

			<spring:message code="dashboard.surname" var="surname" />
			<display:column property="surname" title="${surname}"
				sortable="${true}" />

			<spring:message code="dashboard.phone" var="phone" />
			<display:column property="phone" title="${phone}" sortable="${true}" />

		</display:table>
		<br/>
		<li><h3><spring:message
					code="dashboard.patientWithMoreSpending" /></h3></li>
		<display:table name="patientWithMoreSpending" id="row"
			requestURI="${requestURI}" pagesize="5"
			class="table table-hover table-condensed" keepStatus="true">

			<spring:message code="dashboard.name" var="name" />
			<display:column property="name" title="${name}" sortable="${true}" />

			<spring:message code="dashboard.surname" var="surname" />
			<display:column property="surname" title="${surname}"
				sortable="${true}" />

			<spring:message code="dashboard.phone" var="phone" />
			<display:column property="phone" title="${phone}" sortable="${true}" />

		</display:table>
		<br/>
		<li><h3><spring:message code="dashboard.patientLastYear" /></h3></li>
		<display:table name="patientLastYear" id="row"
			requestURI="${requestURI}" pagesize="5"
			class="table table-hover table-condensed" keepStatus="true">

			<spring:message code="dashboard.name" var="name" />
			<display:column property="name" title="${name}" sortable="${true}" />

			<spring:message code="dashboard.surname" var="surname" />
			<display:column property="surname" title="${surname}"
				sortable="${true}" />

			<spring:message code="dashboard.phone" var="phone" />
			<display:column property="phone" title="${phone}" sortable="${true}" />

			<spring:message code="dashboard.creationMoment" var="creationMoment" />
			<display:column property="creationMoment" title="${creationMoment}"
				sortable="${true}" format="{0,date,dd/MM/yyyy HH:mm}" />

		</display:table>


	</ul>
</div>