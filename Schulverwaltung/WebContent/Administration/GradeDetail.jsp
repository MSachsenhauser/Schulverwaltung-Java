<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="elements.*" %>
<%@ page import="java.text.*" %>
<%@ page import="servlets.StudentDetailServlet" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../Scripts/jquery.js"></script>
    <script src="../Scripts/jquery-ui.js"></script>	
    <link rel="stylesheet" href="../Styles/Themes/redmond/jquery-ui.css" />
    <script type="text/javascript" src="../Scripts/GradeDetail.js"></script>
     <script type="text/javascript" src="../Scripts/Ajax.js"></script>
     <script type="text/javascript" src="../Scripts/Validation.js"></script>
     <style type="text/css">
     	body {
			font-size: medium;
		}
		input {
			
		}
     </style>
</head>
<body>
<div id="dialog" title="Durchschnitt" style="display: none; padding:0px;">
   <iframe id="dialogTarget" style="height: 98%; width: 100%; border: none"></iframe>
</div>
<script type="text/javascript">
$(document).ready(function()
		{
	$("form").submit(function()
					{
						document.getElementById("groupStudents").value = groupStudents.toString();
						parent.closeDialog();
						this.submit();
					});
		}
);
</script>
<%
		int gradeId = Integer.parseInt(request.getParameter("Id"));
		Grade curGrade = new Grade().setId(gradeId);
		if(curGrade.getId() != -1)
		{
			curGrade.load();
		}
		String readonly = curGrade.getDisableflag()  > 0 ? "readonly=readonly" : "";
		String showGroupElements = curGrade.getId() == -1 ? "style=\"display: none\"" : "";
	%>
	<form id="form" method="Post" action="../GradeDetailServlet">
	<input type="hidden" name="Id" id="Id" value="<%= curGrade.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curGrade.getDisableflag() %>" />
	<input type="hidden" name="groupStudents" id="groupStudents"/>
	<input type="hidden" name="groupSubjects" id="groupSubjects" />
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
								out.write("<option selected=\"true\" value=\"" + teacher.getId() + "\">" + teacher.getFullName() + "</option>\n");
							}
							else
							{
								out.write("<option value=\"" + teacher.getId() + "\">" + teacher.getFullName() + "</option>\n");
							}
						}
					%>
				</select>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr <%=showGroupElements%>>
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
		<tr <%=showGroupElements%>>
			<td colspan="6">
				<table style="width: 100%">
					<tr>
						<td style="width: 50%">
							<select id="lstStudents" style="width: 100%; height: 100px" multiple="multiple">
								<%
									for(Student student:ElementLists.getStudents(curGrade.getId()))
									{
										out.write("<option value=\"" + student.getId() + "\">" + student.getId() + ": " + student.getFullName() + " - " + new SimpleDateFormat("dd.MM.yyyy").format(student.getBirthday()) +  "</option>\n");
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
											out.write("<option value=\"" + student.getId() + "\">" + student.getId() + ": " + student.getFullName() + " - " + new SimpleDateFormat("dd.MM.yyyy").format(student.getBirthday()) +  "</option>\n");
										}
									}
								%>
							</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr <%=showGroupElements%>>
			<td colspan="6" style="width: 100%">
				<table style="width: 100%">
					<tr>
					<td><label>Fach: </label></td>
					<td>
						<select id="lstSubjects">
						<option value="-1" selected="selected"> - </option>
						<%
							for(Subject subject:ElementLists.getSubjects())
							{
								out.write("<option value=\"" + subject.getId() +"\">" + subject.getShortName() + "</option>");
							}
						%>
						</select>
					</td>
					<td><label>Lehrer: </label></td>
					<td>
						<select id="lstTeachers">
						<option value="-1" selected="selected"> - </option>
						<%
							for(Teacher teacher:ElementLists.getTeachers())
							{
								out.write("<option value=\"" + teacher.getId() +"\">" + teacher.getFullName() + "</option>");
							}
						%>
						</select>
					</td>
					<td><label>Raum: </label></td>
					<td>
						<select id="lstRooms">
						<option value="-1" selected="selected"> - </option>
						<%
							for(Room room:ElementLists.getRooms())
							{
								out.write("<option value=\"" + room.getId() +"\">" + room.getNumber() + "</option>");
							}
						%>
						</select>
					</td>
					<td><label>Beschreibung:</label></td>
					<td>
						<input type="text" id="txtDescription" /> 
					</td>
					<td>
						<input type="button" value="Hinzufügen" onclick="addSubject()"/>
					</td>
				</tr>
				<tr>
					<td colspan="9">
						<select multiple="multiple" style="width: 100%; height: 150px" id="lstGroupSubjects">
						<%
						if(curGrade.getGroups() != null && curGrade.getGroups().size() > 0)
						{
							for(GroupSubject subject:curGrade.getGroups().get(0).getSubjects())
							{
								out.write("<option value=\"" + subject.getId() + "\">" + subject.getSubject().getShortName() + " - " + subject.getTeacher().getFullName() + " - " + subject.getRoom().getNumber() + (!subject.getDescription().isEmpty() ? " - " + subject.getDescription() : "") +  "</option>\n");
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
				<input <%=readonly != "" ? "disabled=disabled" : ""%> type="submit" value="speichern" />
			</td>
			<td>
				<input <%=showGroupElements%> type="button" value="Klassendurchschnitt" onclick="openGradeAverage()"/>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>
