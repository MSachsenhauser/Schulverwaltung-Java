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
		int id = Integer.parseInt(request.getParameter("Id"));
		Subject curSubject = new Subject().setId(id);
		if(curSubject.getId() != -1)
		{
			curSubject.load();
		}
		String readonly = curSubject.getDisableflag()   > 0 ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="../SubjectDetailServlet">
	<input type="hidden" name="Id" value="<%= curSubject.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curSubject.getDisableflag() %>" />
	<table>
		<tr>
			<td>
				<label>Bezeichnung: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Description" style="width: 200px" value="<%= curSubject.getDescription() != null ? curSubject.getDescription() : "" %>"/>
			</td>
			<td>
				<label>Kürzel: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Short" style="width: 100px" value="<%= curSubject.getShortName() != null ? curSubject.getShortName() : "" %>"/>
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