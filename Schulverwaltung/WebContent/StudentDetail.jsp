<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ page import="Elements.*" %>
<%@ page import="java.text.*" %>
<%@ page import="Controls.StudentDetailServlet" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>	
    <script type="text/javascript" src="Scripts/StudentDetail.js"></script>
</head>
<body>
<%
		int studentId = Integer.parseInt(request.getParameter("StudentId"));
		Student curStudent = new Student().setId(studentId);
		if(curStudent.getId() != -1)
		{
			curStudent.load();
		}
		String readonly = false ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="StudentDetailServlet">
	<input type="hidden" name="StudentId" value="<%= curStudent.getId() %>" />
	<table>
		<tr>
			<td>
				<label>Vorname: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="firstname" value="<%=curStudent.getFirstname()%>"/>
			</td>
			<td>
				<label>Nachname: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="lastname" value="<%=curStudent.getName()%>"/>
			</td>
			<td>
				<label>E-Mail: </label>
			</td>
			<td>
				<input <%=readonly%> style="width: 230px" type="text" name="email" value="<%=curStudent.getEmail()%>"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>Straße: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="street" value="<%=curStudent.getStreet()%>"/>
			</td>
			<td>
				<label>PLZ: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="plz" value="<%=curStudent.getPlz()%>"/>
			</td>
			<td>
				<label>Stadt: </label>
			</td>
			<td>
				<input <%=readonly%> style="width: 150px" type="text" name="city" value="<%=curStudent.getCity()%>"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>Beruf: </label>
			</td>
			<td>
				<!-- <input type="text" name="job" value="<%=curStudent.getJob().getDescription()%>"/> -->
				<select <%=readonly%> id="lstJobs" style="width: 100%">
					<%
						// Hier berufe aus der Datenbank laden und als Option einfügen
					%>
				</select>
			</td>
			<td>
				<label>Firma: </label>
			</td>
			<td>
				<select <%=readonly%> name="company" style="width: 100%">
					<%
						// Hier firmen mit entsprechendem Selected Option
					%>
				</select>
			</td>
			<td colspan="2">
				<input <%=readonly%> type="checkbox" name="shortened" value="<%=curStudent.getShortened()%>" />
				 <label>Verkürzt</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>Geburtstag: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="birthday" value="<%=new SimpleDateFormat("dd.MM.yyyy").format(curStudent.getBirthday())%>"/>
			</td>
			<td>
				<label>Eintritt: </label>
			</td>
			<td>
				<input <%=readonly%> type="text" name="entry" value="<%=new SimpleDateFormat("dd.MM.yyyy").format(curStudent.getEntry())%>"/>
			</td>
			<td>
				<label>Religion: </label>
			</td>
			<td>
				<select <%=readonly%> name="religion" style="width: 100%">
					<%
						// Hier religionen mit entsprechendem Selected Option
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