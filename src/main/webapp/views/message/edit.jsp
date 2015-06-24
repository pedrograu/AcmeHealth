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




<jstl:if test="${details==true }">
	<b><spring:message code="message.details" /></b>
	<ul>
		<li><b><spring:message code="message.sender2" /></b> <jstl:out
				value="${messageCustomer.sender.surname}, ${messageCustomer.sender.name}"></jstl:out></li>
		<li><b><spring:message code="message.subject2" /></b> <jstl:out
				value="${messageCustomer.subject}"></jstl:out></li>
		<li><b><spring:message code="message.creationMoment2" /></b> <fmt:formatDate
				value="${messageCustomer.creationMoment}" pattern="dd/MM/yyyy HH:mm" /></li>
		<li><b><spring:message code="message.textBody2" /></b> <jstl:out
				value="${messageCustomer.textBody}"></jstl:out></li>


	</ul>
	<br />

	<b><a
		href="message/customer/answer.do?messageId=${messageCustomer.id}"><spring:message
				code="message.answer" /></a></b>



</jstl:if>

<br />
<jstl:if test="${details==false }">
	<div class="col-md-7  col-md-offset-2" style="margin-bottom: 100px;">
		<form:form action="${requestURI}" modelAttribute="messageForm">


			<form:hidden path="id" />
			<form:hidden path="version" />

			<jstl:if test="${answer==true }">
				<form:hidden path="recipient" />
			</jstl:if>

			<jstl:if test="${answer==false }">
				<acme:select items="${customers}" itemLabel="name"
					code="message.recipient" path="recipient" />
			</jstl:if>


			<acme:textbox code="message.subject" path="subject" />
			<acme:textarea code="message.textBody" path="textBody" />



			<jstl:if test="${cancel == false && answer==false}">
				<acme:submit code="message.save" name="save" />
			</jstl:if>
			<jstl:if test="${cancel == false && answer==true}">
				<acme:submit code="message.save" name="save3" />
			</jstl:if>
			<jstl:if test="${cancel == true }">
				<acme:submit code="message.save" name="save2" />
			</jstl:if>
			<jstl:if test="${cancel == false}">
				<acme:cancel url="message/customer/list-outbox.do"
					code="comment.cancel" />
			</jstl:if>
			<jstl:if test="${cancel == true}">
				<acme:cancel url="appointment/specialist/listNotFinish.do"
					code="comment.cancel" />
			</jstl:if>

		</form:form>
	</div>
</jstl:if>