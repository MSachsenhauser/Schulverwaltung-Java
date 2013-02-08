<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="elements.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../Scripts/jquery.js"></script>
    <script src="../Scripts/jquery-ui.js"></script>	
    <script type="text/javascript" src="../Scripts/Detail.js"></script>
    <script type="text/javascript" src="../Scripts/Validation.js"></script>
</head>
<body>
<%
		int Id = Integer.parseInt(request.getParameter("Id"));
		Teacher curTeacher = new Teacher().setId(Id);
		if(curTeacher.getId() != -1)
		{
			curTeacher.load();
		}
		String readonly = curTeacher.getDisableflag()  > 0 ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="../TeacherDetailServlet">
	<input type="hidden" name="Id" value="<%= curTeacher.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curTeacher.getDisableflag() %>" />
	<table>
		<tr>
			<td>
				<label>Vorname: </label>
			</td>
			<td>
				<input <%=readonly%>style="width: 200px"  type="text" name="firstname" value="<%=curTeacher.getFirstname()%>"/>
			</td>
			<td>
				<label>Nachname: </label>
			</td>
			<td>
				<input <%=readonly%> style="width: 230px" type="text" name="name" value="<%=curTeacher.getName()%>"/>
			</td>
			<td>
				<label>Kürzel: </label>
			</td>
			<td>
				<input <%=readonly%> style="width: 90px" type="text" name="shortName" value="<%=curTeacher.getShortName() %>"/>
			</td>
			
		</tr>
		<tr>
			<td>
				<label>Telefonnummer: </label>
			</td>
			<td>
				<input <%=readonly%> style="width: 200px" type="text" name="phone" value="<%=curTeacher.getPhone()%>"/>
			</td>
			<td>
				<label>E-Mail: </label>
			</td>
			<td>
				<input <%=readonly%> style="width: 230px" type="text" name="email" value="<%=curTeacher.getEmail()%>"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>Fachraum: </label>
			</td>
			<td>
				<select <%=readonly%> name="roomId" style="width: 100%">
					<option value="-1"></option>
					<%
						for(Room room:ElementLists.getRooms())
						{
							if(curTeacher.getRoomId() == room.getId())
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
				<label>Arbeitsstunden: </label>
			</td>
			<td>
				<input <%=readonly%> style="width: 230px" type="text" name="workhours" value="<%=curTeacher.getWorkhours() %>" onblur="numberValidation(this)"/>
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