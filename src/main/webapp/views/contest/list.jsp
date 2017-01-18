<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<display:table name="contests" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="contest.essays" var="essaysCol"></spring:message>
	<display:column title="${essaysCol}">
		<a href="essay/listPublished.do?contestId=${row.id}"><spring:message code="contest.published"/></a>
		<br/>
		<jstl:if test="${requestURI=='contest/organiser/list.do'}">
			<security:authorize access="hasRole('ORGANISER')">
				<a href="essay/organiser/listAll.do?contestId=${row.id}"><spring:message code="contest.submitted"/></a>
				<br/>
			</security:authorize>
		</jstl:if>
	</display:column>
	
	<jstl:if test="${requestURI=='contest/organiser/list.do'}">
		<security:authorize access="hasRole('ORGANISER')">
			<spring:message code="contest.actions" var="actions"></spring:message>
			<display:column title="${actions}">
				<jstl:if test="${row.result==null || row.result==''}">
					<a href="contest/organiser/edit.do?contestId=${row.id}"><spring:message code="contest.edit"/></a>
					<br/>
				</jstl:if>
				<a href="contest/organiser/addOrganiser.do?contestId=${row.id}"><spring:message code="contest.addOrganiser"/></a>
				<br/>
			</display:column>
			<spring:message code="contest.sessions" var="sessionsCol"></spring:message>
			<display:column title="${sessionsCol}">
				<a href="publicSession/organiser/create.do?contestId=${row.id}"><spring:message code="contest.newSession"/></a>
				<br/>
				<a href="publicSession/organiser/listByContest.do?contestId=${row.id}"><spring:message code="contest.sessions"/></a>
			</display:column>
		</security:authorize>
	</jstl:if>
	<security:authorize access="hasRole('AUTHOR')">
		<display:column title="actions">
			<a href="essay/author/create.do?contestId=${row.id}"><spring:message code="contest.createEssay"/></a>
			<br/>
			<a href="essay/author/listMyEssays.do?contestId=${row.id}"><spring:message code="contest.myEssays"/></a>
			<br/>
		</display:column>
	</security:authorize>
	
	<spring:message code="name" var="name"></spring:message>
	<display:column property="name" title="${name}" sortable="false"></display:column>
	
	<spring:message code="description" var="description"></spring:message>
	<display:column property="description" title="${description}" sortable="false"></display:column>
	
	<spring:message code="holdingDate" var="holdingDate"></spring:message>
	<display:column property="holdingDate" title="${holdingDate}" sortable="false"></display:column>
		
	<spring:message code="deadline" var="deadline"></spring:message>
	<display:column property="deadline" title="${deadline}" sortable="false"></display:column>	
	
	<spring:message code="result" var="result"></spring:message>
	<display:column property="result" title="${result}" sortable="false"></display:column>
</display:table>

<security:authorize access="hasRole('ORGANISER')">
		<input type="button" name="create" value="<spring:message code="contest.create"/>" 
		onclick="javascript: window.location.replace('contest/organiser/create.do')"/>
		<br />
</security:authorize>	