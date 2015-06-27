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
	<br />
	<br />


	<div class="row">
		<div class="col-md-5  toppad  pull-right col-md-offset-3 "></div>
		<div
			class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">


			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">${patient.surname},${patient.name}</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-xs-3 col-md-3 col-lg-3 " align="center">
							<img style="width: 100%; height: 100%;"
								src="profile/patient/showImage.do?patientId=${patient.id}">
						</div>

						<div class=" col-md-9 col-lg-9 ">
							<table class="table table-user-information">
								<tbody>
				
									<tr>
										<td><b><spring:message
													code="personalData.message" /></b></td>
										<td></td>
									</tr>
									<tr>
										<td>
										<td>
									</tr>
									<tr>
										<td><spring:message code="personalData.address" /></td>
										<td><jstl:out value="${patient.address}"></jstl:out></td>
									</tr>
									<tr>
										<td><spring:message code="personalData.phone" /></td>
										<td><jstl:out value="${patient.phone}"></jstl:out></td>
									</tr>
									<tr>
										<td><spring:message code="personalData.emailAddress" /></td>
										<td><a href="mailto:${patient.emailAddress}"><jstl:out
													value="${patient.emailAddress}"></jstl:out></a></td>
									</tr>
									<tr>
										<td>
										<td>
									</tr>
									<tr>
										<td><b><spring:message
													code="personalData.creditCard.name" /></b></td>
										<td></td>
									</tr>
									<tr>
										<td>
										<td>
									</tr>
									<tr>
										<td><spring:message
												code="personalData.creditCard.holderName" /></td>
										<td><jstl:out value="${patient.creditCard.holderName}"></jstl:out></td>
									</tr>
									<tr>
										<td><spring:message
												code="personalData.creditCard.brandName" /></td>
										<td><jstl:out value="${patient.creditCard.brandName}"></jstl:out></td>
									</tr>
									<tr>
										<td><spring:message code="personalData.creditCard.number" /></td>
										<td><jstl:out value="${patient.creditCard.number}"></jstl:out></td>
									</tr>
									<tr>
										<td><spring:message
												code="personalData.creditCard.expirationMonth" /></td>
										<td><jstl:out
												value="${patient.creditCard.expirationMonth}"></jstl:out></td>
									</tr>
									<tr>
										<td><spring:message
												code="personalData.creditCard.expirationYear" /></td>
										<td><jstl:out
												value="${patient.creditCard.expirationYear}"></jstl:out></td>
									</tr>
									<tr>
									<tr>
										<td><spring:message
												code="personalData.creditCard.cVVcode" /></td>
										<td><jstl:out value="${patient.creditCard.cVVcode}"></jstl:out></td>
									</tr>


								</tbody>
							</table>
					</div>
					<div class=" col-md-12 col-lg-12 " align="center">
							<a href="profile/patient/edit.do" class="btn btn-primary"><spring:message
									code="personalData.edit" /></a> &nbsp;<a
								href="profile/patient/changePassword.do" class="btn btn-primary"><spring:message
									code="personalData.changePassword" /></a> &nbsp;<a
								href="profile/patient/editCreditCard.do" class="btn btn-primary"><spring:message
									code="personalData.editCreditCard" /></a>
						
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>



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