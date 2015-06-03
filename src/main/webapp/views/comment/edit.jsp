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
	<b><spring:message code="comment.details" /></b>
	<ul>
		
		<li><b><spring:message code="comment.text" /></b> <jstl:out
				value="${comment.text}"></jstl:out></li>
		<li><b><spring:message code="comment.rating" /></b> <jstl:out
				value="${comment.rating}"></jstl:out></li>
				
		<li><b><spring:message code="comment.creationMoment" /></b>
		<fmt:formatDate value="${comment.creationMoment}"
				pattern="dd/MM/yyyy HH:mm" /></li>
	</ul>
</jstl:if>

<br />
<jstl:if test="${details==false }">
	<form:form action="${requestURI}" modelAttribute="commentForm">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="profile" />
	


		<acme:textarea code="comment.text" path="text" />
		<acme:textbox code="comment.rating" path="rating" />
	

		<acme:submit code="comment.save" name="save" />
		<acme:cancel url="profile/patient/details.do?specialistId=${profile.specialist.id}" code="comment.cancel" />


	</form:form>

</jstl:if>