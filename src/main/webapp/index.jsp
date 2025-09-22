<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Тест з швейного виробництва</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Ласкаво просимо до тесту!</h1>
<p>Цей тест призначений для перевірки знань студентів швейного коледжу. </p>
<p>Вам буде запропоновано відповісти на 5 питань.</p>
<p>Кожне питання супроводжується зображенням.</p>
<p>Уважно аналізуйте зображення та обирайте правильну відповідь.</p>
</p>Якщо ви дасте 2 неправильні відповіді, тест буде провалено. Успіхів!</p>
<form action="start" method="post">
    <label>Номер групи:</label>
    <input type="text" name="groupNumber" required><br>
    <label>ПІБ:</label>
    <input type="text" name="fullName" required><br>
    <input type="submit" value="Почати тест">
</form>
</body>
</html>