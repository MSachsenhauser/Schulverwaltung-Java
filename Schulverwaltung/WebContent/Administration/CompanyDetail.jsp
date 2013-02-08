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
		Company curCompany = new Company().setId(id);
		if(curCompany.getId() != -1)
		{
			curCompany.load();
		}
		String readonly = curCompany.getDisableflag()  > 0 ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="../CompanyDetailServlet">
	<input type="hidden" name="Id" value="<%= curCompany.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curCompany.getDisableflag() %>" />
	<table>
		<tr>
			<td>
				<label>Name: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Name" style="width: 200px" value="<%=curCompany.getName() %>"/>
			</td>
			<td>
				<label>Telefon: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Phone" style="width: 100px" value="<%=curCompany.getPhone() %>"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>Straﬂe: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Street" style="width: 200px" value="<%=curCompany.getStreet() %>"/>
			</td>
			<td>
				<label>PLZ: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Plz" style="width: 100px" value="<%=curCompany.getPlz() %>"/>
			</td>
			<td>
				<label>Stadt: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="City" style="width: 100px" value="<%=curCompany.getCity() %>"/>
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