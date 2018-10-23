<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<html>
<head>

	<title>Пользователи</title>
	<style type="text/css">
		.tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}
		.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}
		.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}
		.tg .tg-4eph{background-color:#f9f9f9}
        .redText  {color:red}
	</style>
</head>
<body>
<h1>
	Добавить пользователя
</h1>
<c:url var="addAction" value="/person/add" ></c:url>
<form:form action="${addAction}" modelAttribute="person" method="post">
<table>

	<tr>
		<td>
			Имя
		</td>
		<td>

            <form:input type="text" path="name"  name = "name"/>
		</td>
        <td> <form:errors path="name" class="redText" /></td>
	</tr>
	<tr>
		<td>
			Фамилия
		</td>
		<td>
            <form:input type="text" path="surname"  name = "surname"/>
		</td>
        <td> <form:errors path="surname" class="redText" /></td>
	</tr>
	<tr>
		<td>
		Email
		</td>
		<td>
            <form:input type="text" path="email"  name = "email"/>
		</td>
        <td> <form:errors path="email" class="redText" /></td>
	</tr>
	<tr>
		<td>
			Дата
		</td>
		<td>
	<form:input type="date" path="date" class= "date" name = "date" value = ""/>
		</td>
        <td> <form:errors path="date" class="redText" /></td>
	</tr>
	<tr>
		<td>
			Пароль
		</td>
		<td>
			<form:password path="pass"  name = "pass"/>
		</td>
        <td> <form:errors path="pass" class="redText" /></td>
	</tr>
	<tr>
		<td>
			Повторите Пароль
		</td>
		<td>
			<form:password path="pass1"  name = "pass1"/>
		</td>
		<td> <form:errors  class="redText" /></td>
	</tr>
	<tr>
		<td colspan="2">
				<input type="submit" value="Добавить пользователя" />
		</td>
	</tr>
</form:form>
<form action="/persons/search">
	<tr>
		<td>
    <label for="searchemail">Поиск:</label>
		</td>
		<td>
				<%String searchEmail=request.getParameter("searchemail"); if(searchEmail==null){searchEmail="";}%>
    <input type="text" id="searchemail" name="searchemail" placeholder="email" value="<%=searchEmail%>"/>
	</td>
	</tr>
	<tr>
		<td>
    <input type="submit" value="Поиск"/>
	</td>
	</tr>
</form>
</table>
<br>
<h3>Список пользователей</h3>
<c:if test="${!empty listPersons}">
	<table class="tg">
	<tr>
		<th width="120">Имя</th>
		<th width="120">Фамилия</th>
		<th width="120">Email</th>
		<th width="120">Дата рождения</th>
		<th width="60">Удаление</th>
	</tr>
	<c:forEach items="${listPersons}" var="person">
		<tr>
			<td>${person.name}</td>
			<td>${person.surname}</td>
			<td>${person.email}</td>
			<td>${person.date}</td>
			<td><a href="<c:url value='/person/remove/${person.id}' />" >Удалить</a></td>
		</tr>
	</c:forEach>
	</table>
</c:if>
</body>
</html>
