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
		Room curRoom = new Room().setId(id);
		if(curRoom.getId() != -1)
		{
			curRoom.load();
		}
		String readonly = curRoom.getDisableflag() > 0 ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="../RoomDetailServlet">
	<input type="hidden" name="Id" value="<%= curRoom.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curRoom.getDisableflag() %>" />
	<table>
		<tr>
			<td>
				<label>Raumnummer: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Number" style="width: 50px" value="<%= curRoom.getNumber() %>"/>
			</td>
			<td>
				<label>Beschreibung: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Description" style="width: 200px" value="<%= curRoom.getDescription() %>"/>
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