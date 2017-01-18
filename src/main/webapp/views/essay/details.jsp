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


<form:form action="${requestURI}" modelAttribute="essay">
<jstl:if  test="${isExpired}">
	<jstl:if test="${!create}">
		<acme:formOut code="essay.title" path="title" value="${essay.title}" />
		<acme:formOut code="essay.abstractEss" path="abstractEss" value="${essay.abstractEss}" />
		<form:label path="submissionDate">
			<b><spring:message code="essay.submissionDate"/>:</b>
		</form:label>
		<fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${essay.submissionDate}" />
		<br/>
		<acme:formOut code="essay.published" path="published" value="${essay.published}" />
		<acme:formOut code="essay.contents" path="contents" value="${essay.contents}" />
	</jstl:if>
	<security:authorize access="!isAnonymous()">
		<spring:message code="essay.expiredEssay"/>
	</security:authorize>
</jstl:if>
<jstl:if   test="${!isExpired}">
	<form:hidden path="id"/>
	<form:hidden path="author"/>
	<form:hidden path="contest"/>
	<acme:textbox code="essay.title" path="title"/>
	<acme:textarea code="essay.abstractEss" path="abstractEss"/>
	<form:hidden path="submissionDate"/>	
	<form:label path="submissionDate">
		<b><spring:message code="essay.submissionDate"/>:</b>
	</form:label>
	<fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${essay.submissionDate}" />
	<br/>	
	<acme:formOut code="essay.published" path="published" value="${essay.published}" />
	<acme:textarea code="essay.contents" path="contents"/>
	<acme:submit code="essay.save" name="save"/>
</jstl:if>
	<security:authorize access="hasRole('AUTHOR')">	
		<input type="button" name="cancel" value="<spring:message code="essay.cancel"/>" 
		onclick="javascript: window.location.replace('${replace}')"/>
	</security:authorize>
	
	<security:authorize access="isAnonymous()">
		<input type="button" name="cancel" value="<spring:message code="essay.cancel"/>" 
		onclick="javascript: window.location.replace('${replace}')"/>
	</security:authorize>
</form:form>
