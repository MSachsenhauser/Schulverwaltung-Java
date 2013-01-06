<%@page import="org.apache.catalina.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="Database.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body onLoad="document.getElementById('nickname').focus()">

<form method="post" action="LoginServlet">
	<table style="width: 100%; height: 100%">
		<tr>
			<td align="center">
				<img src="Images/logo.jpg" />
			</td>
		</tr>
		<tr>
			<td align="center">
				<table>
					<tr>
						<td><label>Benutzername: </label></td>
						<td><input type="text" name="txt_nickname" id="nickname" value="<%= request.getParameter("txt_nickname") != null ? request.getParameter("txt_nickname") : "" %>" /></td>
					</tr>
					<tr>
						<td><label>Passwort: </label></td>
						<td><input type="password" name="txt_password" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="Anmelden" /></td>
					</tr>
					<tr>
						<td colspan="2">
							<label style="color: red" id="txt_Error">
							<% 
								try
								{
									LoginState curLoginState = (LoginState)request.getAttribute("LoginState");
									switch(curLoginState)
									{
										case LoggedIn:
											out.write("<script language='javascript' type='text/javascript'>" + 
												      "window.location.href = 'main.jsp';" +
													  "</script>"); 
											break;
										case WrongUserNameOrPassword: out.write("Falsche Logindaten"); break;
										case NoUserName: out.write("Kein Benutzername"); break;
										case NoPassword: out.write("Kein Passwort"); break;
										default:
											out.write("Es ist ein Fehler aufgetreten");
											break;
									}
								}
								catch(Exception ex)
								{
									out.write(ex.getMessage());
								}
							%>
							</label>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</html>