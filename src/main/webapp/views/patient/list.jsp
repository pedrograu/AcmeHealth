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
	<display:table name="patients" id="row" requestURI="${requestURI}"
		pagesize="5" class="table table-hover" keepStatus="true">

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<jstl:if test="${row.enableMessage == true }">
				<display:column>
					<a href="patient/administrator/ban.do?patientId=${row.id}"><spring:message
							code="patient.ban" /></a>
				</display:column>
			</jstl:if>
			<jstl:if test="${row.enableMessage == false }">
				<display:column>
					<a href="patient/administrator/ban.do?patientId=${row.id}"><spring:message
							code="patient.enable" /></a>
				</display:column>
			</jstl:if>
		</security:authorize>

		<spring:message code="patient.name" var="name" />
		<display:column property="name" title="${name}" sortable="${true}" />

		<spring:message code="patient.surname" var="surname" />
		<display:column property="surname" title="${surname}"
			sortable="${true}" />

		<jstl:if test="${isSpecialist==true}">
			<display:column>
				<a href="prescription/specialist/list.do?patientId=${row.id}"><spring:message
						code="patient.prescription" /></a>
			</display:column>
		</jstl:if>

		<display:column>
			<a
				href="medicalHistory/specialist/detail.do?medicalHistoryId=${row.medicalHistory.id}"><spring:message
					code="patient.medicalHistory" /></a>
		</display:column>

	</display:table>
</div>
