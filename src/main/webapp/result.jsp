<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Результат тесту</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
  <h1>${sessionScope.testState.fullName}, ваш результат:</h1>
  <p>Спроба №${sessionScope.attempts}</p>
  <p>${com.example.util.TestLogic.getResult(sessionScope.testState)}</p>
  <form action="restart" method="post">
    <input type="submit" value="Пройти тест ще раз">
  </form>
  <a href="welcome.jsp">Повернутися на початок</a>
</div>
</body>
</html>