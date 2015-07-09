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

<%-- 	<h2>
		<spring:message code="timetable.listTimetable" />
	</h2> --%>
	
<div class="table-responsive">
    <display:table name="timetables" id="row" requestURI="${requestURI}"
        pagesize="5" class="table table-hover" keepStatus="true">


        <spring:message code="timetable.startShift" var="startShift" />
        <display:column property="startShift" title="${startShift}"
            sortable="${true}" format="{0,date,HH:mm}" />

        <spring:message code="timetable.endShift" var="endShift" />
        <display:column property="endShift" title="${endShift}"
            sortable="${true}" format="{0,date,HH:mm}" />

        <spring:message code="timetable.day" var="day" />
        <display:column title="${day}" sortable="${true}">
            <jstl:if test="${row.day == 1 }">
                <spring:message code="timetable.day.sunday" />
            </jstl:if>
            <jstl:if test="${row.day == 2 }">
                <spring:message code="timetable.day.monday" />
            </jstl:if>
            <jstl:if test="${row.day == 3 }">
                <spring:message code="timetable.day.tuesday" />
            </jstl:if>
            <jstl:if test="${row.day == 4 }">
                <spring:message code="timetable.day.wednesday" />
            </jstl:if>
            <jstl:if test="${row.day == 5 }">
                <spring:message code="timetable.day.thursday" />
            </jstl:if>
            <jstl:if test="${row.day == 6 }">
                <spring:message code="timetable.day.friday" />
            </jstl:if>
            <jstl:if test="${row.day == 7 }">
                <spring:message code="timetable.day.saturday" />
            </jstl:if>
        </display:column>
        
          <display:column>
		   <a href="timetable/specialist/delete.do?timetableId=${row.id}"><button class="btn btn-danger btn-xs">
		     <i class="fa fa-trash-o "></i>
		    </button></a>
		  </display:column>



    </display:table>
</div>

	<h3>
		<spring:message code="timetable.createTimetable" />
	</h3>

<div class="row">
<div class="col-md-7  col-md-offset-2" style="margin-bottom: 100px;">
	<form:form action="${requestURI}" modelAttribute="timetableForm">


		<form:hidden path="id" />
		<form:hidden path="version" />




		<acme:textbox code="timetable.startShift" path="startShift" placeholder ="HH:mm" />
		<acme:textbox code="timetable.endShift" path="endShift" placeholder ="HH:mm" />
		<div class="form-group">
			<form:label path="day">
				<spring:message code="timetable.day" />
			</form:label>
			<form:select path="day" class="form-control" >
				<form:option label="1" value="1">
					<spring:message code="timetable.day.sunday" />
				</form:option>
				<form:option label="2" value="2">
					<spring:message code="timetable.day.monday" />
				</form:option>
				<form:option label="3" value="3">
					<spring:message code="timetable.day.tuesday" />
				</form:option>
				<form:option label="4" value="4">
					<spring:message code="timetable.day.wednesday" />
				</form:option>
				<form:option label="5" value="5">
					<spring:message code="timetable.day.thursday" />
				</form:option>
				<form:option label="6" value="6">
					<spring:message code="timetable.day.friday" />
				</form:option>
				<form:option label="7" value="7">
					<spring:message code="timetable.day.saturday" />
				</form:option>


			</form:select>
		</div>
		<form:errors cssClass="error" path="day" />
		<br />



		<acme:submit code="timetable.save" name="save" />
<%-- 		<acme:cancel url="timetable/specialist/list-own.do"
			code="comment.cancel" /> --%>


	</form:form>
</div>
</div>

