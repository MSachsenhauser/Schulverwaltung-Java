<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="elements.*" %>
<%@ page import="java.text.*" %>
<%@ page import="servlets.StudentDetailServlet" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>	
    <script type="text/javascript" src="../Scripts/GradeDetail.js"></script>
     <script type="text/javascript" src="../Scripts/Ajax.js"></script>
     <script type="text/javascript" src="../Scripts/Validation.js"></script>
</head>
<body onload="onLoad()">
<%
		int gradeId = Integer.parseInt(request.getParameter("Id"));
		Grade curGrade = new Grade().setId(gradeId);
		if(curGrade.getId() != -1)
		{
			curGrade.load();
		}
		String readonly = curGrade.getDisableflag()  > 0 ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="../GradeDetailServlet">
	<input type="hidden" name="Id" id="Id" value="<%= curGrade.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curGrade.getDisableflag() %>" />
	<input type="hidden" name="groupStudents" id="groupStudents"/>
	<table>
		<tr>
			<td>
				<label>Bezeichnung: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="description" value="<%=curGrade.getDescription()%>"/>
			</td>
			<td>
				<label>Klassenzimmer: </label>
			</td>
			<td>
				<select <%=readonly%> name="room" style="width: 100%">
					<option value="-1"></option>
					<%
						for(Room room:ElementLists.getRooms())
						{
							if(curGrade.getRoomId() == room.getId())
							{
								out.write("<option selected=\"true\" value=\"" + room.getId() + "\">" + room.getNumber() + "</option>\n");
							}
							else
							{
								out.write("<option value=\"" + room.getId() + "\">" + room.getNumber() + "</option>\n");
							}
						}
					%>
				</select>
			</td>
			<td>
				<label>Klassenleiter: </label>
			</td>
			<td>
				<select <%=readonly%> name="teacher" style="width: 100%">
					<option value="-1"></option>
					<%
						for(Teacher teacher:ElementLists.getTeachers())
						{
							if(curGrade.getTeacherId() == teacher.getId())
							{
								out.write("<option selected=\"true\" value=\"" + teacher.getId() + "\">" + teacher.getName() + " " + teacher.getFirstname() + "</option>\n");
							}
							else
							{
								out.write("<option value=\"" + teacher.getId() + "\">" + teacher.getName() + " " + teacher.getFirstname() + "</option>\n");
							}
						}
					%>
				</select>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<input type="text" id="txtGroupDescription" />
			</td>
			<td>
				<input type="button" value="Gruppe hinzufügen" onclick="addGroup()"/>
			</td>
			<td>
				<select id="lstGroups" style="width: 100%;" onchange="loadGroupStudents()">
					<%
						for(Group group:curGrade.getGroups())
						{
							out.write("<option value=\"" + group.getId() + "\">" + group.getDescription() + "</option>\n");
						}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="6">
				<table style="width: 100%">
					<tr>
						<td style="width: 50%">
							<select id="lstStudents" style="width: 100%; height: 100px" multiple="multiple">
								<%
									for(Student student:ElementLists.getStudents(curGrade.getId()))
									{
										out.write("<option value=\"" + student.getId() + "\">" + student.getId() + ": " + student.getName() + " " + student.getFirstname() + " - " + new SimpleDateFormat("dd.MM.yyyy").format(student.getBirthday()) +  "</option>\n");
									}
								%>
							</select>
						</td>
						<td>
							<input type="button" value="&gt;&gt;" onclick="addStudents()"/><br />
							<input type="button" value="&lt;&lt;" onclick="removeStudents()"/>
						</td>
						<td style="width: 50%">
							<select id="lstClassMember" style="width: 100%; height: 100px" multiple="multiple">
								<%
									if(curGrade.getGroups() != null && curGrade.getGroups().size() > 0)
									{
										for(Student student:curGrade.getGroups().get(0).getStudents())
										{
											out.write("<option value=\"" + student.getId() + "\">" + student.getId() + ": " + student.getName() + " " + student.getFirstname() + " - " + new SimpleDateFormat("dd.MM.yyyy").format(student.getBirthday()) +  "</option>\n");
										}
									}
								%>
							</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<input <%=readonly != "" ? "disabled=disabled" : ""%> type="button" onclick="onSave()" value="speichern" />
			</td>
		</tr>
	</table>
	</form>
</body>
</html>