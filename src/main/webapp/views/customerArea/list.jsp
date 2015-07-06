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

<div id="tf-services" class="text-center">
	<div class="row mt">
		<div class="col-lg-12">
			<!-- ADMINISTRATOR -->
			<security:authorize access="hasRole('ADMINISTRATOR')">
				<!-- 1st ROW OF PANELS -->
				<div class="row">

					<a href="patient/administrator/listPatientsNoDischarged.do">Lista de usuarios a la espera de ser dados de alta</a>

					<!-- Register Specialist -->
					<a href="register/specialist/edit.do">
						<div class="col-lg-4 col-md-4 col-sm-4 mb service">
							<i class="fa fa-user-md"></i>
							<h4>
								<strong><spring:message
										code="customerArea.administrator.register.specialist" /></strong>
							</h4>
						</div>
					</a>


					<!-- Create specialty -->

					<a href="specialty/administrator/create.do">
						<div class="col-lg-4 col-md-4 col-sm-4 mb service">
							<i class="fa fa-medkit"></i>
							<h4>
								<strong><spring:message
										code="customerArea.administrator.create.specialty" /></strong>
							</h4>
						</div>
					</a>

					<!-- Create offer -->

					<a href="offer/administrator/create.do">
						<div class="col-lg-4 col-md-4 col-sm-4 mb service">
							<i class="fa fa-plus-square"></i>
							<h4>
								<strong><spring:message
										code="customerArea.administrator.create.offer" /></strong>
							</h4>
						</div>
					</a>


					<!-- /col-md-4 -->
				</div>
				<!--/END 1ST ROW OF PANELS-->
			</security:authorize>


			<!--PATIENT-->
			<security:authorize access="hasRole('PATIENT')">
				<!-- 1st ROW OF PANELS -->
				<div class="row">



					<!--Personal Data-->
					<a href="profile/patient/myPersonalDatas.do">
						<div class="col-lg-4 col-md-4 col-sm-4 mb service">

							<i class="fa fa-pencil-square-o"></i>
							<h4>
								<strong><spring:message
										code="customerArea.patient.personal.data" /></strong>
							</h4>

						</div>
					</a>

					<!-- Create Appointment -->

					<a href="appointment/patient/calendar.do">
						<div class="col-lg-4 col-md-4 col-sm-4 mb service">
							<i class="fa fa-calendar"></i>
							<h4>
								<strong><spring:message
										code="customerArea.patient.appointment" /></strong>
							</h4>
						</div>
					</a>
					<!-- /col-md-4 -->


					<!-- /col-md-4 -->
				</div>
				<!--/END 1ST ROW OF PANELS-->
			</security:authorize>


			<!-- SPECIALIST -->
			<security:authorize access="hasRole('SPECIALIST')">
				<!--1st ROW OF PANELS-->
				<div class="row">

					<!-- Profile -->
					<a href="profile/specialist/detail.do">
						<div class="col-lg-4 col-md-4 col-sm-4 mb service">
							<i class="fa fa-user"></i>
							<h4>
								<strong><spring:message
										code="customerArea.specialist.profile" /></strong>
							</h4>
						</div>
					</a>

					<!-- Timetable -->

					<a href="timetable/specialist/create.do">
						<div class="col-lg-4 col-md-4 col-sm-4 mb service">
							<i class="fa fa-table"></i>
							<h4>
								<strong><spring:message
										code="customerArea.specialist.create.timetable" /></strong>
							</h4>
						</div>
					</a>

					<!-- Free day -->

					<a href="freeDay/specialist/create.do">
						<div class="col-lg-4 col-md-4 col-sm-4 mb service">
							<i class="fa fa-plane "></i>
							<h4>
								<strong><spring:message
										code="customerArea.specialist.create.free.day" /></strong>
							</h4>
						</div>
					</a>


					<!-- /col-md-4 -->
				</div>
				<!--/END 1ST ROW OF PANELS-->
			</security:authorize>


			<!-- 2ND ROW OF PANELS -->
			<!-- ADMINISTRATOR -->
			<security:authorize access="hasRole('ADMINISTRATOR')">
				<div class="row">

					<!-- Dashboard-->
					<a href="dashboard/administrator/dashboard.do">
						<div class="col-lg-4 col-md-4 col-sm-4 mb service">
							<i class="fa fa-desktop"></i>
							<h4>

								<strong><spring:message
										code="customerArea.administrator.dashboard" /></strong>
							</h4>
						</div>
					</a>

					<!--/col-md-4-->
				</div>
			</security:authorize>

		</div>
	</div>
</div>
<!--PATIENT-->
<security:authorize access="hasRole('PATIENT')">

	<br />
	<br />
	<h3>
		<spring:message code="customerArea.appointments" />
	</h3>

	<br />

	<!-- TODO PANEL-->
	<div class="table-responsive">
		<display:table name="appointments" id="row" requestURI="${requestURI}"
			pagesize="5" class="table table-hover" keepStatus="true">

			<spring:message code="appointment.startMoment" var="startMoment" />
			<display:column property="startMoment" title="${startMoment}"
				sortable="${true}" format="{0,date,dd/MM/yyyy HH:mm}" />

			<spring:message code="appointment.specialist" var="specialist" />
			<display:column property="specialist.name" title="${specialist}"
				sortable="${true}" />

			<spring:message code="appointment.specialty" var="specialty" />
			<display:column property="specialist.specialty.name"
				title="${specialty}" sortable="${true}" />


		</display:table>
	</div>
</security:authorize>
<!-- SPECIALIST -->
<security:authorize access="hasRole('SPECIALIST')">
	<div class="table-responsive">
		<display:table name="appointments" id="row" requestURI="${requestURI}"
			pagesize="5" class="table table-hover" keepStatus="true">





			<spring:message code="appointment.startMoment" var="startMoment" />
			<display:column property="startMoment" title="${startMoment}"
				sortable="${true}" format="{0,date,dd/MM/yyyy HH:mm}" />

			<spring:message code="appointment.specialist" var="specialist" />
			<display:column property="specialist.name" title="${specialist}"
				sortable="${true}" />

			<spring:message code="appointment.specialty" var="specialty" />
			<display:column property="specialist.specialty.name"
				title="${specialty}" sortable="${true}" />

			<security:authorize access="hasRole('SPECIALIST')">
				<display:column>
					<a href="appointment/specialist/edit.do?appointmentId=${row.id}">
						<button class="btn btn-primary btn-xs">
							<i class="fa fa-pencil"></i>
						</button>
					</a>
				</display:column>
				<display:column>
					<a href="message/customer/cancel.do?appointmentId=${row.id}">
						<button class="btn btn-danger btn-xs">
							<i class="fa fa-trash-o "></i>
						</button>
					</a>
				</display:column>

			</security:authorize>


		</display:table>
	</div>
</security:authorize>