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


<jstl:if test="${registerPatient==true}">

	<form:form action="${requestURI}" modelAttribute="${actor}"
		enctype="multipart/form-data">

		<div class="col-md-6" style="margin-bottom: 100px;"><br />


			<acme:textbox code="register.username" path="username" />
			<acme:password code="register.password" path="password" />
			<acme:password code="register.second.password" path="secondPassword" />


			<acme:textbox code="register.name" path="name" value="<%=request.getParameter(\"name\")%>"/>
			<acme:textbox code="register.surname" path="surname" value="<%=request.getParameter(\"surname\")%>"/>
			<acme:textbox code="register.emailAddress" path="emailAddress" value="<%=request.getParameter(\"email\")%>"/>

			<acme:hidden  value="<%=request.getParameter(\"token\")%>" path ="token"/>
<%-- 			<acme:textbox value="<%=request.getParameter(\"token\")%>"
				readonly="true" code="register.token" path="token" /> --%>


			<acme:textbox code="register.address" path="address" value="<%=request.getParameter(\"address\")%>"/>

			<acme:textbox code="register.phone" path="phone" value="<%=request.getParameter(\"phone\")%>"/>
			<%-- <acme:textbox code = "register.creationMoment" path ="creationMoment"/> --%>

			<acme:select items="${specialists}" itemLabel="name"
				code="register.specialist" path="specialist" />


		</div>
		<div class="col-md-6">

			<fieldset>
				<legend>
					<spring:message code="register.creditCard.name" />
				</legend>

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
			<fieldset>
				<legend>
					<spring:message code="register.image" />
				</legend>

				<%-- 				<form:label path="image">
					<spring:message code="register.image" />
				</form:label> --%>
				<form:input path="image" type="file" />
				<form:errors cssClass="error" path="image" />
			</fieldset>

			<br /><br />

			<acme:checkbox path="available" url="laws/list.do"
				code="register.text" />


			<acme:submit code="register.save" name="save" />

			<acme:cancel url="welcome/index.do" code="register.cancel" />


		</div>
	</form:form>

</jstl:if>

<jstl:if test="${registerSpecialist==true}">


	<form:form action="${requestURI}" modelAttribute="${actor}">

		<div class="col-md-6  col-md-offset-3">



			<acme:textbox code="register.username" path="username" />
			<acme:password code="register.password" path="password" />
			<acme:password code="register.second.password" path="secondPassword" />


			<acme:textbox code="register.name" path="name" />
			<acme:textbox code="register.surname" path="surname" />
			<acme:textbox code="register.emailAddress" path="emailAddress" />


			<acme:select items="${specialtys}" itemLabel="name"
				code="register.specialty" path="specialty" />



			<acme:checkbox path="available" url="laws/list.do"
				code="register.text" />


			<acme:submit code="register.save" name="save" />

			<acme:cancel url="welcome/index.do" code="register.cancel" />

		</div>
	</form:form>

</jstl:if>
