<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="Database.*" %>
    <%@page import="Helpers.*" %>
    <%@page import="Elements.*" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stundenpläne</title>
<link href="Styles/TimeTable.css" rel="stylesheet" type="text/css"/>
<link href="Styles/Default.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="Styles/Themes/redmond/jquery-ui.css" />
    <script src="Scripts/Ajax.js"></script>
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>
    <link rel="stylesheet" href="/resources/demos/style.css" />
<script src="Scripts/TimeTable.js" type="text/javascript" language="javascript"></script>
</head>
<body onload="init();">
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
	<form action="TimeTableServlet" method="post">
		<div id="dialog" title="Stunde" style="display: none; height: 100%; width: 100%">
  			 <table>
  			 	<tr>
  			 		<td>Fach:</td>
  			 		<td>
  			 			<select id="lstSubjects" style="width: 250px">
  			 				<option value="-1" selected="selected">-</option>
  			 			</select>
  			 		</td>
  			 	</tr>
  			 	<tr>
  			 		<td colspan="2">&nbsp;</td>
  			 	</tr>
  			 	<tr>
  			 		<td><input type="button" value="Ok" onclick="submitHourSelection()"/></td>
  			 		<td><input type="button" value="Abbrechen" onclick="closeDialog()"/></td>
  			 	</tr>
  			 </table>
		</div>
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
							out.write(MenuHelper.GenerateTopMenu("TimeTables.jsp"));
						%>
					</td>
				</tr>
				<tr>
					<td>
						<table align="center" style="background: lightBlue; width: 100%;">
							<tr>
								<td><label>Klasse </label></td>
								<td>
									<select id="lstGrades" onchange="loadGroups(this)" style="width: 100px">
										<option value="-1" selected="selected">-</option>
										<%
											for(Grade grade:ElementLists.getGrades())
											{
												out.write("<option value=\"" + grade.getId() + "\">" + grade.getDescription() + "</option>");
											}
										%>
									</select>
								</td>
								<td>&nbsp;</td>
								<td><label>Gruppe</label></td>
								<td>
									<select id="lstGroups" onchange="loadSubjects(this)" style="width: 100px">
										<option value="-1" selected="selected">-</option>
									</select>
								</td>
								<td>&nbsp;</td>
								<td><label><nobr>Gültig bis</nobr></label></td>
								<td>
									<input type="text" id="validTill" style="width: 100px"/>
								</td>
								<td style="width: 100%">
									&nbsp;
								</td>
								<td>
									<input type="button" value="Laden" onclick="loadTimeTable()" />
								</td>
								<td>
									<input type="button" value="Speichern" onclick="onSave()"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center" style="background: lightBlue; width: 100%; height: 550px">
						<table cellspacing="1" cellpadding="2">
							<tr>
								<th></th>
								<th>Montag</th>
								<th>Dienstag</th>
								<th>Mittwoch</th>
								<th>Donnerstag</th>
								<th>Freitag</th>
							</tr>
							<tr>
								<td class="RowHeader">1</td>
								<td class="HourCell" onclick="openHourSelector(0,0);" id="hour_0_0"></td>
								<td class="HourCell" onclick="openHourSelector(1,0);" id="hour_1_0"></td>
								<td class="HourCell" onclick="openHourSelector(2,0);" id="hour_2_0"></td>
								<td class="HourCell" onclick="openHourSelector(3,0);" id="hour_3_0"></td>
								<td class="HourCell" onclick="openHourSelector(4,0);" id="hour_4_0"></td>
							</tr>
							<tr>
								<td class="RowHeader">2</td>
								<td class="HourCell" onclick="openHourSelector(0,1);" id="hour_0_1"></td>
								<td class="HourCell" onclick="openHourSelector(1,1);" id="hour_1_1"></td>
								<td class="HourCell" onclick="openHourSelector(2,1);" id="hour_2_1"></td>
								<td class="HourCell" onclick="openHourSelector(3,1);" id="hour_3_1"></td>
								<td class="HourCell" onclick="openHourSelector(4,1);" id="hour_4_1"></td>
							</tr>
							<tr>
								<td class="RowHeader">3</td>
								<td class="HourCell" onclick="openHourSelector(0,2);" id="hour_0_2"></td>
								<td class="HourCell" onclick="openHourSelector(1,2);" id="hour_1_2"></td>
								<td class="HourCell" onclick="openHourSelector(2,2);" id="hour_2_2"></td>
								<td class="HourCell" onclick="openHourSelector(3,2);" id="hour_3_2"></td>
								<td class="HourCell" onclick="openHourSelector(4,2);" id="hour_4_2"></td>
							</tr>
							<tr>
								<td class="RowHeader">4</td>
								<td class="HourCell" onclick="openHourSelector(0,3);" id="hour_0_3"></td>
								<td class="HourCell" onclick="openHourSelector(1,3);" id="hour_1_3"></td>
								<td class="HourCell" onclick="openHourSelector(2,3);" id="hour_2_3"></td>
								<td class="HourCell" onclick="openHourSelector(3,3);" id="hour_3_3"></td>
								<td class="HourCell" onclick="openHourSelector(4,3);" id="hour_4_3"></td>
							</tr>
							<tr>
								<td class="RowHeader">5</td>
								<td class="HourCell" onclick="openHourSelector(0,4);" id="hour_0_4"></td>
								<td class="HourCell" onclick="openHourSelector(1,4);" id="hour_1_4"></td>
								<td class="HourCell" onclick="openHourSelector(2,4);" id="hour_2_4"></td>
								<td class="HourCell" onclick="openHourSelector(3,4);" id="hour_3_4"></td>
								<td class="HourCell" onclick="openHourSelector(4,4);" id="hour_4_4"></td>
							</tr>
							<tr>
								<td class="RowHeader">6</td>
								<td class="HourCell" onclick="openHourSelector(0,5);" id="hour_0_5"></td>
								<td class="HourCell" onclick="openHourSelector(1,5);" id="hour_1_5"></td>
								<td class="HourCell" onclick="openHourSelector(2,5);" id="hour_2_5"></td>
								<td class="HourCell" onclick="openHourSelector(3,5);" id="hour_3_5"></td>
								<td class="HourCell" onclick="openHourSelector(4,5);" id="hour_4_5"></td>
							</tr>
							<tr>
								<td class="RowHeader">7</td>
								<td class="HourCell" onclick="openHourSelector(0,6);" id="hour_0_6"></td>
								<td class="HourCell" onclick="openHourSelector(1,6);" id="hour_1_6"></td>
								<td class="HourCell" onclick="openHourSelector(2,6);" id="hour_2_6"></td>
								<td class="HourCell" onclick="openHourSelector(3,6);" id="hour_3_6"></td>
								<td class="HourCell" onclick="openHourSelector(4,6);" id="hour_4_6"></td>
							</tr>
							<tr>
								<td class="RowHeader">8</td>
								<td class="HourCell" onclick="openHourSelector(0,7);" id="hour_0_7"></td>
								<td class="HourCell" onclick="openHourSelector(1,7);" id="hour_1_7"></td>
								<td class="HourCell" onclick="openHourSelector(2,7);" id="hour_2_7"></td>
								<td class="HourCell" onclick="openHourSelector(3,7);" id="hour_3_7"></td>
								<td class="HourCell" onclick="openHourSelector(4,7);" id="hour_4_7"></td>
							</tr>
							<tr>
								<td class="RowHeader">9</td>
								<td class="HourCell" onclick="openHourSelector(0,8);" id="hour_0_8"></td>
								<td class="HourCell" onclick="openHourSelector(1,8);" id="hour_1_8"></td>
								<td class="HourCell" onclick="openHourSelector(2,8);" id="hour_2_8"></td>
								<td class="HourCell" onclick="openHourSelector(3,8);" id="hour_3_8"></td>
								<td class="HourCell" onclick="openHourSelector(4,8);" id="hour_4_8"></td>
							</tr>
							<tr>
								<td class="RowHeader">10</td>
								<td class="HourCell" onclick="openHourSelector(0,9);" id="hour_0_9"></td>
								<td class="HourCell" onclick="openHourSelector(1,9);" id="hour_1_9"></td>
								<td class="HourCell" onclick="openHourSelector(2,9);" id="hour_2_9"></td>
								<td class="HourCell" onclick="openHourSelector(3,9);" id="hour_3_9"></td>
								<td class="HourCell" onclick="openHourSelector(4,9);" id="hour_4_9"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</center>
	</form>
</body>
</html>