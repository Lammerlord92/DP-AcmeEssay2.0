<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<fieldset>
	<legend align="left">
		<spring:message code="essay.essaysSelected"></spring:message>
	</legend>
	<display:table name="essaysSelected" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		
		<security:authorize access="hasRole('ORGANISER')">
			<spring:message code="essay.actions" var="Actions"></spring:message>
			<display:column title="action">
				
				<a href="essay/organiser/deleteFromSession.do?essayId=${row.id}&sessionId=${sessionId}">
				<spring:message code="essay.deleteFrom"/></a> 
				<br/>
	
			</display:column>
		</security:authorize>
		
		<spring:message code="essay.title" var="title"></spring:message>
		<display:column property="title" title="${title}" sortable="false"></display:column>
			
		 <spring:message code="essay.abstractEss" var="abstractEss"></spring:message>
		<display:column property="abstractEss" title="${abstractEss}" sortable="false"></display:column>
			
		<spring:message code="essay.submissionDate" var="submissionDate"></spring:message>
		<display:column property="submissionDate" title="${submissionDate}" sortable="false"></display:column>	
			
		<spring:message code="essay.published" var="published"></spring:message>
		<display:column property="published" title="${published}" sortable="false"></display:column>
	</display:table>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="essay.essaysRemaining"></spring:message>
	</legend>
	<display:table name="essaysRemaining" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		
		<security:authorize access="hasRole('ORGANISER')">
			<spring:message code="essay.actions" var="actions"></spring:message>
			<display:column title="${actions}">
				
				<a href="essay/organiser/addFromSession.do?essayId=${row.id}&sessionId=${sessionId}">
				<spring:message code="essay.addFrom"/></a> 
				<br/>
	
			</display:column>
		</security:authorize>
		
		<spring:message code="essay.title" var="title"></spring:message>
		<display:column property="title" title="${title}" sortable="false"></display:column>
			
		 <spring:message code="essay.abstractEss" var="abstractEss"></spring:message>
		<display:column property="abstractEss" title="${abstractEss}" sortable="false"></display:column>
			
		<spring:message code="essay.submissionDate" var="submissionDate"></spring:message>
		<display:column property="submissionDate" title="${submissionDate}" sortable="false"></display:column>	
			
		<spring:message code="essay.published" var="published"></spring:message>
		<display:column property="published" title="${published}" sortable="false"></display:column>
	</display:table>
</fieldset>
	
	<input type="button" name="cancel" value="<spring:message code="essay.cancel"/>" 
	onclick="javascript: window.location.replace('publicSession/organiser/edit.do?publicSessionId=${sessionId}')"/>