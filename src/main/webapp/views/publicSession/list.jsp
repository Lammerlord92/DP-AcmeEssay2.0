<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="sessions" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<security:authorize access="hasRole('ORGANISER')">
		<jstl:if test="${requestURI=='publicSession/organiser/list.do' || requestURI=='publicSession/organiser/listInactive.do'}">
			<spring:message code="session.actions" var="actions"></spring:message>
			<display:column title="${actions}">
			
				<a href="publicSession/organiser/edit.do?publicSessionId=${row.id}">
				<spring:message code="session.edit"/></a> 
				<br/>
				
				<jstl:if test="${requestURI=='publicSession/organiser/listInactive.do'}">
					<a href="publicSession/organiser/delete.do?sessionId=${row.id}">
					<spring:message code="session.delete"/></a> 
					<br/>
				</jstl:if>
				
			</display:column>
		</jstl:if>
	</security:authorize>

	<spring:message code="session.contest" var="contest"></spring:message>
	<display:column property="contest.name" title="${contest}" sortable="true"></display:column>

	<spring:message code="session.startMoment" var="startMoment"></spring:message>
	<display:column property="startMoment" title="${startMoment}" sortable="true"></display:column>
		
	 <spring:message code="session.endMoment" var="endMoment"></spring:message>
	<display:column property="endMoment" title="${endMoment}" sortable="true"></display:column>
		
	<spring:message code="session.capacity" var="capacity"></spring:message>
	<display:column property="capacity" title="${capacity}" sortable="true"></display:column>	
	
	<spring:message code="session.chairman" var="chairman"></spring:message>
	<display:column property="organiser.name" title="${chairman}" sortable="true"></display:column>
</display:table>