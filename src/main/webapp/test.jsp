<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Тест: Швейне виробництво</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="${sessionScope.testState.lastAnswerCorrect ? 'correct' : 'incorrect'}">
<div class="container">
    <h1>Тест для ${sessionScope.testState.fullName} (група ${sessionScope.testState.groupNumber})</h1>
    <p>Спроба №${sessionScope.testState.attempts}</p>
    <c:if test="${sessionScope.testState.currentQuestionIndex < 5 && sessionScope.testState.wrongAnswers < 2}">
        <p>Питання ${sessionScope.testState.currentQuestionIndex + 1} з 5: ${com.example.util.TestLogic.getQuestion(sessionScope.testState)}</p>
        <div class="progress-bar">
            <div class="progress" style="width: ${(sessionScope.testState.currentQuestionIndex + 1) * 20}%"></div>
        </div>
        <c:set var="imagePath" value="${com.example.util.TestLogic.getImage(sessionScope.testState)}"/>
        <script>
            console.log("Dynamic image path: '${imagePath}'");
            console.log("Full URL: '${pageContext.request.contextPath}/${imagePath}'");
        </script>
        <c:if test="${not empty imagePath}">
            <img src="${pageContext.request.contextPath}/${imagePath}" alt="Зображення питання"
                 onerror="console.log('Image failed to load: ${pageContext.request.contextPath}/${imagePath}'); this.src='${pageContext.request.contextPath}/images/placeholder.png';"/>
        </c:if>
        <c:if test="${empty imagePath}">
            <p style="color: red;">Зображення для питання не знайдено! Шлях: ${imagePath}</p>
                    </c:if>
        <form action="test" method="post">
            <c:forEach var="option" items="${com.example.util.TestLogic.getOptions(sessionScope.testState)}">
                <label>
                    <input type="radio" name="answer" value="${option}" required> ${option}
                </label>
            </c:forEach>
            <input type="submit" value="Відповісти">
        </form>
    </c:if>
    <c:if test="${sessionScope.testState.currentQuestionIndex >= 5 || sessionScope.testState.wrongAnswers >= 2}">
        <p>Тест завершено! <a href="result.jsp" class="button">Перейти до результатів</a></p>
    </c:if>
</div>
</body>
</html>