<%@page import="org.apache.catalina.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Database.*" %>
    <%@page import="model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
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
						<td><label>Username: </label></td>
						<td><input type="text" name="txt_nickname" value="<%= request.getParameter("txt_nickname") != null ? request.getParameter("txt_nickname") : "" %>" /></td>
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
											out.write("<script language='javascript' type='text/javascript'>document.location = 'Berechnung.html';</script>"); 
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
	<!--  <Label>Nickname: </Label> &nbsp; &nbsp; <input type="text" name="txt_nickname" /><br />
	<Label>Passwort: </Label> &nbsp; &nbsp; <input type="password" name="txt_password" /><br />
	<input type="submit" value="Login" /> -->
	
</form>
</body>
</html>