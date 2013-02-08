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
    <link rel="stylesheet" href="Styles/Themes/redmond/jquery-ui.css" />
    <script type="text/javascript" src="Scripts/Detail.js"></script>
    <script type="text/javascript" src="Scripts/ExamDetail.js"></script>
    <script type="text/javascript" src="Scripts/Ajax.js"></script>
    <script type="text/javascript" src="Scripts/Validation.js"></script>
    <script>
    	$(document).ready(function()
		{
    		$("#tabControl").tabs({ heightStyle: "fill" });
    		$(".noEnterSubmit").keypress(function(e){
    		    if ( e.which == 13 ) e.preventDefault();
    		});
		});
    </script>
</head>
<body style="background: lightBlue; " onload="onLoad()">
	<%
		int id = Integer.parseInt(request.getParameter("Id"));
		Exam curExam = new Exam().setId(id);
		if(curExam.getId() != -1)
		{
			curExam.load();
		}
		String readonly = curExam.getDisableflag()  > 0 ? "readonly=readonly" : "";
		DecimalFormat df = new DecimalFormat("0.00");
	%>
	<form id="form" method="Post" action="ExamDetailServlet">
	<input type="hidden" name="Id" value="<%= curExam.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curExam.getDisableflag() %>" />
	<input type="hidden" name="Marks" id="Marks" value="<%= curExam.getMarkArrayString("|") %>"/>
	<input type="hidden" id="SubjectId" value="<%= curExam.getGroupSubjectId() %>"/>
	<div id="tabControl" style="background: lightBlue; width: 99%; height:500px; margin-left: -10px; overflow: hidden">
		<ul style="font-size: 70%">
	        <li><a href="#tab1">Allgemein</a></li>
	        <li><a href="#tab2">Schüler</a></li>
		</ul>
		<div id="tab1" style="overlow: hidden">
			<table style="height: 300px">
				<tr>
					<td>
						<label>Typ: </label>
					</td>
					<td>
						<select name="Type" style="width: 200px">
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
						<input name="ExecutionDate" value="<%= new SimpleDateFormat("dd.MM.yyyy").format(curExam.getExecutionDate()) %>" class="noEnterSubmit" style="width: 100px" onblur="dateValidation(this);"/>
					</td>
					<td>
						<label>Ankündigungsdatum:</label>
					</td>
					<td>
						<input name="AnnounceDate" value="<%= new SimpleDateFormat("dd.MM.yyyy").format(curExam.getAnnounceDate()) %>" class="noEnterSubmit"  style="width: 100px"  onblur="dateValidation(this);"/>
					</td>
					</tr>
					<tr>
					<td>
						<label>Klasse:</label>
					</td>
					<td colspan="2">
						<select name="grade" id="lstGrades" style="width: 100px" onchange="loadSubjects(this);">
							<option value="-1"> - </option>
							<%
								for(Grade grade:ElementLists.getGrades())
								{
									if(curExam.getGroupSubject().getGroup().getGradeId() == grade.getId())
									{
										out.write("<option selected=\"true\" value=\"" + grade.getId() + "\">" + grade.getDescription() + "</option>\n");
									}
									else
									{
										out.write("<option value=\"" + grade.getId() + "\">" + grade.getDescription() + "</option>\n");
									}
								}
							%>
						</select>
					</td>
					<td>
						<label>Fach:</label>
					</td>
					<td colspan="3">
						<select name="Subject" id="lstSubjects" style="width: 200px" onchange="loadStudents(this)">
						<option value="-1"> - </option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<label><nobr>Max. Punkte: </nobr></label>
						<input name="maxPoints" class="noEnterSubmit" value="<%= curExam.getMaxPoints() > 0 ? curExam.getMaxPoints() : "" %>" type="number" id="maxPoints" style="width: 50px" onblur="numberValidation(this);"/>
						<label>&nbsp;&nbsp;&nbsp;&nbsp;&#216; <%= curExam.getAvarage() > 0 ? df.format(curExam.getAvarage()) : "" %></label>
					</td>
					<td colspan="3" rowspan="9" valign="top">
						<label>Beschreibung: </label>
						<input style="width: 300px" type="text" name="Description" value="<%= curExam.getDescription() %>"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="IHK-Schlüssel verwenden" onclick="generateIHKGradingKey()"/>
					</td>
				</tr>
				<tr><td></td></tr>
				<tr>
					<td colspan="4">
						<label> 1:</label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMaxPoints() > 0 ? curExam.getMaxPoints() : ""%>" readonly="readonly" name="points1" id="points1" style="width: 50px" />
						<label> - </label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints1() > 0 ? curExam.getMinPoints1() : "" %>" name="minPoints1" id="minPoints1" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
					
				</tr>
				<tr>
					<td colspan="4">
						<label> 2:</label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints1() - 1 > 0 ? curExam.getMinPoints1() - 1 : ""%>" readonly="readonly" name="points2" id="points2" style="width: 50px" />
						<label> - </label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints2() > 0 ? curExam.getMinPoints2(): "" %>" name="minPoints2" id="minPoints2" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<label> 3:</label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints2() - 1 > 0 ? curExam.getMinPoints2() -1 : ""  %>" readonly="readonly" name="points3" id="points3" style="width: 50px" />
						<label> - </label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints3() > 0 ? curExam.getMinPoints3(): ""  %>" name="minPoints3" id="minPoints3" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<label> 4:</label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints3() - 1 > 0 ? curExam.getMinPoints3() -1 : "" %>" readonly="readonly" name="points4" id="points4" style="width: 50px" />
						<label> - </label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints4() > 0 ? curExam.getMinPoints4(): ""  %>" name="minPoints4" id="minPoints4" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<label> 5:</label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints4() - 1 > 0 ? curExam.getMinPoints4() -1 : "" %>" readonly="readonly" name="points5" id="points5" style="width: 50px" />
						<label> - </label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints5() > 0 ? curExam.getMinPoints5(): ""  %>" name="minPoints5" id="minPoints5" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<label> 6:</label>
						<input type="number" class="noEnterSubmit" value="<%= curExam.getMinPoints5() - 1 > 0 ? curExam.getMinPoints5() -1 : "" %>" readonly="readonly" name="points6" id="points6" style="width: 50px" />
						<label> - </label>
						<input type="number" class="noEnterSubmit" readonly="readonly" style="width: 50px" value="0"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<input type="button" onclick="onSave()" id="btnSubmit" value="speichern" />
					</td>
				</tr>
			</table>
		</div>
		<div id="tab2" style="overflow: scroll;">
			
		</div>
	</div>
	</form>
</body>
</html>