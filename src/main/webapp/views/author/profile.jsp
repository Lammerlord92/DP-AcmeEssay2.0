<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="author">
	<fieldset>
		<legend align="left">
			<b><spring:message code="author.personalInfo" /></b>
		</legend>
		
		<acme:formOut code="author.name" value="${author.name}" path="name"/>
		<acme:formOut code="author.surname" value="${author.surname}" path="surname"/>
		<acme:formOut code="author.emailAddress" value="${author.emailAddress}" path="emailAddress"/>
		<acme:formOut code="author.contactPhone" value="${author.contactPhone}" path="contactPhone"/>
		<acme:formOut code="author.url" value="${author.url}" path="url"/>
	</fieldset>	
</form:form>

<fieldset>
	<legend align="left">
		<b><spring:message code="author.essays" /></b>
	</legend>
	
	<display:table name="author.essays" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="essay.title" var="title"></spring:message>
		<display:column property="title" title="${title}" sortable="false"></display:column>
		
		<%-- <spring:message code="essay.abstractEss" var="abstractEss"></spring:message>
		<display:column property="abstractEss" title="${abstractEss}" sortable="false"></display:column> --%>
		
		<spring:message code="essay.submissionDate" var="submissionDate"></spring:message>
		<display:column property="submissionDate" title="${submissionDate}" sortable="false"></display:column>	
		
		<spring:message code="essay.published" var="published"></spring:message>
		<display:column property="published" title="${published}" sortable="false"></display:column>
	</display:table>
</fieldset>	