<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Elements.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>	
    <script type="text/javascript" src="Scripts/Detail.js"></script>
    <script type="text/javascript" src="Scripts/ExamDetail.js"></script>
    <script type="text/javascript" src="Scripts/Validation.js"></script>
</head>
<body>
<%
		int id = Integer.parseInt(request.getParameter("Id"));
		Exam curExam = new Exam().setId(id);
		if(curExam.getId() != -1)
		{
			curExam.load();
		}
		String readonly = false ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="ExamDetailServlet">
	<input type="hidden" name="Id" value="<%= curExam.getId() %>" />
	<input type="hidden" name="disableFlag" value="<%= curExam.getDisableflag() %>" />
	<table>
		<tr>
			<td>
				<label>Typ: </label>
			</td>
			<td>
				<select name="type" style="width: 100px">
				<option value="-1"> - </option>
					<%
						for(MarkType type:ElementLists.getMarkTypes())
						{
							if(curExam.getTypeId() == type.getId())
							{
								out.write("<option selected=\"true\" value=\"" + type.getId() + "\">" + type.getDescription() + "</option>\n");
							}
							else
							{
								out.write("<option value=\"" + type.getId() + "\">" + type.getDescription() + "</option>\n");
							}
						}
					%>
				</select>
			</td>
			<td>
				<label>Datum:</label>
			</td>
			<td>
				<input name="executionDate" style="width: 75px" onblur="dateValidation(this);"/>
			</td>
			<td>
				<label>Ankündigungsdatum:</label>
			</td>
			<td>
				<input name="announceDate" style="width: 75px"  onblur="dateValidation(this);"/>
			</td>
			<td>
				<label>Fach:</label>
			</td>
			<td>
				<select name="subject" style="width: 200px">
				<option value="-1"> - </option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<label><nobr>Max. Punkte: </nobr></label>
				<input name="maxPoints" type="number" id="maxPoints" style="width: 50px" onblur="numberValidation(this);"/>
			</td>
			<td colspan="2">
				<input type="button" value="IHK-Schlüssel verwenden" onclick="generateIHKGradingKey()"/>
			</td>
		</tr>
		<tr>
			<td colspan="8">
				<label>Min. Punkte 1:</label>
				<input type="number" name="minPoints1" id="minPoints1" style="width: 50px" onblur="numberValidation(this);" />
				<label>Min. Punkte 2:</label>
				<input type="number" name="minPoints2" id="minPoints2" style="width: 50px" onblur="numberValidation(this);" />
				<label>Min. Punkte 3:</label>
				<input type="number" name="minPoints3" id="minPoints3" style="width: 50px" onblur="numberValidation(this);" />
				<label>Min. Punkte 4:</label>
				<input type="number" name="minPoints4" id="minPoints4" style="width: 50px" onblur="numberValidation(this);" />
				<label>Min. Punkte 5:</label>
				<input type="number" name="minPoints5" id="minPoints5" style="width: 50px" onblur="numberValidation(this);" />
			</td>
		</tr>
		<tr>
			<td>
				<input type="submit" id="btnSubmit" value="speichern" />
			</td>
		</tr>
	</table>
	</form>
</body>
</html>