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




<%-- 
<form:form action="${requestURI}" modelAttribute="eventForm">

	<jstl:if test="${isEditable==false || isOwn==false}">
		<ul>
			<li><b><spring:message code="event.title" /></b>
				<jstl:out value="${eventForm.title}"></jstl:out></li>
			<li><b><spring:message code="event.creationMoment" /></b><fmt:formatDate
					value="${event.creationMoment}" pattern="dd/MM/yyyy HH:mm" /></li>
			<li><b><spring:message code="event.referenceNumber" /></b>
				<jstl:out value="${eventForm.referenceNumber}"></jstl:out></li>
			<li><b><spring:message code="event.description" /></b>
				<jstl:out value="${eventForm.description}"></jstl:out></li>
			<li><b><spring:message code="event.fee" /></b>
				<jstl:out value="${eventForm.fee}"></jstl:out></li>
			<li><b><spring:message code="event.startMoment" /></b>
				<fmt:formatDate value="${eventForm.startMoment}" pattern="dd/MM/yyyy HH:mm" /></li>
			<li><b><spring:message code="event.finishMoment" /></b>
				<fmt:formatDate value="${eventForm.finishMoment}" pattern="dd/MM/yyyy HH:mm" /></li>
			<li><b><spring:message code="event.rating" /></b>
				<jstl:out value="${event.rating}"></jstl:out></li>
		</ul>

	</jstl:if>

	<security:authorize access="hasRole('PARTICIPANT')">
	
	<jstl:if test="${startMomentElapsed==false}"> 
	<jstl:if test="${isRegistered==false }">
	<a href="registration/participant/register.do?eventId=${event.id}"><spring:message code="event.register" /></a>
	</jstl:if>
	<jstl:if test="${isRegistered==true }">
	<a href="registration/participant/unregister.do?eventId=${event.id}"><spring:message code="event.unregister" /></a>
	</jstl:if>
	 </jstl:if> 
	<jstl:if test="${startMomentElapsed==true}">
	<b><spring:message code="event.message" /></b><br/>
	</jstl:if>
	<jstl:if test="${isRegistered==true && finishMomentElapsed==true}">
	<a href="registration/participant/edit.do?eventId=${event.id}"><spring:message code="event.evaluate" /></a>
	</jstl:if>
	
	</security:authorize>
	<!-- && isOwn==true -->
	<jstl:if test="${isEditable==true && isOwn==true }">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="administrator" />  <!-- No se ponen aqui por el tema del hacking, -->
	<form:hidden path="routes" />         <!--  en el servicio recuperamos dichos atributos de la -->
	<form:hidden path="challenges" />     <!--  base de datos y comprobamos q sean validos, es decir, -->
	<form:hidden path="registrations" />  <!--  que sean del adminConnect --> --%>
<%-- 

	<acme:textbox code="event.creationMoment" path="creationMoment" readonly="true"/> 
	<acme:textbox code="event.referenceNumber" path="referenceNumber" />
	<acme:textbox code="event.title2" path="title" />
	<acme:textarea code="event.description" path="description" />
	<acme:textbox code="event.fee" path="fee" />
	<acme:textbox code="event.startMoment" path="startMoment" />
	<acme:textbox code="event.finishMoment" path="finishMoment" />
	<acme:checkbox code="event.available" path="available" />

	<acme:submit code="event.save" name="save" />
	<jstl:if test="${id !=0}">
		<acme:submit code="event.delete" name="delete" />
	</jstl:if>
	<acme:cancel url="welcome/index.do" code="event.cancel"/>
	</jstl:if>

</form:form>
 --%>

