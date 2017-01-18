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

 <fieldset>
	<legend align="left">
		<spring:message code="customers.fieldset1"/>
	</legend>
	<display:table name="organisers" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="customer.view" var="action"></spring:message>
			<display:column title="view">
				<a href="customer/administrator/profile.do?customerId=${row.id}"><spring:message code="customer.details"/></a>
				<br/>
			</display:column>
	
		<spring:message code="customer.name" var="name"/>
		<display:column property="name" title="${name}" sortable="false" />
		
		<spring:message code="customer.surname" var="surname"/>
		<display:column property="surname" title="${surname}" sortable="false" />
		
		<spring:message code="customer.email" var="emailAddress"/>
		<display:column property="emailAddress" title="${emailAddress}" sortable="false" />
		
		<spring:message code="customer.phone" var="contactPhone"/>
		<display:column property="contactPhone" title="${contactPhone}" sortable="false" />
		
		<spring:message code="customer.birthDate" var="birthDate"/>
		<display:column property="birthDate" title="${birthDate}" sortable="false" />
		
		<spring:message code="customer.nationality" var="nationality"/>
		<display:column property="nationality" title="${nationality}" sortable="false" />
	</display:table>
</fieldset>


 <fieldset>
	<legend align="left">
		<spring:message code="customers.fieldset2"/>
	</legend>
	<display:table name="authors" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="customer.view" var="action"></spring:message>
			<display:column title="view">
				<a href="customer/administrator/profile.do?customerId=${row.id}"><spring:message code="customer.details"/></a>
				<br/>
			</display:column>
	
		<spring:message code="customer.name" var="name"/>
		<display:column property="name" title="${name}" sortable="false" />
		
		<spring:message code="customer.surname" var="surname"/>
		<display:column property="surname" title="${surname}" sortable="false" />
		
		<spring:message code="customer.email" var="emailAddress"/>
		<display:column property="emailAddress" title="${emailAddress}" sortable="false" />
		
		<spring:message code="customer.phone" var="contactPhone"/>
		<display:column property="contactPhone" title="${contactPhone}" sortable="false" />
		
		<spring:message code="customer.birthDate" var="birthDate"/>
		<display:column property="birthDate" title="${birthDate}" sortable="false" />
		
		<spring:message code="customer.nationality" var="nationality"/>
		<display:column property="nationality" title="${nationality}" sortable="false" />
	</display:table>
</fieldset>