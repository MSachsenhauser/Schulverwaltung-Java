<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<%
try
{
	out.write(request.getAttribute("LoginState").toString());
}
catch(Exception ex)
{}
%>
<form method="post" action="LoginServlet">
	<Label>Nickname: </Label> &nbsp; &nbsp; <input type="text" name="txt_nickname" /><br />
	<Label>Passwort: </Label> &nbsp; &nbsp; <input type="password" name="txt_password" /><br />
	<input type="submit" value="Login" />
</form>
</body>
</html>