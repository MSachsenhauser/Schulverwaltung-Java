<%@page import="org.apache.catalina.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Database.*" %>
    <%@page import="model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Startseite</title>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script language="javascript" type="text/javascript" src="Scripts/jquery.js"></script>
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<% 
	try
	{
		LoginBean curLogin = (LoginBean)request.getSession().getAttribute("Login");
		curLogin.DoLogin();
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
				<div>
					<table>
						<tr>
							<td class="SelectedMenuItem"><a href="main.jsp">Start</</a></td>
							<td class="MenuItem"><a href="Students.jsp">Schüler</</a></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td align="center" style="background: lightBlue; width: 100%; height: 450px">
				<div style="background: lightBlue; width: 100%; height: 100%">
				
				</div>
			</td>
		</tr>
	</table>
</form>
</center>
</body>
</html>