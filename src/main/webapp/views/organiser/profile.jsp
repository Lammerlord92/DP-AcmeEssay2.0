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


<form:form action="${requestURI}" modelAttribute="organiser">
<fieldset>
	<legend align="left">
		<b><spring:message code="organiser.personalInfo" /></b>
	</legend>
	
	<form:label path="name">
		<b><spring:message code="organiser.name"/>:</b>
	</form:label>
	<jstl:out value="${organiser.name}" />
	<br/>
	
	<form:label path="surname">
		<b><spring:message code="organiser.surname"/>:</b>
	</form:label>
	<jstl:out value="${organiser.surname}" />
	<br/>

	<form:label path="emailAddress">
		<b><spring:message code="organiser.emailAddress"/>:</b>
	</form:label>
	<jstl:out value="${organiser.emailAddress}" />
	<br/>
	
	<form:label path="contactPhone">
		<b><spring:message code="organiser.contactPhone"/>:</b>
	</form:label>
	<jstl:out value="${organiser.contactPhone}" />
	<br/>
	
	<form:label path="url">
		<b><spring:message code="organiser.url"/>:</b>
	</form:label>
	<jstl:out value="${organiser.url}" />
	<br/>

	<form:label path="birthDate">
		<b><spring:message code="organiser.birthDate"/>:</b>
	</form:label>
	<fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${organiser.birthDate}" />
	<br/>
		
	<form:label path="nationality">
		<b><spring:message code="organiser.nationality"/>:</b>
	</form:label>
	<jstl:out value="${organiser.nationality}" />
	<br/>
</fieldset>	
</form:form>

<legend align="left">
		<b><spring:message code="organiser.contests" /></b>
</legend>
<display:table name="contests" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	<spring:message code="organiser.contest.name" var="name"></spring:message>
	<display:column property="name" title="${name}" sortable="false"></display:column>
	
	<spring:message code="organiser.contest.description" var="description"></spring:message>
	<display:column property="description" title="${description}" sortable="false"></display:column>
	
	<spring:message code="organiser.contest.holdingDate" var="holdingDate"></spring:message>
	<display:column property="holdingDate" title="${holdingDate}" sortable="false"></display:column>
		
	<spring:message code="organiser.contest.deadline" var="deadline"></spring:message>
	<display:column property="deadline" title="${deadline}" sortable="false"></display:column>	
	
	<spring:message code="organiser.contest.result" var="result"></spring:message>
	<display:column property="result" title="${result}" sortable="false"></display:column>
</display:table>