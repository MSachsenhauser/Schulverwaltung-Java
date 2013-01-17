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
    <link rel="stylesheet" href="Styles/Themes/redmond/jquery-ui.css" />
    <script type="text/javascript" src="Scripts/Detail.js"></script>
    <script type="text/javascript" src="Scripts/ExamDetail.js"></script>
    <script type="text/javascript" src="Scripts/Validation.js"></script>
    <script>
    	$(document).ready(function()
		{
    		$("#tabControl").tabs({ heightStyle: "fill" });
		});
    </script>
</head>
<body style="background: lightBlue; ">
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
	<div id="tabControl" style="background: lightBlue; width: 99%; height:400px; margin-left: -10px; overflow: hidden">
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
				<tr><td></td></tr>
				<tr>
					<td colspan="4">
						<label> 1:</label>
						<input type="number" readonly="readonly" name="points1" id="points1" style="width: 50px" />
						<label> - </label>
						<input type="number" name="minPoints1" id="minPoints1" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
					
				</tr>
				<tr>
					<td colspan="4">
						<label> 2:</label>
						<input type="number" readonly="readonly" name="points2" id="points2" style="width: 50px" />
						<label> - </label>
						<input type="number" name="minPoints2" id="minPoints2" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<label> 3:</label>
						<input type="number" readonly="readonly" name="points3" id="points3" style="width: 50px" />
						<label> - </label>
						<input type="number" name="minPoints3" id="minPoints3" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<label> 4:</label>
						<input type="number" readonly="readonly" name="points4" id="points4" style="width: 50px" />
						<label> - </label>
						<input type="number" name="minPoints2" id="minPoints4" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<label> 5:</label>
						<input type="number" readonly="readonly" name="points5" id="points5" style="width: 50px" />
						<label> - </label>
						<input type="number" name="minPoints5" id="minPoints5" style="width: 50px" onblur="numberValidation(this); generateCustomGradingKey(this);" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<label> 6:</label>
						<input type="number" readonly="readonly" name="points6" id="points6" style="width: 50px" />
						<label> - </label>
						<input type="number" readonly="readonly" style="width: 50px" value="0"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<input type="submit" id="btnSubmit" value="speichern" />
					</td>
				</tr>
			</table>
		</div>
		<div id="tab2">
			
		</div>
	</div>
	</form>
</body>
</html>