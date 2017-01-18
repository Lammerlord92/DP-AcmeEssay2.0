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


<form:form action="contest/organiser/create.do" modelAttribute="contest">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="contestOrganisers"/>
	<form:hidden path="essays"/>
	<form:hidden path="result"/>
	
	<acme:textbox code="name" path="name"/>
	<acme:textbox code="description" path="description"/>
	<acme:textbox code="holdingDate" path="holdingDate"/>
	<acme:textbox code="deadline" path="deadline"/>
	
	<div>
		<input type="submit" name="saveCreate"
			value="<spring:message code="contest.save"/>"
		/>
	</div>
</form:form>