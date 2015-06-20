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
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">



<jstl:if test="${isSpecialist==false}">

	<jstl:if test="${existAppointment==true}">
		<b><spring:message code="appointment.message2" /></b>
	</jstl:if>

	<jstl:if test="${existAppointment==false}">

		<form:form action="${requestURI}" modelAttribute="appointmentForm">


			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="offer" />
			<%-- 
	<form:hidden path="medicalHistory" />
	<form:hidden path="prescriptions" />
	<form:hidden path="patient" />
	<form:hidden path="timetable" /> --%>



			<jstl:if test="${create==false}">

				<jstl:if test="${hayHorasDisponibles==true}">
					<form:select hidden="true" path="startMoment"
						items="${listaDeFechas}"></form:select>

					<b><spring:message code="appointment.confirmAppointment" />${fecha}
						<spring:message code="appointment.at" /> ${hora} <spring:message
							code="appointment.hours" />? </b>
					<br />

					<acme:submit code="appointment.confirm" name="save2" />

					<jstl:if test="${isOffer==false}">
						<acme:cancel url="appointment/patient/calendar.do"
							code="appointment.cancel" />
					</jstl:if>

					<jstl:if test="${isOffer==true}">
						<acme:cancel
							url="appointment/patient/calendar2.do?offerId=${appointmentForm.offer.id}"
							code="appointment.cancel" />
					</jstl:if>

				</jstl:if>

				<jstl:if test="${hayHorasDisponibles==false}">
					<br />
					<b><spring:message code="appointment.message" /></b>
				</jstl:if>

			</jstl:if>


		</form:form>


		<%-- 	<jstl:if test="${isOffer==false}">
		<br/>
		
		<a href="appointment/patient/calendar.do"> <spring:message code="appointment.selectOther" /></a>
	</jstl:if>
	
	<jstl:if test="${isOffer==true}">
		<br/>
		<a href="appointment/patient/calendar2.do?offerId=${appointmentForm.offer.id}"> <spring:message code="appointment.selectOther" /></a>
	</jstl:if> --%>

	</jstl:if>

</jstl:if>




<jstl:if test="${isSpecialist2==true}">

	<jstl:if test="${existAppointment==true}">
		<b><spring:message code="appointment.message2" /></b>
	</jstl:if>

	<jstl:if test="${existAppointment==false}">

		<form:form action="${requestURI}" modelAttribute="appointmentForm">


			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="patient" />
			<form:hidden path="specialist" />

			<%-- 
	<form:hidden path="medicalHistory" />
	<form:hidden path="prescriptions" />
	<form:hidden path="patient" />
	<form:hidden path="timetable" /> --%>

			<jstl:if test="${create==false}">

				<jstl:if test="${hayHorasDisponibles==true}">

					<form:select hidden="true" path="startMoment"
						items="${listaDeFechas}"></form:select>

					<b><spring:message code="appointment.confirmAppointment" />${fecha}
						<spring:message code="appointment.at" /> ${hora} <spring:message
							code="appointment.hours" />? </b>
					<br />

					<acme:submit code="appointment.confirm" name="save2" />

					<acme:cancel
						url="appointment/specialist/calendar.do?patientId=${appointmentForm.patient.id}&specialistId=${appointmentForm.specialist.id}"
						code="appointment.cancel" />
				</jstl:if>

				<jstl:if test="${hayHorasDisponibles==false}">
					<br />
					<b><spring:message code="appointment.message" /></b>
				</jstl:if>

			</jstl:if>


		</form:form>


	</jstl:if>


</jstl:if>



<jstl:if test="${isSpecialist==true}">


	<form:form action="${requestURI}" modelAttribute="appointmentForm">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<%-- 	<form:hidden path="offer" /> 
	<form:hidden path="medicalHistory" />
	<form:hidden path="prescriptions" />
	<form:hidden path="patient" />
	<form:hidden path="timetable" />
	<form:hidden path="startMoment" /> --%>


		<acme:textbox code="appointment.purpose" path="purpose" />
		<acme:textarea code="appointment.result" path="result" />
		<acme:checkbox code="appointment.isFinish" path="isFinish" />

		<acme:submit code="appointment.save" name="save" />

	</form:form>

	<br />
	<b><a
		href="medicalHistory/specialist/detail.do?medicalHistoryId=${appointment.medicalHistory.id}">
			<spring:message code="appointment.medicalHistory" />
	</a></b>
	<br />
	<b><a
		href="prescription/specialist/create.do?appointmentId=${appointment.id}">
			<spring:message code="appointment.prescription" />
	</a></b>
	<br />
	<b><a
		href="appointment/specialist/select.do?patientId=${appointment.patient.id}">
			<spring:message code="appointment.createAppointment" />
	</a></b>

</jstl:if>

