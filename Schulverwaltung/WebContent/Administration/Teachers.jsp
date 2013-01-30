<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="java.util.*" %>
    <%@page import="elements.*" %>
    <%@page import="database.*" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="Styles/Themes/redmond/jquery-ui.css" />
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css" />
    <script language="javascript" type="text/javascript" src="Scripts/Ajax.js"></script>
	<script language="javascript" type="text/javascript" src="Scripts/List.js"></script>
	<script language="javascript" type="text/javascript">
		detailFileName = "Administration/TeacherDetail.jsp";
		deleteServlet = "DeleteTeacherServlet";
		deleteText = "Möchten Sie die ausgewählten Lehrer wirklich entfernen?";
		elementName = "Lehrer";
	</script>
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>
</head>
<body class="TabItem">
<div id="dialog" title="Religionen" style="display: none; height: 100%; width: 100%">
   <iframe id="dialogTarget" style="height: 100%; width: 100%; border: none;"></iframe>
</div>
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
<form action="TeacherServlet" method="post">
	<input type="hidden" id="SortKey" name="SortKey" />
	<input type="hidden" id="NeedSort" name="NeedSort" value="0" />
							<div style="background: lightBlue; width: 100%;">
								<table style="width: 100%;">
									<tr>
										<td align="Left" >
											<label>Suchbegriff: </label>
										</td>
										<td align="Left">
											<input style="width: 300px" type="text" name="Filter" value="<%= filter %>"/>
										</td>
										<td align="Left"><input type="submit" value="Suchen"/></td>
										<td align="Left" valign="bottom"><input type="checkbox" id="ShowDisabled" name="ShowDisabled" value="true" <% if(showDisabled) out.write("checked=\"true\""); %>/></td>
										<td align="Left" valign="middle" style="width: 100%;">
											<label for="ShowDisabled"><nobr>Ausgeblendete anzeigen</nobr></label>
										</td>
										
										<td align="Right" >
											<input type="button" value="Neu" id="btnNew"/>
										</td>
										<td align="Right" >
											<input type="button" value="Löschen" id="btnDelete" onClick="deleteEntries();"/>
										</td>
									</tr>
								</table>
							</div>
							<br/>
       				 		<div style="background: lightBlue; width: 100%; height: 100%; overflow: auto">
								<%
									ArrayList<Teacher> teachers = (ArrayList<Teacher>)request.getAttribute("List");
									out.write("	<table class=\"DetailList\" border=1>\n");
									out.write("		<thead style=\"background-color: lightBlue\">");
									out.write("			<tr>");
									out.write("				<td>&nbsp;</td>");
									out.write("				<td class=\"DetailHeader\" onclick=\"SetSortKey(0)\">Id</td>");
									out.write("				<td class=\"DetailHeader\" style=\"width: 99%\" onclick=\"SetSortKey(1)\">Name</td>");
									out.write("				<td class=\"DetailHeader\" style=\"width: 99%\" onclick=\"SetSortKey(2)\">Vorname</td>");
									out.write("			</tr>");
									out.write("		</thead>");
									out.write("		<tbody>");
									if(teachers != null && teachers.size() > 0)
									{
										for(Teacher teacher:teachers)
										{
											out.write("		<tr class=\"DetailEntry\" id=\"" + teacher.getId() + "\">\n");
											out.write("			<td style=\"width: 50px\"><input type=\"checkbox\" onclick=\"checkDeleteEnty(this);\"/></td>\n");
											out.write("			<td style=\"width: 100px\" onclick=\"openDetails=true;\">" + teacher.getId() + "</td>\n");
											out.write("			<td style=\"width: 100%\" onclick=\"openDetails=true;\">" + teacher.getName() + "</td>\n");
											out.write("			<td style=\"width: 100%\" onclick=\"openDetails=true;\">" + teacher.getFirstname() + "</td>\n");
											out.write("		</tr>\n");
										}
									}
									out.write("		</tbody>");
									out.write("</table>\n");
								%>
								</div>
       				 	</form>
</body>
</html>