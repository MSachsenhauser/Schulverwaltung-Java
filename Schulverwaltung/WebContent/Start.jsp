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
	String nickName = request.getParameter("txt_nickname");
	String password = request.getParameter("txt_password");
	if(nickName != "" && password != "")
	{
		if(nickName == "Michael" || nickName == "Nicole0")
		{
			if(password == "asdfg")
			{
				out.write("passt");		
			}
			else
			{
				out.write("<b>Passwort falsch</b>");
			}
		}
		else
		{
			out.write("<b>Nickname falsch</b>");
		}
	}
%>
<form method="post" action="Start.jsp">
	<Label>Nickname</Label> &nbsp; &nbsp; <input type="text" name="txt_nickname" /><br />
	<Label>Passwort</Label> &nbsp; &nbsp; <input type="password" name="txt_password" /><br />
	<input type="submit" value="Login" />
</form>
</body>
</html>