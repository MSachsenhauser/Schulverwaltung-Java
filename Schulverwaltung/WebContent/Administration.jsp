<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="Database.*" %>
     <%@page import="Helpers.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Schülerverwaltung</title>
    <link rel="stylesheet" href="Styles/Themes/redmond/jquery-ui.css" />
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css" />
    <script language="javascript" type="text/javascript" src="Scripts/Administration.js"></script>
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<% 
		try
		{
			Login curLogin = (Login)request.getSession().getAttribute("Login");
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
		String filter = request.getParameter("Filter") != null ? request.getParameter("Filter") : "";
		Boolean showDisabled = request.getParameter("ShowDisabled") != null ? 
							   Boolean.parseBoolean(request.getParameter("ShowDisabled")) : 
							   false;
%>
<input type="hidden" id="SortKey" name="SortKey" />
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
					out.write(MenuHelper.GenerateTopMenu("Administration.jsp"));
				%>
			</td>
		</tr>
		<tr>
			<td align="center" style="background: lightBlue; width: 100%; height: 550px">
					<div id="tabControl" style="background: lightBlue; border: none; width: 99%;  height: 550px">
					<ul style="font-size: 70%">
				        <li><a href="#tab1">Berufe</a></li>
				        <li><a href="#tab2">Religionen</a></li>
				        <li><a href="#tab3">Lehrer</a></li>
				        <li><a href="#tab4">Stundenpläne</a></li>
				        <li><a href="#tab5">Klassen</a></li>
    				</ul>
    				<div id="tab1" style="width: 100%; height: 100%;">
       				 	<iframe src="JobServlet" style="border: none; height: 100%; width: 970px; margin-left: -45px; margin-top: -15px;"></iframe>
    				</div>
    				
				    <div id="tab2" style="width: 100%; height: 100%; overflow: auto">
				        <iframe src="ReligionServlet" style="border: none; height: 100%; width: 970px; margin-left: -45px; margin-top: -15px;"></iframe>
				    </div>
				    <div id="tab3" style="width: 100%; height: 100%; overflow: auto">
				        <iframe src="TeacherServlet" style="border: none; height: 100%; width: 100%; margin-left: -60px; margin-top: -15px;"></iframe>
				    </div>
				    <div id="tab4" style="width: 100%; height: 100%; overflow: auto">
				        <iframe src="TimeTableServlet" style="border: none; height: 100%; width: 100%; margin-left: -60px; margin-top: -15px;"></iframe>
				    </div>
				    <div id="tab5" style="width: 100%; height: 100%; overflow: auto">
						<iframe src="GradeServlet" style="border: none; height: 100%; width: 100%; margin-left: -60px; margin-top: -15px;"></iframe>
				    </div>
				</div>
			</td>
		</tr>
	</table>
	</center>
</body>
</html>