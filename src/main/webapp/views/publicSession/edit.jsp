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

<form:form action="publicSession/organiser/edit.do" modelAttribute="publicSession">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="essays"/>
	<form:hidden path="contest"/>

	<acme:textbox code="session.startMoment" path="startMoment"/>
	<acme:textbox code="session.endMoment" path="endMoment"/>
	<acme:textbox code="session.capacity" path="capacity"/>
	
	<acme:select code="session.chairman" items="${organisers}" itemLabel="name" path="organiser"/>

	<acme:submit code="session.save" name="save"/>
	
<%--<jstl:if test="${session.id!=0}">
		<acme:submit code="session.delete" name="delete"/>
	</jstl:if>--%>
	
	<input type="button" name="cancel" value="<spring:message code="session.cancel"/>" 
	onclick="javascript: window.location.replace('contest/organiser/list.do')"/>
	
</form:form>