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


<form:form action="${requestURI }" modelAttribute="contest">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="contestOrganisers"/>
	<form:hidden path="essays"/>
	
	<acme:textbox code="name" path="name"/>
	<acme:textbox code="description" path="description"/>
	<acme:textbox code="holdingDate" path="holdingDate"/>
	<acme:textbox code="deadline" path="deadline"/>
	<jstl:if test="${resoluble}">
		<acme:textbox code="result" path="result"/>
	</jstl:if>
	
	<acme:submit name="save" code="contest.save"/>
	<jstl:if test="${borrable}">	
		<input type="submit" name="delete"
			value="<spring:message code="contest.delete"/>"
		/>	
	</jstl:if>
	<input type="button" name="return"
		value="<spring:message code="contest.return"/>"
		onClick="javascript: window.location.replace('contest/organiser/list.do');"
	/>
</form:form>