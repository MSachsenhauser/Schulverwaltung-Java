<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Database.*" %>
    <%@page import="model.*" %>
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
	LoginState curLoginState = (LoginState)request.getAttribute("LoginState");
	switch(curLoginState)
	{
		case LoggedIn: break;
		case WrongUserNameOrPassword: break;
		case NoUserName: break;
		case NoPassword: break;
	}
}
catch(Exception ex)
{
	out.write(ex.getMessage());
}
%>
<form method="post" action="LoginServlet">
	<Label>Nickname: </Label> &nbsp; &nbsp; <input type="text" name="txt_nickname" /><br />
	<Label>Passwort: </Label> &nbsp; &nbsp; <input type="password" name="txt_password" /><br />
	<input type="submit" value="Login" />
</form>
</body>
</html>