<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="helpers.*" %>
    <%@page import="database.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>
<title>Import</title>
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
		String filter = request.getParameter("Filter") != null ? request.getParameter("Filter") : "";
		Boolean showDisabled = request.getParameter("ShowDisabled") != null ? 
					   Boolean.parseBoolean(request.getParameter("ShowDisabled")) : 
					   false;
%>
	<form action="ImportServlet" method="post" enctype="multipart/form-data">
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
							out.write(MenuHelper.GenerateTopMenu("Import.jsp"));
						%>
					</td>
				</tr>
				<tr>
					<td align="center" valign="top" style="background: lightBlue; width: 100%; height: 550px">
						<div style="background: lightBlue; width: 100%;">
						<br/><br/>
							<label><%= request.getAttribute("State") != null ? request.getAttribute("State") : "" %></label></br>
							<label>Excel Import </label>
							<input type="file" name="excelFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"/>
							<input type="submit" value="Importieren" />
						</div>
					</td>
				</tr>
			</table>
		</center>
	</form>
</body>
</html>