

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
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">




<jstl:choose>
	<jstl:when test="${error == true}">
		<br />
		<b><spring:message code="appointment.error" /></b>
	</jstl:when>
	<jstl:when test="${mensaje == true}">
		<br />
		<b><spring:message code="appointment.mensaje" /></b>
	</jstl:when>
	<jstl:otherwise>
		<textarea id="eventos" hidden="true">${eventos}</textarea>
		<div id='calendar'></div>
	</jstl:otherwise>
</jstl:choose>







