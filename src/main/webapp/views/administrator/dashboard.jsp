<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- DASHBOARD LEVEL A -->
<fieldset>
	<legend align="left">
		<spring:message code="dashboard.fieldset1"></spring:message>
	</legend>
	<display:table name="contestDesc" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="contest.name" var="name"/>
		<display:column property="name" title="${name}" sortable="false" />
		
		<spring:message code="contest.description" var="description"/>
		<display:column property="description" title="${description}" sortable="false" />
		
		<spring:message code="contest.holdingDate" var="holdingDate"/>
		<display:column property="holdingDate" title="${holdingDate}" sortable="false" />
		
		<spring:message code="contest.deadLine" var="deadline"/>
		<display:column property="deadline" title="${deadline}" sortable="false" />
		
		<spring:message code="contest.result" var="result"/>
		<display:column property="result" title="${result}" sortable="false" />
	</display:table>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="dashboard.fieldset2"></spring:message>
	</legend>
	<display:table name="authorsMoreEssSubmit" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="author.name" var="name"/>
		<display:column property="name" title="${name}" sortable="false" />
		
		<spring:message code="author.surname" var="surname"/>
		<display:column property="surname" title="${surname}" sortable="false" />
		
		<spring:message code="author.emailAddress" var="emailAddress"/>
		<display:column property="emailAddress" title="${emailAddress}" sortable="false" />
		
		<spring:message code="author.contactPhone" var="contactPhone"/>
		<display:column property="contactPhone" title="${contactPhone}" sortable="false" />
		
		<spring:message code="author.url" var="url"/>
		<display:column property="url" title="${url}" sortable="false" />
		
		<spring:message code="author.birthDate" var="birthDate"/>
		<display:column property="birthDate" title="${birthDate}" sortable="false" />
		
		<spring:message code="author.nationality" var="nationality"/>
		<display:column property="nationality" title="${nationality}" sortable="false" />
	</display:table>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="dashboard.fieldset3"></spring:message>
	</legend>
	<display:table name="authorsMoreEssPublished" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="author.name" var="name"/>
		<display:column property="name" title="${name}" sortable="false" />
		
		<spring:message code="author.surname" var="surname"/>
		<display:column property="surname" title="${surname}" sortable="false" />
		
		<spring:message code="author.emailAddress" var="emailAddress"/>
		<display:column property="emailAddress" title="${emailAddress}" sortable="false" />
		
		<spring:message code="author.contactPhone" var="contactPhone"/>
		<display:column property="contactPhone" title="${contactPhone}" sortable="false" />
		
		<spring:message code="author.url" var="url"/>
		<display:column property="url" title="${url}" sortable="false" />
		
		<spring:message code="author.birthDate" var="birthDate"/>
		<display:column property="birthDate" title="${birthDate}" sortable="false" />
		
		<spring:message code="author.nationality" var="nationality"/>
		<display:column property="nationality" title="${nationality}" sortable="false" />
	</display:table>
</fieldset>


