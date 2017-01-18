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

<form:form action="author/register.do" modelAttribute="authorForm">

	<fieldset>
		<legend align="left">
			<spring:message code="author.userAccount" />
		</legend>	
		<acme:textbox code="author.userAccount.username" path="userName"/>
		<acme:password code="author.userAccount.password" path="password"/>
		<acme:password code="author.userAccount.confirmPassword" path="confirmPassword"/>
	</fieldset>
	<fieldset>
		<legend align="left">
			<spring:message code="author.personalInfo" />
		</legend>
		
		<acme:textbox code="author.name" path="name"/>
		<acme:textbox code="author.surname" path="surname"/>
		<acme:textbox code="author.emailAddress" path="emailAddress"/>
		<acme:textbox code="author.contactPhone" path="contactPhone"/>
		<acme:textbox code="author.url" path="url"/>
		<acme:textbox code="author.birthDate" path="birthDate"/>
		<acme:textbox code="author.nationality" path="nationality"/>
	</fieldset>	
	
	<fieldset>	
		<legend align="left">
		<spring:message code="author.creditCard" />
		</legend>
				
		<acme:textbox code="author.creditCard.holderName" path="creditCard.holderName"/>
		<acme:textbox code="author.creditCard.brandName" path="creditCard.brandName"/>
		<acme:textbox code="author.creditCard.number" path="creditCard.number"/>
		<acme:textbox code="author.creditCard.expirationMonth" path="creditCard.expirationMonth"/>
		<acme:textbox code="author.creditCard.expirationYear" path="creditCard.expirationYear"/>
		<acme:textbox code="author.creditCard.cvvCode" path="creditCard.cvvCode"/>	
	</fieldset>	
	
	<p><acme:checkbox code="author.accepConditions" path="accepConditions"/>
	<a onClick="condiciones();" style="cursor:pointer;">(About)</a>
	</p>
	
	<acme:submit name="save" code="author.save"/>
	<acme:cancel code="author.cancel" url="/AcmeEssay"/>
</form:form>