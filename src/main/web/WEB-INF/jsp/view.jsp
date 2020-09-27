<%@ page import="com.matuageorge.webapp.model.ListSection" %>
<%@ page import="com.matuageorge.webapp.model.OrganizationSection" %>
<%@ page import="com.matuageorge.webapp.model.TextSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.matuageorge.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<%--Resume ID--%>
<p>Resume ID: ${resume.uuid}</p>
<%--Resume ID--%>
<%--Resume Full Name--%>
<p><strong>${resume.fullName}</strong></p>
<%--Resume Full Name--%>
<%--Resume Contacts Section--%>
<p>
    <c:forEach var="contact" items="${resume.contacts}">
        <c:choose>
            <c:when test="${contact.key=='SKYPE'}">
                <img src="<c:url value='/img/skype.png'/>"
                     alt="Skype Contact" width="18" height="18">
                : <a href="skype:${contact.value}?call>">${contact.value}</a><br/>
            </c:when>
            <c:when test="${contact.key=='EMAIL'}">
                <img src="<c:url value='/img/email.png'/>"
                     alt="Email Contact" width="18" height="18">
                : <a href="mailto:${contact.value}>">${contact.value}</a><br/>
            </c:when>
            <c:when test="${contact.key=='LINKEDIN'}">
                <img src="<c:url value='/img/linkedin.png'/>" alt="Linked Contact"
                     width="18" height="18">
                : <a href="${contact.value}>">${contact.key}</a><br/>
            </c:when>
            <c:when test="${contact.key=='GITHUB'}">
                <img src="<c:url value='/img/github.png'/>" alt="Github Contact" width="18"
                     height="18">
                : <a href="${contact.value}>">${contact.key}</a><br/>
            </c:when>
            <c:when test="${contact.key=='STACKOVERFLOW'}">
                <img src="<c:url value='/img/stack_overflow.png'/>" alt="Github Contact" width="18"
                     height="18">
                : <a href="${contact.value}>">${contact.key}</a><br/>
            </c:when>
            <c:when test="${contact.key=='HOMEPAGE'}">
                <img src="<c:url value='/img/home_page.png'/>" alt="Github Contact" width="18"
                     height="18">
                : <a href="${contact.value}>">${contact.value}</a><br/>
            </c:when>
            <c:otherwise>
                <img src="<c:url value='/img/phone.png'/>" alt="Phone Contact" width="18"
                     height="18">
                : <a href="tel:${contact.value}>">${contact.value}</a><br/>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</p>
<%--Resume Contacts Section--%>

<%--Resume PERSONAL and OBJECTIVE Section--%>
<c:forEach var="sections" items="${resume.sections}">
    <c:set var="abstractSection" value="${sections.value}"/>
    <jsp:useBean id="abstractSection" type="com.matuageorge.webapp.model.AbstractSection"/>
    <c:choose>
        <c:when test="${sections.key=='PERSONAL' || sections.key=='OBJECTIVE'}">
            <p><strong>${sections.key.title}</strong></p>
            <p><%=((TextSection) abstractSection).getContent()%>
            </p>
        </c:when>
        <%--Resume ACHIEVEMENT and QUALIFICATIONS Section--%>
        <c:when test="${sections.key=='ACHIEVEMENT' || sections.key=='QUALIFICATIONS'}">
            <p><strong>${sections.key.title}</strong></p>
            <c:forEach var="item" items="<%=((ListSection) abstractSection).getItems()%>">
                <p>${item}</p>
            </c:forEach>
        </c:when>
        <%--Resume ACHIEVEMENT and QUALIFICATIONS Section--%>
        <%--Resume EXPERIENCE and EDUCATION Section--%>
        <c:when test="${sections.key=='EXPERIENCE' || sections.key=='EDUCATION'}">
            <p><strong>${sections.key.title}</strong></p>
            <c:forEach var="organization" items="<%=((OrganizationSection) abstractSection).getOrganizations()%>">
                <c:if test="${empty organization.homePage.url}">
                    <p>No homepage</p>
                </c:if>
                <c:if test="${not empty organization.homePage.url}">
                    <p><a href="${organization.homePage.url}">${organization.homePage.name}</a></p>
                </c:if>
                <c:forEach var="position" items="${organization.positions}">
                    <fmt:parseDate value="${position.startDate}" pattern="yyyy-MM-dd"
                                   var="startDateParsed" type="date"/>
                    <fmt:formatDate var="formattedStartDate" pattern="dd MMM yyyy" value="${startDateParsed}"/>
                    <fmt:parseDate value="${position.endDate}" pattern="yyyy-MM-dd"
                                   var="endDateParsed" type="date"/>
                    <fmt:formatDate var="formattedEndDate" pattern="dd MMM yyyy" value="${endDateParsed}"/>
                    <p><em>${formattedStartDate}
                        -
                            ${formattedEndDate}</em></p>
                    <ul>
                        <li>${position.title} - <a
                                href="${organization.homePage}">${organization.homePage}</a><br/>${position.description}
                        </li>
                    </ul>
                </c:forEach>
            </c:forEach>
        </c:when>
        <%--Resume EXPERIENCE and EDUCATION Section--%>
    </c:choose>
</c:forEach>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>