<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>




<display:table name="offers" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag" keepStatus="true">


	<security:authorize access="isAnonymous()">
		<display:column>
			<a href="offer/details.do?offerId=${row.id}"><spring:message code="offer.details" /></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('PATIENT')">
		<display:column>
			<a href="offer/patient/detail.do?offerId=${row.id}"><spring:message code="offer.details" /></a>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column>
			<a href="offer/administrator/detail.do?offerId=${row.id}"><spring:message code="offer.details" /></a>
		</display:column>
		<jstl:if test="${own==true}">
		<display:column>
			<a href="offer/administrator/delete.do?offerId=${row.id}"><spring:message code="offer.delete" /></a>
		</display:column>
		</jstl:if>
	</security:authorize>
	
	<security:authorize access="hasRole('SPECIALIST')">
		<display:column>
			<a href="offer/specialist/detail.do?offerId=${row.id}"><spring:message code="offer.details" /></a>
		</display:column>
	</security:authorize>
	


	<security:authorize access="hasRole('PATIENT')">
	<jstl:if test="${notFinish==true}">	
		<display:column>
		<a href="offer/patient/hire.do?offerId=${row.id}"><spring:message code="offer.hire" /></a>
		</display:column>
	</jstl:if>
	
	<jstl:if test="${hire==true}">	
		<display:column>
		<a href="appointment/patient/calendar2.do?offerId=${row.id}"><spring:message code="offer.selectApointment" /></a>
		</display:column>
	</jstl:if> 
	</security:authorize> 

	<spring:message code="offer.title" var="title" />
	<display:column property="title" title="${title}" sortable="${true}" />
	
	<spring:message code="offer.startMoment" var="startMoment" />
	<display:column property="startMoment" title="${startMoment}" sortable="${true}" format="{0,date,dd/MM/yyyy }"/>

	<spring:message code="offer.finishMoment" var="finishMoment" />
	<display:column property="finishMoment" title="${finishMoment}" sortable="${true}" format="{0,date,dd/MM/yyyy }"/>
	
	<spring:message code="offer.price" var="price" />
	<display:column property="price" title="${price}" sortable="${true}" />
	
	
	
	
</display:table> 


<jstl:if test="${mostrarError==true}">
	<b><spring:message code="offer.errorDelete" /></b>
</jstl:if>

