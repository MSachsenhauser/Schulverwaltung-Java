<%@page import="org.apache.catalina.Session"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
    <%@page import="Database.*" %>
    <%@page import="model.*" %>
    <%@page import="java.util.*" %>
    <%@page import="Elements.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Schülerverwaltung</title>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css" />
<script language="javascript" type="text/javascript" src="Scripts/Students.js"></script>
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="dialog" title="Schüler" style="display: none; height: 100%; width: 100%">
   <iframe id="dialogTarget" style="height: 100%; width: 100%; border: none;"></iframe>
</div>
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
<form method="post" action="StudentServlet">
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
							<td class="MenuItem"><a href="main.jsp">Start</</a></td>
							<td class="SelectedMenuItem"><a href="Students.jsp">Schüler</</a></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td align="center" style="background: lightBlue; width: 100%; height: 400px">
				<div style="background: lightBlue; width: 100%;">
					<table style="width: 100%;">
						<tr>
							<td align="Left" >
								<label>Suchbegriff: </label>
							</td>
							<td align="Left">
								<input style="width: 300px" type="text" name="Filter" />
							</td>
							<td align="Left" style="width: 100%;"><input type="submit" value="Suchen"/></td>
							<td align="Right" >
								<input type="button" value="Neu" id="btnNew"/>
							</td>
							<td align="Right" >
								<input type="button" value="Löschen" id="btnDelete"/>
							</td>
							<td align="Right" >
								<input type="button" value="Bearbeiten" id="btnEdit"/>
							</td>
						</tr>
					</table>
				</div>
				<br/>
				<div style="background: lightBlue; width: 100%; height: 100%; overflow: auto">
					<%
						LinkedList<Student> students = new LinkedList();
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
						students.add(new Student());
					//(LinkedList<Student>)request.getAttribute("Students");
						out.write("	<table class=\"DetailList\" border=1>\n");
						out.write("<thead style=\"background-color: white\">");
						out.write("<tr>");
						out.write("<td class=\"DetailHeader\">TestHeader</td>");
						out.write("<td class=\"DetailHeader\">TestHeader2</td>");
						out.write("<td class=\"DetailHeader\">TestHeader3</td>");
						out.write("</tr>");
						out.write("</thead>");
						out.write("<tbody>");
						if(students.size() > 0)
						{
							
							for(Student student:students)
							{
								out.write("		<tr class=\"DetailEntry\" id=\"" + student.getId() + "\">\n");
								out.write("			<td>&nbsp;</td>\n");
								out.write("			<td>&nbsp;</td>\n");
								out.write("			<td>&nbsp;</td>\n");
								out.write("		</tr>\n");
							}
						}
						out.write("</tbody>");
						out.write("	</table>\n");
					%>
				</div>
			</td>
		</tr>
	</table>
</form>
</center>
</body>
</html>