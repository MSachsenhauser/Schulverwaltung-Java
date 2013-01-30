<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="elements.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="servlets.StudentDetailServlet" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>	
    <link rel="stylesheet" href="Styles/Themes/redmond/jquery-ui.css" />
    <script type="text/javascript" src="Scripts/Detail.js"></script>
    <script type="text/javascript" src="Scripts/Validation.js"></script>
    <script type="text/javascript" src="Scripts/Ajax.js"></script>
    <script type="text/javascript">
    	$(document).ready(function()
    	{
    		$("#tabControl").tabs();
    	});
    	
    	function expandDetails(ctrl, id)
    	{
    		var detailTable = document.getElementById("details_" + id);
    		var isExpanded = detailTable.style.display == "block";
    		if(isExpanded)
   			{
    			ctrl.src = "Images/Plus.png";
   				detailTable.style.display = "none";
   			}
    		else
    		{
    			ctrl.src = "Images/Minus.png";
    			detailTable.style.display = "block";
    		}
    	}
    	
    	function lstCompanyChange(ctrl)
    	{
    		UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadInstructors&CompanyId=" + ctrl.value, GotInstructors, false);
    	}
    	
    	function GotInstructors(result)
    	{
    		var lstInstructors = document.getElementById("lstInstructors");
    		lstInstructors.options.length = 0;
    		var instructors = result.split('|');
    		for(var i = 0; i < instructors.length; i++)
   			{
   				var instructor = instructors[i].split(";");
   				if(instructor[1] != "")
				{
					lstInstructors.options.add(new Option(instructor[1], instructor[0]));
				}
   			}
    	}
    </script>