<fieldset>
	<legend align="left">
		<spring:message code="dashboard.fieldset4"></spring:message>
	</legend>
	<display:table name="authorsLessEssPublished" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="author.name" var="name"/>
		<display:column property="name" title="${name}" sortable="false" />
		
		<spring:message code="author.surname" var="surname"/>
		<display:column property="surname" title="${surname}" sortable="false" />
		
		<spring:message code="author.emailAddress" var="emailAddress"/>
		<display:column property="emailAddress" title="${emailAddress}" sortable="false" />
		
		<spring:message code="author.contactPhone" var="contactPhone"/>
		<display:column property="contactPhone" title="${contactPhone}" sortable="false" />
		
		<spring:message code="author.url" var="url"/>
		<display:column property="url" title="${url}" sortable="false" />
		
		<spring:message code="author.birthDate" var="birthDate"/>
		<display:column property="birthDate" title="${birthDate}" sortable="false" />
		
		<spring:message code="author.nationality" var="nationality"/>
		<display:column property="nationality" title="${nationality}" sortable="false" />
	</display:table>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="dashboard.fieldset5"></spring:message>
	</legend>
	<table class="displaytag">
		<thead>
		<tr>
			<th><spring:message code="author.name"/></th>
			<th><spring:message code="avgNumberEssaysSubmitted"></spring:message></th>
		</tr>
		</thead>
		
		<tbody>
		<jstl:forEach items="${avgNumberEssaysSubmitted}" var="avg">
			<tr>
				<jstl:forEach items="${avg}" var="aux">
					<td><jstl:out value="${aux}"/></td>
				</jstl:forEach>
			</tr>
		</jstl:forEach>
		</tbody>
	</table>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="dashboard.fieldset6"></spring:message>
	</legend>
	<table class="displaytag">
		<thead>
		<tr>
			<th><spring:message code="author.name"/></th>
			<th><spring:message code="avgContestOrganisedByOrganiser"></spring:message></th>
		</tr>
		</thead>
		
		<tbody>
		<jstl:forEach items="${avgContestOrganisedByOrganiser}" var="avg">
			<tr>
				<jstl:forEach items="${avg}" var="aux">
					<td><jstl:out value="${aux}"/></td>
				</jstl:forEach>
			</tr>
		</jstl:forEach>
		</tbody>
	</table>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="dashboard.fieldset7"></spring:message>
	</legend>
	<display:table name="contestsHeldLastMonth" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="contest.name" var="name"/>
		<display:column property="name" title="${name}" sortable="false" />
		
		<spring:message code="contest.description" var="description"/>
		<display:column property="description" title="${description}" sortable="false" />
		
		<spring:message code="contest.holdingDate" var="holdingDate"/>
		<display:column property="holdingDate" title="${holdingDate}" sortable="false" />
		
		<spring:message code="contest.deadLine" var="deadline"/>
		<display:column property="deadline" title="${deadline}" sortable="false" />
		
		<spring:message code="contest.result" var="result"/>
		<display:column property="result" title="${result}" sortable="false" />
	</display:table>
</fieldset>

<!-- falta uno -->
<fieldset>
	<legend align="left">
		<spring:message code="dashboard.contestsWithPublicSessions"></spring:message>
	</legend>
	<jstl:forEach items="${contests}" var="temp">
		<b><spring:message code="contest.name"/>:</b>
		<jstl:out value="${temp.name}" /><br/>
		
		<b><spring:message code="contest.description"/>:</b>
		<jstl:out value="${temp.description}" /><br/>
		<jstl:if test="${empty temp.publicSessions}">
			<spring:message code="contest.noPublicSession"/><br/>
		</jstl:if>
		<jstl:if test="${fn:length(temp.publicSessions) gt 0}">
			<table class="displaytag">
				<thead>
				<tr>
					<th><spring:message code="session.startMoment"/></th>
					<th><spring:message code="session.endMoment"/></th>
					<th><spring:message code="session.capacity" /></th>
					<th><spring:message code="session.chairman" /></th>
				</tr>
				</thead>
		
				<tbody>
				<jstl:forEach items="${temp.publicSessions}" var="avg">
					<tr>
						<td><jstl:out value="${avg.startMoment}"/></td>
						<td><jstl:out value="${avg.endMoment}"/></td>
						<td><jstl:out value="${avg.capacity}"/></td>
						<td><jstl:out value="${avg.organiser.name}"/></td>
					</tr>
				</jstl:forEach>
			</tbody>
		</table>
	</jstl:if>
		
	</jstl:forEach>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="dashboard.chairmans"></spring:message>
	</legend>
	<table class="displaytag">
		<thead>
		<tr>
			<th><spring:message code="organiser.name"/></th>
			<th><spring:message code="sessionsNumber"></spring:message></th>
		</tr>
		</thead>
		
		<tbody>
		<jstl:forEach items="${chairmans}" var="avg">
			<tr>
				<jstl:forEach items="${avg}" var="aux">
					<td><jstl:out value="${aux}"/></td>
				</jstl:forEach>
			</tr>
		</jstl:forEach>
		</tbody>
	</table>
</fieldset>

<fieldset>
	<legend align="left">
		<spring:message code="dashboard.publicSessions"></spring:message>
	</legend>
	<display:table name="publicSessions" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
		<spring:message code="session.startMoment" var="startMoment"></spring:message>
		<display:column property="startMoment" title="${startMoment}" sortable="false"></display:column>
		
	 	<spring:message code="session.endMoment" var="endMoment"></spring:message>
		<display:column property="endMoment" title="${endMoment}" sortable="false"></display:column>
		
		<spring:message code="session.capacity" var="capacity"></spring:message>
		<display:column property="capacity" title="${capacity}" sortable="true"></display:column>	
	
		<spring:message code="session.chairman" var="organiser.name"></spring:message>
		<display:column property="organiser.name" title="Chairman" sortable="false"></display:column>
	</display:table>
</fieldset>