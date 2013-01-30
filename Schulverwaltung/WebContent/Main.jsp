<%@page import="org.apache.catalina.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="database.*" %>
    <%@page import="helpers.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Startseite</title>
<link rel="stylesheet" href="Styles/Themes/redmond/jquery-ui.css" />
<script language="javascript" type="text/javascript" src="Scripts/jquery.js"></script>
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<%
	try
	{
		Login curLogin = (Login)request.getSession().getAttribute("Login");
		curLogin.doLogin();
		if(curLogin.getState() != LoginState.LoggedIn)
		{
	out.write("<script language='javascript' type='text/javascript'>document.location = 'Start.jsp';</script>"); 
		}
	}
	catch(Exception ex)
	{
		out.write(ex.getMessage());
		out.write("<script language='javascript' type='text/javascript'>document.location = 'Start.jsp';</script>");
	}
%>
<form method="post" action="LoginServlet">
<center>
	<table>
		<tr>
			<td align="center">
				<img src="Images/logo.jpg" />
			</td>
		</tr>
		<tr>
			<td align="center">
				<%
				
					out.write(MenuHelper.GenerateTopMenu("Main.jsp"));
				%>
			</td>
		</tr>
		<tr>
			<td align="center" style="background: lightBlue; width: 100%; height: 550px">
				<div style="background: lightBlue; width: 100%; height: 100%">
				
				</div>
			</td>
		</tr>
	</table>
</form>
</center>
</body>
</html>