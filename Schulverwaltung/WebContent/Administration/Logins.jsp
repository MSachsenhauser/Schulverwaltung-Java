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
		detailFileName = "Administration/LoginDetail.jsp";
		deleteServlet = "deleteLogin";
		deleteText = "Möchten Sie die ausgewählten Logins wirklich entfernen?";
		elementName = "Login";
	</script>
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>
</head>
<body class="TabItem">
<div id="dialog" title="Logins" style="display: none; height: 100%; width: 100%">
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
<form action="LoginListServlet" method="post">
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
										<td style="width:100%">&nbsp;</td>
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
									ArrayList<Login> logins = (ArrayList<Login>)request.getAttribute("List");
									out.write("	<table class=\"DetailList\" border=1>\n");
									out.write("		<thead style=\"background-color: lightBlue\">");
									out.write("			<tr>");
									out.write("				<td>&nbsp;</td>");
									out.write("				<td class=\"DetailHeader\" onclick=\"SetSortKey(0)\">Username</td>");
									out.write("				<td class=\"DetailHeader\" onclick=\"SetSortKey(1)\">Email</td>");
									out.write("			</tr>");
									out.write("		</thead>");
									out.write("		<tbody>");
									if(logins != null && logins.size() > 0)
									{
										for(Login login:logins)
										{
											out.write("		<tr class=\"DetailEntry\" id=\"" + login.getUserName() + "\">\n");
											out.write("			<td style=\"width: 50px\"><input type=\"checkbox\" onclick=\"checkDeleteEnty(this);\"/></td>\n");
											out.write("			<td style=\"width: 50px\" onclick=\"openDetails=true;\">" + login.getUserName() + "</td>\n");
											out.write("			<td onclick=\"openDetails=true;\">" + login.getEmail() + "</td>\n");
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