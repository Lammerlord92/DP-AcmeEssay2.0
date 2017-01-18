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

<form:form action="administrator/register.do" modelAttribute="administratorForm">
	<fieldset>
		<legend align="left">
			<spring:message code="administrator.userAccount" />
		</legend>	
		<acme:textbox code="administrator.userAccount.username" path="userName"/>
		<acme:password code="administrator.userAccount.password" path="password"/>
		<acme:password code="administrator.userAccount.confirmPassword" path="confirmPassword"/>
	</fieldset>
	<fieldset>
		<legend align="left">
			<spring:message code="administrator.personalInfo" />
		</legend>
			
		<acme:textbox code="administrator.name" path="name"/>
		<acme:textbox code="administrator.surname" path="surname"/>
		<acme:textbox code="administrator.emailAddress" path="emailAddress"/>
		<acme:textbox code="administrator.contactPhone" path="contactPhone"/>
		<acme:textbox code="administrator.url" path="url"/>
	
	</fieldset>	
	<p><acme:checkbox code="administrator.accepConditions" path="accepConditions"/>
	<a onClick="condiciones();" style="cursor:pointer;">(About)</a>
	</p>
	
	<input type="submit" name="save" value="<spring:message code="administrator.save"/>" />
	&nbsp;
	<input type="button" name="cancel" value="<spring:message code="administrator.cancel"/>" 
	onclick="javascript: window.location.replace('/AcmeEssay')"/>
</form:form>