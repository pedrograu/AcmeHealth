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


<jstl:if test="${detail==true}">

	<ul>
		<img style="width: 50px; height: 50px;"
			src="profile/patient/showImage.do?patientId=${patient.id}" />

		<li><b><spring:message code="personalData.name" /></b> <jstl:out
				value="${patient.name}"></jstl:out></li>
		<li><b><spring:message code="personalData.surname" /></b> <jstl:out
				value="${patient.surname}"></jstl:out></li>
		<li><b><spring:message code="personalData.emailAddress" /></b> <jstl:out
				value="${patient.emailAddress}"></jstl:out></li>
		<li><b><spring:message code="personalData.address" /></b> <jstl:out
				value="${patient.address}"></jstl:out></li>
		<li><b><spring:message code="personalData.phone" /></b> <jstl:out
				value="${patient.phone}"></jstl:out></li>

		<li><b><spring:message
					code="personalData.creditCard.holderName" /></b> <jstl:out
				value="${patient.creditCard.holderName}"></jstl:out></li>
		<li><b><spring:message
					code="personalData.creditCard.brandName" /></b> <jstl:out
				value="${patient.creditCard.brandName}"></jstl:out></li>
		<li><b><spring:message code="personalData.creditCard.number" /></b>
			<jstl:out value="${patient.creditCard.number}"></jstl:out></li>
		<li><b><spring:message
					code="personalData.creditCard.expirationMonth" /></b> <jstl:out
				value="${patient.creditCard.expirationMonth}"></jstl:out></li>
		<li><b><spring:message
					code="personalData.creditCard.expirationYear" /></b> <jstl:out
				value="${patient.creditCard.expirationYear}"></jstl:out></li>
		<li><b><spring:message code="personalData.creditCard.cVVcode" /></b>
			<jstl:out value="${patient.creditCard.cVVcode}"></jstl:out></li>

	</ul>

	<b><a href="profile/patient/edit.do"><spring:message
				code="personalData.edit" /></a></b>
	<br />
	<b><a href="profile/patient/changePassword.do"><spring:message
				code="personalData.changePassword" /></a></b>
	<br />
	<b><a href="profile/patient/editCreditCard.do"><spring:message
				code="personalData.editCreditCard" /></a></b>


</jstl:if>




<jstl:if test="${detail==false}">
	<div class="col-md-7  col-md-offset-2" style="margin-bottom: 20%;">
		<br /> <br />

		<form:form action="${requestURI}" modelAttribute="patientForm2">

			<div class="col-md-12" style="margin-bottom: 20%;">

				<form:hidden path="id" />
				<form:hidden path="version" />

				<acme:textbox code="personalData.emailAddress" path="emailAddress" />
				<acme:textbox code="personalData.address" path="address" />
				<acme:textbox code="personalData.phone" path="phone" />


				<acme:submit code="personalData.save" name="save" />
				<acme:cancel url="profile/patient/myPersonalDatas.do"
					code="personalData.cancel" />

			</div>

		</form:form>

	</div>
</jstl:if>



<jstl:if test="${changePassword==true}">
	<div class="col-md-7  col-md-offset-2" style="margin-bottom: 20%;">
		<br /> <br />

		<form:form action="${requestURI}" modelAttribute="patientForm3">

			<div class="col-md-12" style="margin-bottom: 20%;">

				<form:hidden path="id" />
				<form:hidden path="version" />


				<acme:password code="personalData.oldPassword" path="oldPassword" />
				<acme:password code="personalData.newPassword" path="newPassword" />
				<acme:password code="personalData.secondPassword"
					path="secondPassword" />



				<acme:submit code="personalData.save" name="save2" />
				<acme:cancel url="profile/patient/myPersonalDatas.do"
					code="personalData.cancel" />

			</div>

		</form:form>

	</div>
</jstl:if>


<jstl:if test="${changeCreditCard==true}">
	<div class="col-md-7  col-md-offset-2" style="margin-bottom: 20%;">
		<br /> <br />

		<form:form action="${requestURI}" modelAttribute="patientForm4">

			<div class="col-md-12" style="margin-bottom: 20%;">


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



				<acme:submit code="personalData.save" name="save3" />
				<acme:cancel url="profile/patient/myPersonalDatas.do"
					code="personalData.cancel" />

			</div>

		</form:form>

	</div>
</jstl:if>