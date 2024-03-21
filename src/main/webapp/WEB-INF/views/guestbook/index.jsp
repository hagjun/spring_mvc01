<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/GuestBookController" method="post">
		<input type="hidden" name="cmd" value="list">
		<input type="submit" value="방명록2">
	</form>
</body>
</html>