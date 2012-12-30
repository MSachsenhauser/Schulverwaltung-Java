<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@page import="Database.*" %>
    <%@page import="model.*" %>
    <%@page import="java.util.*" %>
    <%@page import="Elements.*" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administration</title>
<link rel="stylesheet" href="Styles/Themes/redmond/jquery-ui.css" />
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css" />
    <script language="javascript" type="text/javascript" src="Scripts/Ajax.js"></script>
<script language="javascript" type="text/javascript" src="Scripts/Administration.js"></script>
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>
</head>
<body>
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
							<td class="MenuItem"><a href="main.jsp">Start</a></td>
							<td class="SelectedMenuItem"><a href="Administration.jsp">Administration</a></td>
							<td class="MenuItem"><a href="Students.jsp">Schüler</a></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td align="center" style="background: lightBlue; width: 100%;">
				<div id="tabControl" style="width: 970px;">
					<ul style="font-size: 70%">
				        <li><a href="#tab1">Berufe</a></li>
				        <li><a href="#tab2">Religionen</a></li>
				        <li><a href="#tab3">Lehrer</a></li>
				        <li><a href="#tab4">Stundenpläne</a></li>
				        <li><a href="#tab5">Klassen</a></li>
    				</ul>
    				<div id="tab1" style="width: 100%; height: 400px">
       				 	<form action="JobServlet" method="post">
       				 		<div style="background: lightBlue; margin-left: -50px; margin-top: -10px; width: 100%; height: 100%; overflow: auto">
								<%
									List<Job> jobs = (List<Job>)request.getAttribute("Jobs");
									out.write("	<table class=\"DetailList\" border=1>\n");
									out.write("		<thead style=\"background-color: lightBlue\">");
									out.write("			<tr>");
									out.write("				<td>&nbsp;</td>");
									out.write("				<td class=\"DetailHeader\" onclick=\"SetSortKey(0)\">Id</td>");
									out.write("				<td class=\"DetailHeader\" onclick=\"SetSortKey(1)\">Bezeichnung</td>");
									out.write("				<td class=\"DetailHeader\" onclick=\"SetSortKey(2)\">Dauer</td>");
									out.write("			</tr>");
									out.write("		</thead>");
									out.write("		<tbody>");
									if(jobs != null && jobs.size() > 0)
									{
										for(Job job:jobs)
										{
											out.write("		<tr class=\"DetailEntry\" id=\"" + job.getId() + "\">\n");
											out.write("			<td style=\"width: 50px\"><input type=\"checkbox\" onclick=\"checkDeleteEnty(this);\"/></td>\n");
											out.write("			<td style=\"width: 100px\" onclick=\"openDetails=true;\">" + job.getId() + "</td>\n");
											out.write("			<td onclick=\"openDetails=true;\">" + job.getDescription() + "</td>\n");
											out.write("			<td onclick=\"openDetails=true;\">" + job.getDuration() + "</td>\n");
											out.write("			<td style=\"width: 100%\" onclick=\"openDetails=true;\">&nbsp;</td>\n");
											out.write("		</tr>\n");
										}
									}
									out.write("		</tbody>");
									out.write("</table>\n");
								%>
								</div>
       				 	</form>
    				</div>
    				
				    <div id="tab2">
				        <form action="ReligionServlet" method="post">
       				 		
       				 	</form>
				    </div>
				    <div id="tab3">
				        <form action="TeacherServlet" method="post">
       				 		
       				 	</form>
				    </div>
				    <div id="tab4">
				        <form action="TimeTableServlet" method="post">
       				 		
       				 	</form>
				    </div>
				    <div id="tab5">
				        <form action="GradeServlet" method="post">
       				 		
       				 	</form>
				    </div>
				</div>
			</td>
		</tr>
	</table>
	</center>
</body>
</html>