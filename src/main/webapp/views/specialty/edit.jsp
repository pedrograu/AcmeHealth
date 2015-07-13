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



<jstl:if test="${createSpecialty==true}">
	<div class="col-md-7  col-md-offset-2" style="margin-bottom: 20%;">
		<form:form action="${requestURI}" modelAttribute="specialty">


			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="administrator" />
			<form:hidden path="specialists" />


			<acme:textbox code="specialty.name" path="name" />
			<acme:textarea code="specialty.description" path="description" />


			<acme:submit code="specialty.save" name="save" />
			<acme:cancel url="specialty/administrator/list-all.do"
				code="specialty.cancel" />


		</form:form>

	</div>
</jstl:if>

<jstl:if test="${detailsSpecialty==true}">
	<br />
	<br />


	<div class="col-lg-12 ">
		<div class="row row-centered">
			<div class="col-lg-7 col-lg-offset-2 col-md-12 col-sm-12 mb">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="centered">
							<b><spring:message code="specialty.details" /></b>
						</h3>
					</div>
					<div class="panel-body">
						<ul>
							<li><b><spring:message code="specialty.name2" /></b> <jstl:out
									value="${specialty.name}"></jstl:out></li>
							<li><b><spring:message code="specialty.description2" /></b>
								<jstl:out value="${specialty.description}"></jstl:out></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>




	<br />
	<br />
	<h3>
		<spring:message code="specialists.list" />
	</h3>
	<br />
	<div class="table-responsive">
		<display:table name="specialists" id="row" requestURI="${requestURI}"
			pagesize="5" class="table table-hover" keepStatus="true">

			<security:authorize access="isAnonymous()">
				<display:column>
					<a href="profile/details.do?specialistId=${row.id}"><spring:message
							code="specialist.profile" /></a>
				</display:column>
			</security:authorize>

			<security:authorize access="hasRole('PATIENT')">
				<display:column>
					<a href="profile/patient/details.do?specialistId=${row.id}"><spring:message
							code="specialist.profile" /></a>
				</display:column>
			</security:authorize>

			<spring:message code="specialist.name" var="name" />
			<display:column property="name" title="${name}" sortable="${true}" />

			<spring:message code="specialist.surname" var="surname" />
			<display:column property="surname" title="${surname}"
				sortable="${true}" />

			<spring:message code="specialist.specialty" var="specialty" />
			<display:column property="specialty.name" title="${specialty}"
				sortable="${true}" />


		</display:table>
	</div>

</jstl:if>
