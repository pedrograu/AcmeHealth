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

<div class="col-md-7  col-md-offset-2" style="margin-bottom: 20%;">
	<br />
	<br />

 	<jstl:if test="${isPatient==true}"> 

		<div class="col-lg-12">
			<div class="row row-centered">
				<div class="col-lg-12 col-md-12 col-sm-12 mb">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="centered">
								<b><spring:message code="prescription.details" /></b>
							</h3>
						</div>
						<div class="panel-body">
							<ul>
								<li><b><spring:message code="prescription.title2" /></b> <jstl:out
										value="${prescription.title}"></jstl:out></li>
								<li><b><spring:message
											code="prescription.creationMoment2" /></b> <fmt:formatDate
										value="${prescription.creationMoment}"
										pattern="dd/MM/yyyy HH:mm" /></li>
								<li><b><spring:message code="prescription.price" /></b> <jstl:out
										value="${prescription.price}"></jstl:out></li>
								<li><b><spring:message
											code="prescription.appointment.specialist" /></b> <jstl:out
										value="${prescription.appointment.specialist.surname}, ${prescription.appointment.specialist.name} (${prescription.appointment.specialist.specialty.name})"></jstl:out></li>
								<li><b><spring:message code="prescription.description" /></b>
									<jstl:out value="${prescription.description}"></jstl:out></li>

							</ul>
						</div>
					</div>
				</div>
			</div>
			<security:authorize access="hasRole('PATIENT')">
				<div class="row">
					<a
						href="prescription/patient/print.do?prescriptionId=${prescription.id}">
						<button type="button" class="btn btn-default btn-lg btn-block">
							<spring:message code="prescription.print" />
						</button>
					</a>
				</div>
			</security:authorize>
			
			<security:authorize access="hasRole('SPECIALIST')">
				<div class="row">
					<a
						href="prescription/specialist/print.do?prescriptionId=${prescription.id}">
						<button type="button" class="btn btn-default btn-lg btn-block">
							<spring:message code="prescription.print" />
						</button>
					</a>
				</div>
			</security:authorize>
		</div>

 	</jstl:if> 


	<jstl:if test="${isPatient==false}">

		<form:form action="${requestURI}" modelAttribute="prescription">


			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="appointment" />
			<form:hidden path="creationMoment" />



			<acme:textbox code="prescription.title" path="title" />
			<acme:textarea code="prescription.description" path="description" />
			<acme:textbox code="prescription.price" path="price" />


			<acme:submit code="prescription.save" name="save" />

		</form:form>

	</jstl:if>

</div>