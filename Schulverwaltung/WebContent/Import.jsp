<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Helpers.*" %>
    <%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>
<title>Import</title>
</head>
<body>
	<form action="Import.jsp" method="post" enctype="multipart/form-data">
	<%
		String fileName = request.getParameter("excelFile");
		if(fileName != null && !fileName.isEmpty())
		{
			out.write(fileName + "; Test");
			// ImportHelper.Import("C:\Documents\Schulverwaltung_Projekt");
		}
		ImportHelper.Import("C:\\Documents\\Schulverwaltung_Projekt\\it_excel5.xls");
%>
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
			<td align="center" style="background: lightBlue; width: 100%; height: 550px">
				<div style="background: lightBlue; width: 100%;">
					<input type="file" name="excelFile" />
					<input type="submit" value="Importieren" />
				</div>
			</td>
		</tr>
	</table>
	</center>
	</form>
</body>
</html>