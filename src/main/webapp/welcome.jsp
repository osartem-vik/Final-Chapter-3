<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Тест з швейного виробництва</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <h1>Ласкаво просимо до тесту!</h1>
    <p>Цей тест призначений для перевірки знань студентів із швейного виробництва. Вам буде запропоновано 5 питань,
        випадково обраних із бази. Кожне питання супроводжується зображенням. Уважно аналізуйте зображення та обирайте
        правильну відповідь. Якщо ви дасте 2 неправильні відповіді, тест буде завершено. Успіхів!</p>
    <form action="start" method="post">
        <label>Номер групи:</label>
        <input type="text" name="groupNumber" required><br>
        <label>ПІБ:</label>
        <input type="text" name="fullName" required><br>
        <input type="submit" value="Почати тест">
    </form>
</div>
</body>
</html>