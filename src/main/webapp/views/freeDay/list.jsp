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



<display:table name="freeDays" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag" keepStatus="true">
	
		<display:column>
			<a href="freeDay/specialist/delete.do?freeDayId=${row.id}"><spring:message code="freeDay.delete" /></a>
		</display:column>


	<spring:message code="freeDay.startMoment" var="startMoment" />
	<display:column property="startMoment" title="${startMoment}"
		sortable="${true}" format="{0,date,dd/MM/yyyy}" />
	
	<spring:message code="freeDay.finishMoment" var="finishMoment" />
	<display:column property="finishMoment" title="${finishMoment}"
		sortable="${true}" format="{0,date,dd/MM/yyyy}" />

	<spring:message code="freeDay.description" var="description" />
	<display:column property="description" title="${description}" sortable="${true}" />

</display:table>
