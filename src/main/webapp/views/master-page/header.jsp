<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="AcmeEssay Gp32" />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<li><a class="fNiv"><spring:message	code="master.page.contests" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="contest/list.do"><spring:message code="master.page.contests.list" /></a></li>
				<security:authorize access="hasRole('ORGANISER')">
					<li><a href="contest/organiser/list.do"><spring:message code="master.page.myContests" /></a></li>
				</security:authorize>					
			</ul>
		</li>
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/register.do"><spring:message code="master.page.administrator.register" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.customers" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/list.do"><spring:message code="master.page.customers.list" /></a></li>					
				</ul>
			</li>
			<li><a href="administrator/dashboard.do"><spring:message code="master.page.dashboard"/></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUTHOR')">
			<li><a class="fNiv"><spring:message	code="master.page.essay" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="essay/author/list.do"><spring:message code="master.page.author.listEssay" /></a></li>					
				</ul>
			</li>
			
		</security:authorize>
		
		<security:authorize access="hasRole('ORGANISER')">
			<li><a class="fNiv"><spring:message	code="master.page.sessionsMenu" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="publicSession/list.do"><spring:message code="master.page.sessions" /></a></li>
					<li><a href="publicSession/organiser/list.do"><spring:message code="master.page.mySessions" /></a></li>
					<li><a href="publicSession/organiser/listInactive.do"><spring:message code="master.page.inactiveSessions" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">		
			<li><a class="fNiv" href="publicSession/list.do"><spring:message code="master.page.sessions" /></a></li>
			<li>
				<a class="fNiv"> <spring:message code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="organiser/register.do"><spring:message code="master.page.organiser.register"/></a>
					<li><a href="author/register.do"><spring:message code="master.page.author.register"/></a>
				</ul>
			</li>
			
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

