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


<form:form action="${requestURI }" modelAttribute="contestAddOrgganiserForm">
	<form:hidden path="contestId"/>	
	<fieldset>
		<legend align="left">
			<spring:message code="contest.organisers" />
		</legend>
		<jstl:forEach items="${organisers}" var="organiser">
			<jstl:out value="${organiser.surname}" />,<jstl:out value="${organiser.name}" />
			<br/>	
		</jstl:forEach>
	</fieldset>
	<acme:select items="${organisersSelect}" itemLabel="name" code="contest.organisersSelect" path="organiser"/>
	
	<acme:submit name="save" code="contest.save"/>
	<input type="button" name="return"
		value="<spring:message code="contest.return"/>"
		onClick="javascript: window.location.replace('contest/organiser/list.do');"
	/>
</form:form>