<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@ page import="Elements.*" %>
<%@ page import="java.text.*" %>
<%@ page import="Controls.*" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>	
    <script language="javascript" type="text/javascript" src="Scripts/StudentDetail.js"></script>
</head>
<body>
	<form method="Post" action="StudentDetailServlet">
	<%
		int studentId = Integer.parseInt(request.getParameter("StudentId"));
		Student curStudent = new Student().setId(studentId);
		if(studentId != -1)
		{
			curStudent.load();
			request.setAttribute("CurStudent", curStudent);
		}
		List<Job> jobs = new List<Job>();
	%>
	<table>
		<tr>
			<td>
				<label>Vorname: </label>
			</td>
			<td>
				<input type="text" name="firstname" value="<%=curStudent.getFirstname()%>"/>
			</td>
			<td>
				<label>Nachname: </label>
			</td>
			<td>
				<input type="text" name="lastname" value="<%=curStudent.getName()%>"/>
			</td>
			<td>
				<label>E-Mail: </label>
			</td>
			<td>
				<input style="width: 230px" type="text" name="email" value="<%=curStudent.getEmail()%>"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>Straße: </label>
			</td>
			<td>
				<input type="text" name="street" value="<%=curStudent.getStreet()%>"/>
			</td>
			<td>
				<label>PLZ: </label>
			</td>
			<td>
				<input type="text" name="plz" value="<%=curStudent.getPlz()%>"/>
			</td>
			<td>
				<label>Stadt: </label>
			</td>
			<td>
				<input style="width: 150px" type="text" name="city" value="<%=curStudent.getCity()%>"/>
			</td>
		</tr>
		<tr>
			<td>
				<label>Beruf: </label>
			</td>
			<td>
				<!-- <input type="text" name="job" value="<%=curStudent.getJob().getDescription()%>"/> -->
				<select id="lstJobs" style="width: 150px">
					<%
						// Hier berufe aus der Datenbank laden und als Option einfügen
					%>
				</select>
			</td>
			<td>
				<label>Firma: </label>
			</td>
			<td>
				<input type="text" name="company" value="<%=curStudent.getCompany().getName()%>"/>
			</td>
			<td colspan="2">
				<input type="checkbox" name="shortened" value="<%=curStudent.getShortened()%>" />
				 <label>Verkürzt</label>
			</td>
		</tr>
		<tr>
			<td>
				<label>Geburtstag: </label>
			</td>
			<td>
				<input type="text" name="birthday" value="<%=new SimpleDateFormat("dd.MM.yyyy").format(curStudent.getBirthday())%>"/>
			</td>
			<td>
				<label>Eintritt: </label>
			</td>
			<td>
				<input type="text" name="entry" value="<%=new SimpleDateFormat("dd.MM.yyyy").format(curStudent.getEntry())%>"/>
			</td>
			<td>
				<label>Religion: </label>
			</td>
			<td>
				<input  style="width: 150px" type="text" name="religion" value="<%=curStudent.getReligion().getDescription()%>"/>
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" id="btnSave" value="speichern"/>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>