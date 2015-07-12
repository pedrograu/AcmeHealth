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

	<jstl:if test="${details==true }">


		<div class="col-lg-12">
			<div class="row row-centered">
				<div class="col-lg-12 col-md-12 col-sm-12 mb">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="centered">
								<b><spring:message code="message.details" /></b>
							</h3>
						</div>
						<div class="panel-body">
							<ul>
								<li><b><spring:message code="message.sender2" /></b> <jstl:out
										value="${messageCustomer.sender.surname}, ${messageCustomer.sender.name}"></jstl:out></li>
								<li><b><spring:message code="message.recipient2" /></b> <jstl:out
										value="${messageCustomer.recipient.surname}, ${messageCustomer.recipient.name}"></jstl:out></li>
								<li><b><spring:message code="message.subject2" /></b> <jstl:out
										value="${messageCustomer.subject}"></jstl:out></li>
								<li><b><spring:message code="message.creationMoment2" /></b>
									<fmt:formatDate value="${messageCustomer.creationMoment}"
										pattern="dd/MM/yyyy HH:mm" /></li>
								<li><b><spring:message code="message.textBody2" /></b> <jstl:out
										value="${messageCustomer.textBody}"></jstl:out></li>


							</ul>
						</div>
					</div>
				</div>
			</div>
			<jstl:if test="${inbox==true }">
			<div class="row">
				<a href="message/customer/answer.do?messageId=${messageCustomer.id}">
					<button type="button" class="btn btn-default btn-lg btn-block">
						<spring:message code="message.reply" />
					</button>
				</a>
			</div>
			</jstl:if>
		</div>



	</jstl:if>

	<br />
	<jstl:if test="${details==false }">

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
				<acme:cancel url="customerArea/specialist/list.do"
					code="comment.cancel" />
			</jstl:if>

		</form:form>
	</jstl:if>
</div>
