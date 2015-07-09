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
	<br /> <br />

	<jstl:if test="${detailsMedicalHistory==true}">



		<div class="col-lg-12">
			<div class="row row-centered">
				<div class="col-lg-12 col-md-12 col-sm-12 mb">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="centered">
								<b><spring:message code="medicalHistory.details" /></b>
							</h3>
						</div>
						<div class="panel-body">
							<ul>

								<li><b><spring:message code="medicalHistory.bloodGroup2" /></b>
									<jstl:out value="${medicalHistory.bloodGroup}"></jstl:out></li>
								<li><b><spring:message code="medicalHistory.allergy2" /></b>
									<jstl:out value="${medicalHistory.allergy}"></jstl:out></li>
								<li><b><spring:message code="medicalHistory.incompatibilities2" /></b> <jstl:out
										value="${medicalHistory.incompatibilities}"></jstl:out></li>
								<li><b><spring:message code="medicalHistory.note2" /></b> <jstl:out
										value="${medicalHistory.note}"></jstl:out></li>

							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<a
					href="medicalHistory/specialist/edit.do?medicalHistoryId=${medicalHistory.id}">
					<button type="button" class="btn btn-default btn-lg btn-block">
						<spring:message code="specialist.medicalHistory.edit" />
					</button>
				</a>
			</div>
		</div>





	</jstl:if>

	<jstl:if test="${detailsMedicalHistory==false}">
		<form:form action="${requestURI}" modelAttribute="medicalHistory">


			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="appointments" />
			<form:hidden path="patient" />



			<acme:textbox code="medicalHistory.bloodGroup" path="bloodGroup" />
			<acme:textbox code="medicalHistory.allergy" path="allergy" />
			<acme:textarea code="medicalHistory.incompatibilities"
				path="incompatibilities" />
			<acme:textarea code="medicalHistory.note" path="note" />


			<acme:submit code="medicalHistory.save" name="save" />
			<acme:cancel
				url="medicalHistory/specialist/detail.do?medicalHistoryId=+${medicalHistory.id}"
				code="specialty.cancel" />


		</form:form>

	</jstl:if>
</div>





