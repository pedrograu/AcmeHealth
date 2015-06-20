<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:if test="${error==false}">

<form:form action="${requestURI}" modelAttribute="${actor}">

    <acme:textbox code = "register.nif" path ="nif"/>
    <acme:password code = "register.pass" path ="pass"/>


    <acme:submit code="register.check" name="check" />
    <acme:cancel url="welcome/index.do" code="register.cancel" />

    
</form:form>

</jstl:if>

<jstl:if test="${error==true}">

<form:form action="${requestURI}" modelAttribute="${actor}">

    <acme:textbox code = "register.nif" path ="nif"/>
    <acme:password code = "register.pass" path ="pass"/>


    <acme:submit code="register.check" name="check" />
    <acme:cancel url="welcome/index.do" code="register.cancel" />

    
</form:form>

<br/>
<div style="color:red;"><b><spring:message code="register.message" /></b></div>
</jstl:if>
