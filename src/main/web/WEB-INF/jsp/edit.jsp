<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.matuageorge.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <%--Explanatory  note--%>
    <p>Редактировать резюме</p>
    <p><strong>UUID ${resume.uuid}</strong></p>
    <p>Заполните или обновите поля и нажмите кнопку <strong>сохранить</strong> внизу</p>
    <%--Explanatory  note--%>
    <%--Resume Full Name--%>
    <form action="/action_page.php">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullNameInput" value="${resume.fullName}"><br><br>
        <%--Resume Full Name--%>
        <%--Resume Contacts Section--%>
        <p>
            <c:forEach var="contact" items="${resume.contacts}">
                <c:choose>
                    <c:when test="${contact.key=='SKYPE'}">
                        <img src="<c:url value='/img/skype.png'/>"
                             alt="Skype Contact" width="18" height="18">
                        : <label for="skypeContact"></label><input type="text" id="skypeContact"
                                                                   name="skypeContactInput"
                                                                   value="${contact.value}"><br>
                    </c:when>
                    <c:when test="${contact.key=='EMAIL'}">
                        <img src="<c:url value='/img/email.png'/>"
                             alt="Email Contact" width="18" height="18">
                        : <label for="emailContact"></label><input type="text" id="emailContact"
                                                                   name="emailContactInput"
                                                                   value="${contact.value}"><br>
                    </c:when>
                    <c:when test="${contact.key=='LINKEDIN'}">
                        <img src="<c:url value='/img/linkedin.png'/>" alt="Linked Contact"
                             width="18" height="18">
                        : <label for="linkedinContact"></label><input type="text" id="linkedinContact"
                                                                      name="linkedinContactInput"
                                                                      value="${contact.value}"><br>
                    </c:when>
                    <c:when test="${contact.key=='GITHUB'}">
                        <img src="<c:url value='/img/github.png'/>" alt="Github Contact" width="18"
                             height="18">
                        : <label for="githubContact"></label><input type="text" id="githubContact"
                                                                    name="githubContactInput"
                                                                    value="${contact.value}"><br>
                    </c:when>
                    <c:when test="${contact.key=='STACKOVERFLOW'}">
                        <img src="<c:url value='/img/stack_overflow.png'/>" alt="Github Contact" width="18"
                             height="18">
                        : <label for="stackoverflowContact"></label><input type="text" id="stackoverflowContact"
                                                                           name="stackoverflowContactInput"
                                                                           value="${contact.value}"><br/>
                    </c:when>
                    <c:when test="${contact.key=='HOMEPAGE'}">
                        <img src="<c:url value='/img/home_page.png'/>" alt="Github Contact" width="18"
                             height="18">
                        : <label for="homepageContact"></label><input type="text" id="homepageContact"
                                                                      name="homepageContactInput"
                                                                      value="${contact.value}"><br/>
                    </c:when>
                    <c:otherwise>
                        <img src="<c:url value='/img/phone.png'/>" alt="Phone Contact" width="18"
                             height="18">
                        : <label for="phoneContact"></label><input type="text" id="phoneContact"
                                                                   name="phoneContactInput"
                                                                   value="${contact.value}"><br/>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </p>
        <%--Resume Contacts Section--%>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>