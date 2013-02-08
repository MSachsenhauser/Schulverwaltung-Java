<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="database.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../Scripts/jquery.js"></script>
    <script src="../Scripts/jquery-ui.js"></script>	
    <script type="text/javascript" src="../Scripts/Detail.js"></script>
    <script type="text/javascript" src="../Scripts/Validation.js"></script>
</head>
<body>
<%
		String id = request.getParameter("Id");
		Login curLogin = new Login().setUserName(id.equals("-1") ? "" : id);
		if(!curLogin.getUserName().isEmpty())
		{
			curLogin.load();
		}
	%>
	<form id="form" method="Post" action="../LoginDetailServlet">
	<input type="hidden" name="Id" value="<%= curLogin.getUserName() %>" />
	<table>
		<tr>
			<td>
				<label>Username: </label>
			</td>
			<td>
				<input <%=curLogin.getUserName().isEmpty() ? "" : "readonly=readonly"%> type="text" required name="Username" style="width: 100px" value="<%= curLogin.getUserName() %>"/>
			</td>
			<td>
				<label>Email: </label>
			</td>
			<td>
				<input type="text" name="Email" style="width: 200px" value="<%= curLogin.getEmail() %>"/>
			</td>
		</tr>
		<tr>
		<td>
				<label>Passwort: </label>
			</td>
			<td>
				<input type="password" name="Password" style="width: 200px" value="<%= curLogin.getPassword() %>"/>
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="speichern" />
			</td>
		</tr>
	</table>
	</form>
</body>
</html>