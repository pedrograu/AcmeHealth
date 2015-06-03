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
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:if test="${detailsProfile==true}">

<h2><b><spring:message code="profile.datosPersonales" /></b></h2>

<ul>
	<li><b><spring:message code="profile.datosPersonales.name" /></b> 
	<jstl:out value="${specialist.name}"></jstl:out></li>
	<li><b><spring:message code="profile.datosPersonales.surname" /></b> 
	<jstl:out value="${specialist.surname}"></jstl:out></li>
	<li><b><spring:message code="profile.datosPersonales.emailAddress" /></b> 
	<jstl:out value="${specialist.emailAddress}"></jstl:out></li>
	<li><b><spring:message code="profile.datosPersonales.text" /></b> 
	<jstl:out value="${profile.text}"></jstl:out></li>

</ul>
<br/>

<security:authorize access="hasRole('PATIENT')">
	<jstl:if test="${isGP==true}"> 
	<a href="profile/patient/change.do?specialistId=${specialist.id}" ><spring:message code="profile.change.specialist" /></a>
	</jstl:if> 
</security:authorize>

<h2><b><spring:message code="profile.specialty" /></b></h2>

<ul>
	<li><b><spring:message code="profile.specialty.name" /></b> 
	<jstl:out value="${specialist.specialty.name}"></jstl:out></li>
	<li><b><spring:message code="profile.specialty.description" /></b> 
	<jstl:out value="${specialist.specialty.description}"></jstl:out></li>

</ul>
<br/>

<h2><b><spring:message code="profile.rating" /></b></h2>

<ul>
	<li><b><spring:message code="profile.rating2" /></b> 
	<jstl:if test="${specialist.profile.rating!=null}">
		<jstl:out value="${specialist.profile.rating}"></jstl:out>
	</jstl:if>
	<jstl:if test="${specialist.profile.rating==null}">
		<spring:message code="profile.rating.message" />
	</jstl:if>
	</li>


</ul>
<br/>

<h2><b><spring:message code="profile.comments" /></b></h2>

<display:table name="comments" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag" keepStatus="true">
	
	<security:authorize access="isAnonymous()">
	<display:column>
	<a href="comment/details.do?commentId=${row.id}"><spring:message code="profile.comment.details" /></a>
	</display:column>
	</security:authorize>
	<security:authorize access="hasRole('PATIENT')">
	<display:column>
	<a href="comment/patient/details.do?commentId=${row.id}"><spring:message code="profile.comment.details" /></a>
	</display:column>
	</security:authorize>
	<security:authorize access="hasRole('ADMINISTRATOR')">
	<display:column>
	<a href="comment/administrator/details.do?commentId=${row.id}"><spring:message code="profile.comment.details" /></a>
	</display:column>
	</security:authorize>


	<spring:message code="profile.comment.creationMoment" var="creationMoment" />
	<display:column property="creationMoment" title="${creationMoment}" sortable="${true}"  format="{0,date,dd/MM/yyyy HH:mm}"/>
	
	<spring:message code="profile.comment.rating" var="rating" />
	<display:column property="rating" title="${rating}" sortable="${true}" />
	
</display:table>
<security:authorize access="hasRole('PATIENT')">
	<jstl:if test="${hasAppointmentFinish==true}"> 
	<a href="comment/patient/create.do?profileId=${specialist.profile.id}" ><spring:message code="profile.comment.create" /></a>
	</jstl:if> 
</security:authorize>
<br/>
<br/>
<security:authorize access="hasRole('SPECIALIST')">
	
	<a href="profile/specialist/edit.do?profileId=${specialist.profile.id}" ><spring:message code="specialist.profile.edit" /></a>
	
</security:authorize>



</jstl:if>

<security:authorize access="hasRole('SPECIALIST')">
		
		<jstl:if test="${detailsProfile==false}">
		
	<form:form action="${requestURI}" modelAttribute="profile">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="rating" /> 
		<form:hidden path="comments" /> 
		<form:hidden path="specialist" /> 
		
	
		<acme:textarea code="profile.text" path="text" />
	
		<acme:submit code="profile.save" name="save" />
		<acme:cancel url="profile/specialist/detail.do" code="profile.cancel" />


	</form:form>
	
	</jstl:if>
	
</security:authorize>