</head>
<body>
<%
		int studentId = Integer.parseInt(request.getParameter("Id"));
		Student curStudent = new Student().setId(studentId);
		if(curStudent.getId() != -1)
		{
			curStudent.load();
		}
		String readonly = curStudent.getDisableflag()  > 0 ? "readonly=readonly" : "";
	%>
	<form id="form" method="Post" action="StudentDetailServlet">
	<input type="hidden" name="Id" value="<%= curStudent.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curStudent.getDisableflag() %>" />
		<div id="tabControl" style="heigth: 100%; overflow: hidden">
			<ul style="font-size: 70%">
		        <li><a href="#tab1">Allgemein</a></li>
		        <li><a href="#tab2">Noten</a></li>
		        <li><a href="#tab3">Fehlzeiten</a></li>
			</ul>
			<div id="tab1" style="heigth: 100%">
				<table style="heigth: 100%">
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
							<select <%=readonly%> name="Job" style="width: 100%">
								<option value="-1"></option>
								<%
									for(Job job:ElementLists.getJobs())
									{
										if(curStudent.getJobId() == job.getId())
										{
											out.write("<option selected=\"true\" value=\"" + job.getId() + "\">" + job.getDescription() + "</option>\n");
										}
										else
										{
											out.write("<option value=\"" + job.getId() + "\">" + job.getDescription() + "</option>\n");
										}
									}
								%>
							</select>
						</td>
						<td>
							<label>Firma: </label>
						</td>
						<td>
							<select <%=readonly%> name="company" style="width: 100%" onchange="lstCompanyChange(this)">
								<option value="-1"></option>
								<%
									for(Company company:ElementLists.getCompanies())
									{
										if(curStudent.getCompany().getId() == company.getId())
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
						<td>
							<label>Ausbilder: </label>
						</td>
						<td colspan="2">
							<select <%=readonly%> name="instructor" id="lstInstructors" style="width: 100%">
							 </select>
						</td>
					</tr>
					<tr>
						<td>
							<label>Geburtstag: </label>
						</td>
						<td>
							<input <%=readonly%> type="text" name="birthday" onblur="dateValidation(this)" value="<%=new SimpleDateFormat("dd.MM.yyyy").format(curStudent.getBirthday())%>"/>
						</td>
						<td>
							<label>Eintritt: </label>
						</td>
						<td>
							<input <%=readonly%> type="text" name="entry" onblur="dateValidation(this)" value="<%=new SimpleDateFormat("dd.MM.yyyy").format(curStudent.getEntry())%>"/>
						</td>
						<td>
							<label>Religion: </label>
						</td>
						<td>
							<select <%=readonly%> name="religion" style="width: 100%">
								<option value="-1"></option>
								<%
									for(Religion religion:ElementLists.getReligions())
									{
										if(curStudent.getReligionId() == religion.getId())
										{
											out.write("<option selected=\"true\" value=\"" + religion.getId() + "\">" + religion.getDescription() + "</option>\n");
										}
										else
										{
											out.write("<option value=\"" + religion.getId() + "\">" + religion.getDescription() + "</option>\n");
										}
									}
								%>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<input <%=readonly%> type="checkbox" id="shortened" name="shortened" value="<%=curStudent.getShortened()%>" />
							<label for="shortened">Verkürzt</label>
						</td>
					</tr>
					<tr style="height: 100%">
						<td style="heigth: 100%">&nbsp;</td>
					</tr>
					<tr>
						<td>
							<input <%=readonly != "" ? "disabled=disabled" : ""%> type="submit" value="speichern" />
						</td>
					</tr>
				</table>
			</div>
			<div id="tab2" style="width: 100%">
				<table style="width: 100%">
					<tr>
						<td></td>
						<td>Fach</td>
						<td>Lehrer</td>
						<td><nobr>Note &#216;</nobr></td>
						<td style="width: 100%"></td>
					</tr>
					<%
						SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
						for(ArrayList<Mark> list:curStudent.getMarks())
						{
							Mark firstElement = list.get(0);
							out.write("<tr>\n");
							out.write("		<td valign=\"middle\"><img onclick=\"expandDetails(this, '" + firstElement.getId() + "')\" src=\"Images/Plus.png\" style=\"width: 10px; height: 10px; margin-bottom: 2px; cursor: pointer;\" alt=\"Erweitern\"/></td>\n");
							out.write("		<td><label>&nbsp;&nbsp;" + firstElement.getExam().getGroupSubject().getSubject().getShortName() + "&nbsp;&nbsp;</label></td>\n");
							out.write("		<td><label>&nbsp;&nbsp;" + firstElement.getExam().getGroupSubject().getTeacher().getShortName() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label></td>");
							double avg = 0;
							double count = 0;
							StringBuilder subMarks = new StringBuilder();
							subMarks.append("<tr>");
							subMarks.append("<td colspan=\"5\">");
							subMarks.append("	<table id=\"details_" + firstElement.getId() + "\" style=\"display: none; border: 1px solid lightBlue;width: 50%; margin-left: 15px\">\n");
							subMarks.append("		<tr>\n");
							subMarks.append("			<th>Datum</th>\n");
							subMarks.append("			<th>Typ</th>\n");
							subMarks.append("			<th>Wertung</th>\n");
							subMarks.append("			<th>Note</th>\n");
							subMarks.append("		</tr>\n");
							for(Mark mark:list)
							{
								MarkType type = mark.getExam().getMarkType();
								count += type.getWeight();
								avg += mark.getMark() * type.getWeight();
								subMarks.append("	<tr>\n");
								subMarks.append("		<td>&nbsp;" + format.format(mark.getExam().getExecutionDate()) +"</td>\n");
								subMarks.append("		<td>&nbsp;&nbsp;" + type.getDescription() +"&nbsp;&nbsp;</td>\n");
								subMarks.append("		<td>&nbsp;&nbsp;" + type.getWeight() +"&nbsp;&nbsp;</td>\n");
								subMarks.append("		<td>&nbsp;&nbsp;" + mark.getMark() + " " + (!mark.getTrend().equals("0") ? mark.getTrend() : "") + "&nbsp;&nbsp;</td>\n");
								subMarks.append("	</tr>\n");
							}
							subMarks.append("	</table>");
							subMarks.append("</td>");
							subMarks.append("</tr>");
							out.write("		<td><label>" + ((avg / count * 100)/100) + "</label></td>");
							out.write("</tr>");
							out.write(subMarks.toString());
						}
					%>
				</table>
			</div>
			<div id="tab3">
			
			</div>
		</div>
	</form>
</body>
</html>