<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ page import="elements.*" %>
<%@ page import="java.text.*" %>
<%@ page import="servlets.StudentDetailServlet" %>
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
		Job curJob = new Job().setId(id);
		if(curJob.getId() != -1)
		{
			curJob.load();
		}
		String readonly = curJob.getDisableflag()  > 0 ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="../JobDetailServlet">
	<input type="hidden" name="Id" value="<%= curJob.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curJob.getDisableflag() %>" />
	<table>
		<tr>
			<td>
				<label>Beschreibung: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Description" style="width: 200px" value="<%=curJob.getDescription() %>"/>
			</td>
			<td>
				<label>Ausbildungsdauer: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Duration" style="width: 50px" value="<%=curJob.getDuration() %>" onblur="numberValidation(this)"/>
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