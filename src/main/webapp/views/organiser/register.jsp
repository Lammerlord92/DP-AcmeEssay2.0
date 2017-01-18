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

<form:form action="organiser/register.do" modelAttribute="organiserForm">
	<fieldset>
		<legend align="left">
			<spring:message code="organiser.userAccount" />
		</legend>	
		<acme:textbox code="organiser.userAccount.username" path="userName"/>
		<acme:password code="organiser.userAccount.password" path="password"/>
		<acme:password code="organiser.userAccount.confirmPassword" path="confirmPassword"/>
	</fieldset>
	<fieldset>
		<legend align="left">
			<spring:message code="organiser.personalInfo" />
		</legend>
			
		<acme:textbox code="organiser.name" path="name"/>
		<acme:textbox code="organiser.surname" path="surname"/>
		<acme:textbox code="organiser.emailAddress" path="emailAddress"/>
		<acme:textbox code="organiser.contactPhone" path="contactPhone"/>
		<acme:textbox code="organiser.url" path="url"/>
		<acme:textbox code="organiser.birthDate" path="birthDate"/>
		<acme:textbox code="organiser.nationality" path="nationality"/>
	</fieldset>
	
	<fieldset>
		<legend align="left">
			<spring:message code="organiser.creditCard" />
		</legend>
			
		<acme:textbox code="organiser.credit.holderName" path="creditCard.holderName"/>
		<acme:textbox code="organiser.credit.brandName" path="creditCard.brandName"/>
		<acme:textbox code="organiser.credit.number" path="creditCard.number"/>
		<acme:textbox code="organiser.credit.expirationMonth" path="creditCard.expirationMonth"/>
		<acme:textbox code="organiser.credit.expirationYear" path="creditCard.expirationYear"/>
		<acme:textbox code="organiser.credit.cvvCode" path="creditCard.cvvCode"/>
	</fieldset>	
	
	<p><acme:checkbox code="organiser.accepConditions" path="accepConditions"/>
	<a onClick="condiciones();" style="cursor:pointer;">(About)</a>
	</p>
	
	<input type="submit" name="save" value="<spring:message code="organiser.save"/>" />
	&nbsp;
	<input type="button" name="cancel" value="<spring:message code="organiser.cancel"/>" 
	onclick="javascript: window.location.replace('/AcmeEssay')"/>
</form:form>