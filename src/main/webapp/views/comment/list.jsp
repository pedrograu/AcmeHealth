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


<jstl:if test="${itemSeller==false }">
	<b><spring:message code="comment.item.details" /></b>
	<ul>
		<li><b><spring:message code="comment.item.name" /></b> <jstl:out
				value="${item.name}"></jstl:out></li>
		<li><b><spring:message code="comment.item.description" /></b> <jstl:out
				value="${item.description}"></jstl:out></li>
		<li><b><spring:message code="comment.item.seller" /></b> <jstl:out
				value="${item.seller.name}"></jstl:out></li>
		<li><b><spring:message code="comment.item.units" /></b> <jstl:out
				value="${item.units}"></jstl:out></li>
		<li><b><spring:message code="comment.item.price" /></b> <jstl:out
				value="${item.price}"></jstl:out></li>
		<li><b><spring:message code="comment.item.rating" /></b> <jstl:out
				value="${item.rating}"></jstl:out></li>
	</ul>
</jstl:if>

<br />


<display:table name="comments" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag" keepStatus="true">
	

	<jstl:if test="${itemSeller==true }">
		<display:column>
			<a href="comment/customer/detail.do?commentId=${row.id}"><spring:message
					code="comment.edit" /></a>
		</display:column>

		<spring:message code="comment.item" var="name" />
		<display:column property="item.name" title="${name}"
			sortable="${false}" />
	</jstl:if>

	<spring:message code="comment.creationMoment" var="creationMoment" />
	<display:column property="creationMoment" title="${creationMoment}"
		sortable="${true}" format="{0,date,dd/MM/yyyy HH:mm}" />

	<jstl:if test="${itemSeller==false }">
		<spring:message code="comment.text" var="text" />
		<display:column property="text" title="${text}" sortable="${false}" />
	</jstl:if>

	<spring:message code="comment.rating" var="rating" />
	<display:column property="rating" title="${rating}" sortable="${true}" />

</display:table>

<jstl:if test="${notMyItem==false || isBuyer==true}">
	<b><a href="comment/customer/create.do?itemId=${itemId}"><spring:message
				code="comment.create" /></a></b>
</jstl:if>