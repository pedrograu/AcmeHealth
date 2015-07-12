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
	<jstl:if test="${createOffer==true}">


		<form:form action="${requestURI}" modelAttribute="offer">


			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="administrator" />
			<form:hidden path="creationMoment" />
			<form:hidden path="appointments" />
			<form:hidden path="patients" />
			<form:hidden path="enrollees" />


			<acme:textbox code="offer.startMoment" path="startMoment" />
			<acme:textbox code="offer.finishMoment" path="finishMoment" />
			<acme:textbox code="offer.title" path="title" />
			<acme:textarea code="offer.description" path="description" />
			<acme:textbox code="offer.price" path="price" />
			<acme:textbox code="offer.amountPerson" path="amountPerson" />
			<acme:select items="${specialists}" itemLabel="name"
				code="offer.specialist" path="specialist" />


			<div class="form-group">
				<acme:submit code="offer.save" name="save" />
				<jstl:if test="${offer.id !=0}">
					<acme:submit code="offer.delete" name="delete" />
				</jstl:if>
				<acme:cancel url="welcome/index.do" code="offer.cancel" />
			</div>

		</form:form>

	</jstl:if>


	<jstl:if test="${detailsOffer==true}">
		<div class="col-lg-12">
			<div class="row row-centered">
				<div class="col-lg-12 col-md-12 col-sm-12 mb">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="centered">
								<b><spring:message code="offer.details" /></b>
							</h3>
						</div>
						<div class="panel-body">
							<ul>

								<li><b><spring:message code="offer.startMoment" /></b> <fmt:formatDate
										value="${offer.startMoment}" pattern="dd/MM/yyyy" /></li>
								<li><b><spring:message code="offer.finishMoment" /></b> <fmt:formatDate
										value="${offer.finishMoment}" pattern="dd/MM/yyyy" /></li>
								<li><b><spring:message code="offer.title" /></b> <jstl:out
										value="${offer.title}"></jstl:out></li>
								<li><b><spring:message code="offer.price" /></b> <jstl:out
										value="${offer.price}"></jstl:out></li>
								<li><b><spring:message code="offer.amountPerson" /></b> <jstl:out
										value="${offer.amountPerson}"></jstl:out></li>
								<li><b><spring:message code="offer.enrollees" /></b> <jstl:out
										value="${offer.enrollees}"></jstl:out></li>
								<li><b><spring:message code="offer.description" /></b> <jstl:out
										value="${offer.description}"></jstl:out></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<security:authorize access="hasRole('PATIENT')">
				<div class="row">
					<a href="offer/patient/print.do?offerId=${offer.id}">
						<button type="button" class="btn btn-default btn-lg btn-block">
							<spring:message code="offer.print" />
						</button>
					</a>

				</div>
			</security:authorize>
		</div>
	</jstl:if>
</div>