
<%--
 * login.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="col-md-5  col-md-offset-3">
	<form:form action="j_spring_security_check"
		modelAttribute="credentials">

		<%-- <form:label path="username">
			<spring:message code="security.username" />
		</form:label>
		<form:input path="username" /> --%>
		<acme:textbox code="security.username" path="username" />
		<form:errors class="error" path="username" />
		<br />

		<%-- <form:label path="password">
			<spring:message code="security.password" />
		</form:label>
		<form:password path="password" /> --%>
		<acme:password code="security.password" path="password" />
		<form:errors class="error" path="password" />
		<br />

		<jstl:if test="${showError == true}">
			<div class="error">
				<spring:message code="security.login.failed" />
			</div>
		</jstl:if>

		<%-- <input type="submit" value="<spring:message code="security.login" />" /> --%>
		<div class="pull-right saveimic">
			<button type="submit" class="btn btn-primary  btn-lg pull-right"
				onclick="${onclick}">
				<spring:message code="security.login" />
			</button>
		</div>

	</form:form>
</div>