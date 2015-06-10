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

<div class="col-md-7  col-md-offset-2" style="margin-bottom: 100px;">
	<form:form action="${requestURI}" modelAttribute="${actor}">

		<acme:textbox code="register.username" path="username" />
		<acme:password code="register.password" path="password" />
		<acme:password code="register.second.password" path="secondPassword" />


		<acme:textbox code="register.name" path="name" />
		<acme:textbox code="register.surname" path="surname" />
		<acme:textbox code="register.emailAddress" path="emailAddress" />

		<jstl:if test="${registerPatient==true}">


			<acme:textbox code="register.address" path="address" />

			<acme:textbox code="register.phone" path="phone" />
			<%-- <acme:textbox code = "register.creationMoment" path ="creationMoment"/> --%>

			<acme:select items="${specialists}" itemLabel="name"
				code="register.specialist" path="specialist" />



			<fieldset>
				<legend>Credit Card</legend>

				<acme:textbox code="register.creditCard.holderName"
					path="creditCard.holderName" />
				<acme:textbox code="register.creditCard.brandName"
					path="creditCard.brandName" />
				<acme:textbox code="register.creditCard.number"
					path="creditCard.number" />
				<acme:textbox code="register.creditCard.expirationMonth"
					path="creditCard.expirationMonth" />
				<acme:textbox code="register.creditCard.expirationYear"
					path="creditCard.expirationYear" />
				<acme:textbox code="register.creditCard.cVVcode"
					path="creditCard.cVVcode" />


			</fieldset>

		</jstl:if>

		<jstl:if test="${registerSpecialist==true}">

			<acme:select items="${specialtys}" itemLabel="name"
				code="register.specialty" path="specialty" />
		</jstl:if>



		<acme:checkbox path="available" url="laws/list.do"
			code="register.text" />


		<acme:submit code="register.save" name="save" />

		<acme:cancel url="welcome/index.do" code="register.cancel" />






	</form:form>
</div>

