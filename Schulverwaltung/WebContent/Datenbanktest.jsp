<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Database.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		try
		{
			Database db = new Database();
			String command = request.getParameter("txt_script");
			out.print(db.getObject(command));
			db.closeConnection();
			out.print(Integer.parseInt(null));
		}
		catch(Exception ex)
		{
			out.write(ex.getMessage());
		}
	%>
<form method="post" action="Datenbanktest.jsp">
	<Label>Datenbankscript: </Label> &nbsp; &nbsp; <input type="text" name="txt_script" /><br />
	<input type="submit" value="Abschicken" />
</form>
</body>
</html>