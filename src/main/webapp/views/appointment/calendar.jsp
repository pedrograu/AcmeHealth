
    
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


<textarea id="eventos" hidden="true">${eventos}</textarea>

<div id='calendar'></div>


<%-- 
<jstl:if test="${isPatient==true}">

<form:form method="get" action="${requestURI}">

	<script>
	function fj(){
		$("#startMoment").datepicker({
			dateFormat : 'dd/mm/yy',
		});
	};
	</script>

<label for="startMoment"><spring:message code="appointment.startMoment1" /></label>
<input type="text" size="20" id="startMoment" name="startMoment" onmousemove="fj()"/>
<input type="hidden" size="20" id="offerId" name="offerId" value="<%=  request.getParameter("offerId")%> "/>


<button type="submit" class="btn btn-primary">
	<spring:message code="appointment.save" />
</button>

</form:form>

</jstl:if>

<jstl:if test="${isPatient==false}">

<form:form method="get" action="${requestURI}">

	<script>
	function fj(){
		$("#startMoment").datepicker({
			dateFormat : 'dd/mm/yy',
		});
	};
	</script>

<label for="startMoment"><spring:message code="appointment.startMoment1" /></label>
<input type="text" size="20" id="startMoment" name="startMoment" onmousemove="fj()"/>
<input type="hidden" size="20" id="patientId" name="patientId" value="<%=  request.getParameter("patientId")%> "/>


 	<SELECT name="specialistId" id="specialistId">

		<jstl:forEach var="x" items="${specialists}">
			<OPTION VALUE="${x.id}">${x.name}</OPTION>
		</jstl:forEach>

	</SELECT> 


<button type="submit" class="btn btn-primary">
	<spring:message code="appointment.save" />
</button>

</form:form>

</jstl:if> --%>