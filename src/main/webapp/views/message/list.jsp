<%--
 * action-1.jsp
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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div class="table-responsive">
	<display:table name="messages" id="row" requestURI="${requestURI}"
		pagesize="5" class="table table-hover" keepStatus="true">


		<jstl:if test="${inbox==true }">
			<display:column>
				<a href="message/customer/details.do?messageId=${row.id}"><spring:message
						code="message.details" /></a>
			</display:column>
		</jstl:if>


		<spring:message code="message.subject" var="subject" />
		<display:column property="subject" title="${subject}"
			sortable="${false}" />


		<spring:message code="message.creationMoment" var="creationMoment" />
		<display:column property="creationMoment" title="${creationMoment}"
			sortable="${true}" format="{0,date,dd/MM/yyyy HH:mm}" />


		<spring:message code="message.sender" var="sender" />
		<display:column property="sender.name" title="${sender}"
			sortable="${false}" />


		<spring:message code="message.recipient" var="recipient" />
		<display:column property="recipient.name" title="${recipient}"
			sortable="${false}" />



	</display:table>
</div>

<b><a href="message/customer/create.do"><spring:message
			code="message.create" /></a></b>



