<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="elements.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>	
    <script type="text/javascript" src="../Scripts/Detail.js"></script>
    <script type="text/javascript" src="../Scripts/Validation.js"></script>
</head>
<body>
<%
		int id = Integer.parseInt(request.getParameter("Id"));
		Religion curReligion = new Religion().setId(id);
		if(curReligion.getId() != -1)
		{
			curReligion.load();
		}
		String readonly = curReligion.getDisableflag()  > 0 ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="../RoomDetailServlet">
	<input type="hidden" name="Id" value="<%= curReligion.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curReligion.getDisableflag() %>" />
	<table>
		<tr>
			<td>
				<label>Religion: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Description" style="width: 200px" value="<%= curReligion.getDescription() %>"/>
			</td>
			<td>
				<label>Fach: </label>
			</td>
			<td>
				<select <%=readonly%> name="SubjectId" style="width: 200%">
					<option value="-1"></option>
					<%
						for(Subject subject:ElementLists.getSubjects())
						{
							if(curReligion.getSubjectId() == subject.getId())
							{
								out.write("<option selected=\"true\" value=\"" + subject.getId() + "\">" + subject.getDescription() + "</option>\n");
							}
							else
							{
								out.write("<option value=\"" + subject.getId() + "\">" + subject.getDescription() + "</option>\n");
							}
						}
					%>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<input <%=readonly != "" ? "disabled=disabled" : ""%> type="submit" value="speichern" />
			</td>
		</tr>
	</table>
	</form>
</body>
</html>