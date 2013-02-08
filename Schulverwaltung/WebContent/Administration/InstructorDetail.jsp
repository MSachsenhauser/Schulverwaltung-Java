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
		Instructor curInstructor = new Instructor().setId(id);
		if(curInstructor.getId() != -1)
		{
			curInstructor.load();
		}
		String readonly = curInstructor.getDisableflag()  > 0 ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="../InstructorDetailServlet">
	<input type="hidden" name="Id" value="<%= curInstructor.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curInstructor.getDisableflag() %>" />
	<table>
		<tr>
			<td>
				<label>Name: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Name" style="width: 200px" value="<%= curInstructor.getName() %>"/>
			</td>
			<td>
				<label>Vorname: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Firstname" style="width: 200px" value="<%= curInstructor.getFirstname() %>"/>
			</td>
		</tr>
		<tr>
		<td>
				<label>Telefon: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Phone" style="width: 200px" value="<%= curInstructor.getPhone() %>"/>
			</td>
			<td>
				<label>Email: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="Email" style="width: 200px" value="<%= curInstructor.getEmail() %>"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>Firma: </label>
			</td>
			<td colspan="3">
				<select <%=readonly%> name="CompanyId" style="width: 100%">
					<option value="-1"></option>
					<%
						for(Company company:ElementLists.getCompanies())
						{
							if(company.getId() == curInstructor.getCompanyId())
							{
								out.write("<option selected=\"true\" value=\"" + company.getId() + "\">" + company.getName() + "</option>\n");
							}
							else
							{
								out.write("<option value=\"" + company.getId() + "\">" + company.getName() + "</option>\n");
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