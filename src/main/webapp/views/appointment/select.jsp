
    
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">


<form:form method="get" action="${requestURI}">


<input type="hidden" size="20" id="patientId" name="patientId" value="<%=  request.getParameter("patientId")%> "/>


    <SELECT name="specialistId" id="specialistId">

        <jstl:forEach var="x" items="${specialists}">
            <OPTION VALUE="${x.id}">${x.name}</OPTION>
        </jstl:forEach>

    </SELECT> 


<button type="submit" class="btn btn-primary">
    <spring:message code="appointment.selectSpecialist" />
</button>

</form:form>

