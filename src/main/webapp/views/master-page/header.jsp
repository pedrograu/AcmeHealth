<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Health Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMINISTRATOR')">

			<li><a href="register/specialist/edit.do" class="fNiv"><spring:message	code="master.page.register.specialist" /></a></li>
	
			
			<li><a class="fNiv"><spring:message	code="master.page.specialty" /></a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="specialty/administrator/list-all.do" ><spring:message	code="master.page.specialty.listAll" /></a></li>
					<li><a href="specialty/administrator/create.do" ><spring:message code="master.page.specialty.create" /></a></li>
				</ul>
			</li>
				
				
			<li><a class="fNiv"><spring:message code="master.page.offer" /></a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="offer/administrator/list-own.do" ><spring:message	code="master.page.offer.listOwn" /></a></li>
					<li><a href="offer/administrator/list-not-finished.do"	><spring:message code="master.page.offer.listNotFinished" /></a></li>
					<li><a href="offer/administrator/list-active.do" ><spring:message code="master.page.offer.listActive" /></a></li>
					<li><a href="offer/administrator/list-order.do" ><spring:message code="master.page.offer.listOrder" /></a></li>
					<li><a href="offer/administrator/create.do" ><spring:message code="master.page.offer.create" /></a></li>
				</ul>
			</li>
			<li><a href="patient/administrator/list.do" class="fNiv"><spring:message code="master.page.patient.list" /></a></li>
			<li><a href="specialist/administrator/list.do" class="fNiv"><spring:message code="master.page.specialist.list" /></a></li>
			<li><a href="dashboard/administrator/dashboard.do" class="fNiv"><spring:message code="master.page.dashboard" /></a></li>
		</security:authorize>
		
		

		<security:authorize access="hasRole('PATIENT')">
		
			<li><a class="fNiv"><spring:message code="master.page.appointment" /></a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="appointment/patient/list.do" ><spring:message	code="master.page.appointment.list" /></a></li>
					<li><a href="appointment/patient/calendar.do" ><spring:message	code="master.page.appointment.create" /></a></li>					
				</ul>
			</li>
			
			
			
			<li><a href="prescription/patient/list-my-prescription.do"	class="fNiv"><spring:message code="master.page.prescription.listMyPrescription" /></a></li> 
			
		
			
		
		

			<li><a href="specialist/patient/list.do" class="fNiv"><spring:message	code="master.page.specialist.listAll" /></a></li>
			

			<li><a class="fNiv"><spring:message	code="master.page.offer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="offer/patient/list-own.do"><spring:message code="master.page.offerOwn" /></a></li> 
					<li><a href="offer/patient/list-not-finished.do"><spring:message code="master.page.offerNotFinish" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message code="master.page.message" /></a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="message/customer/list-outbox.do" ><spring:message	code="master.page.message.list.outbox" /></a></li>
					<li><a href="message/customer/list-inbox.do" ><spring:message	code="master.page.message.list.inbox" /></a></li>
					<li><a href="message/customer/create.do" ><spring:message	code="master.page.message.create" /></a></li>					
				</ul>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('SPECIALIST')">

			
			<li><a class="fNiv"><spring:message code="master.page.specialist.appointment" /></a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="appointment/specialist/listNotFinish.do" ><spring:message code="master.page.appointment.listNotFinish" /></a></li>						
					<li><a href="appointment/specialist/listFinish.do"	><spring:message code="master.page.appointment.listFinish" /></a></li>					
				</ul>
			</li>	
			
			 
	
			<li><a class="fNiv"><spring:message code="master.page.specialist.patient" /></a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="patient/specialist/list-own.do" ><spring:message	code="master.page.specialist.patient.list.own" /></a></li>
					<li><a href="patient/specialist/list-today.do" ><spring:message	code="master.page.specialist.patient.list.today" /></a></li>					
				</ul>
			</li>	
				
			<li><a class="fNiv"><spring:message code="master.page.specialist.timetable" /></a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="timetable/specialist/list-own.do" ><spring:message	code="master.page.specialist.timetable.list.own" /></a></li>
					<li><a href="timetable/specialist/create.do" ><spring:message	code="master.page.specialist.timetable.create" /></a></li>					
				</ul>
			</li>
						
			<li><a href="offer/specialist/list-not-finished.do"
				class="fNiv"><spring:message
						code="master.page.specialist.listNotFinish" /></a></li>
						
						
			<li><a class="fNiv"><spring:message code="master.page.specialist.freeDay" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="freeDay/specialist/list-own.do" ><spring:message code="master.page.specialist.freeDays.list-own" /></a></li>				
					<li><a href="freeDay/specialist/create.do" ><spring:message code="master.page.specialist.freeDays.create" /></a></li>			
				</ul>
			</li>
						
			
			
			<li><a href="profile/specialist/detail.do"
				class="fNiv"><spring:message
						code="master.page.profile.details" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.message" /></a>
				<ul>
					<li class="arrow"></li>
					
					<li><a href="message/customer/list-outbox.do" ><spring:message	code="master.page.message.list.outbox" /></a></li>
					<li><a href="message/customer/list-inbox.do" ><spring:message	code="master.page.message.list.inbox" /></a></li>
					<li><a href="message/customer/create.do" ><spring:message	code="master.page.message.create" /></a></li>					
				</ul>
			</li>


		</security:authorize>

		<security:authorize access="isAnonymous()">

			<li><a class="fNiv" href="security/login.do"><spring:message	code="master.page.login" /></a></li>
			<li><a href="register/patient/edit.do" class="fNiv"><spring:message		code="master.page.register.patient" /></a></li>
			<li><a href="specialty/list-all.do" class="fNiv"><spring:message		code="master.page.specialty.listAll" /></a></li>
			<li><a href="specialist/list.do" class="fNiv"><spring:message			code="master.page.specialist.listAll" /></a></li>
			<li><a href="offer/list-not-finish.do" class="fNiv"><spring:message		code="master.page.offer.listNotFinished" /></a></li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication		property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message			code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

