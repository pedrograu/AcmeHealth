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
	<display:table name="appointments" id="row" requestURI="${requestURI}"
		pagesize="5" class="table table-hover" keepStatus="true">


		<security:authorize access="hasRole('SPECIALIST')">
			<jstl:if test="${isNotFinish==true}">
				<display:column>
					<a href="appointment/specialist/edit.do?appointmentId=${row.id}"><spring:message
							code="appointment.attend" /></a>
				</display:column>
				<display:column>
					<a href="message/customer/cancel.do?appointmentId=${row.id}"><spring:message
							code="appointment.cancel" /></a>
				</display:column>
			</jstl:if>
		</security:authorize>



		<spring:message code="appointment.startMoment" var="startMoment" />
		<display:column property="startMoment" title="${startMoment}"
			sortable="${true}" format="{0,date,dd/MM/yyyy HH:mm}" />

		<security:authorize access="hasRole('PATIENT')">

			<spring:message code="appointment.specialist" var="specialist" />
			<display:column property="specialist.name" title="${specialist}"
				sortable="${true}" />

			<spring:message code="appointment.specialty" var="specialty" />
			<display:column property="specialist.specialty.name"
				title="${specialty}" sortable="${true}" />
		</security:authorize>

		<security:authorize access="hasRole('SPECIALIST')">

			<spring:message code="appointment.patient.name" var="patient" />
			<display:column property="patient.name" title="${patient}"
				sortable="${true}" />

			<spring:message code="appointment.patient.surname" var="patient" />
			<display:column property="patient.surname" title="${patient}"
				sortable="${true}" />
				
		</security:authorize>


	</display:table>
</div>
