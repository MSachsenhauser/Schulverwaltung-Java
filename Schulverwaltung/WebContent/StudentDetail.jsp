<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="elements.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="helpers.*" %>
<%@ page import="servlets.StudentDetailServlet" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="Scripts/jquery.js"></script>
    <script src="Scripts/jquery-ui.js"></script>	
    <script src="Scripts/jquery-cookie.js"></script>
    <link rel="stylesheet" href="Styles/Themes/redmond/jquery-ui.css" />
    <script type="text/javascript" src="Scripts/Detail.js"></script>
    <script type="text/javascript" src="Scripts/Validation.js"></script>
    <script type="text/javascript" src="Scripts/Ajax.js"></script>
    <script type="text/javascript">
    	$(document).ready(function()
    	{
    		$("#tabControl").tabs({ cookie: { expires: 7 } });
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
    	
    	function onLoad()
    	{
    		lstCompanyChange(document.getElementById("lstCompany"));
    	}
    	
    	function lstCompanyChange(ctrl)
    	{
    		UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadInstructors&CompanyId=" + ctrl.value, GotInstructors, false);
    	}
    	
    	function GotInstructors(result)
    	{
    		var instructorId = document.getElementById("InstructorId").value;
    		var lstInstructors = document.getElementById("lstInstructors");
    		lstInstructors.options.length = 0;
    		var instructors = result.split('|');
    		for(var i = 0; i < instructors.length; i++)
   			{
   				var instructor = instructors[i].split(";");
   				if(instructor[0] != null && instructor[1] != null)
				{
					lstInstructors.options.add(new Option(instructor[1], instructor[0]));
				}
   			}
    		
    		lstInstructors.value = instructorId;
    	}
    	
    	function copyGuardianAdress()
    	{
    		document.getElementById("txtGuardianStreet2").value = document.getElementById("txtGuardianStreet1").value;
    		document.getElementById("txtGuardianPLZ2").value = document.getElementById("txtGuardianPLZ1").value;
    		document.getElementById("txtGuardianCity2").value = document.getElementById("txtGuardianCity1").value;
    		document.getElementById("txtGuardianPhone2").value = document.getElementById("txtGuardianPhone1").value;
    	}
    	
    	function recalcDuration()
    	{
    		var txtBegin = document.getElementById("txtNewAbsenceBegin");
    		var txtEnd = document.getElementById("txtNewAbsenceEnd");
    		if(txtBegin.value != "" && txtEnd.value != "")
   			{
    			var splitDate1 = txtBegin.value.split(".");
   				var date1 = new Date(splitDate1[2], (parseInt(splitDate1[1]) - 1), splitDate1[0]);
   				var splitDate2 = txtEnd.value.split(".");
   				var date2 = new Date(splitDate2[2], (parseInt(splitDate2[1]) - 1), splitDate2[0]);
   				var dayMillis = 1000*60*60*24;
   				document.getElementById("txtNewAbsenceDuration").value = Math.ceil((date2.getTime() - date1.getTime()) / dayMillis + 1);
   			}
    	}
    	
    	function recalcDelayDuration()
    	{
    		var txtBegin = document.getElementById("txtNewDelayBegin");
    		var txtEnd = document.getElementById("txtNewDelayEnd");
    		if(txtBegin.value != "" && txtEnd.value != "")
   			{
    			var splitDate1 = txtBegin.value.split(".");
    			var timeSplit = splitDate1[2].split(" ");
    			var time = timeSplit[1].split(":");
   				var date1 = new Date(timeSplit[0], (parseInt(splitDate1[1]) - 1), splitDate1[0], time[0], time[1], 0);
   				var splitDate2 = txtEnd.value.split(".");
   				timeSplit = splitDate2[2].split(" ");
    			time = timeSplit[1].split(":");
   				var date2 = new Date(timeSplit[0], (parseInt(splitDate2[1]) - 1), splitDate2[0], time[0], time[1], 0);
   				var minuteMillis = 1000*60;
   				document.getElementById("txtNewDelayDuration").value = Math.ceil((date2.getTime() - date1.getTime()) / minuteMillis);
   			}
    	}
    	
    	function saveAbsence()
    	{		
			var txtBegin = document.getElementById("txtNewAbsenceBegin");
    		var txtEnd = document.getElementById("txtNewAbsenceEnd");
    		var chkExcusedByPhone = document.getElementById("chkNewAbsenceExcusedByPhone");
    		var chkExcusedByEmail = document.getElementById("chkNewAbsenceExcusedByEmail");
    		var chkCertificate = document.getElementById("chkNewAbsenceCertificate");
    		
    		if(!markControl(txtBegin, txtBegin.value == "") && !markControl(txtEnd, txtEnd.value == ""))
   			{
    				UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=saveAbsence&StudentId=" + document.getElementById("Id").value + 
    				"&Begin=" + txtBegin.value + 
    				"&End=" + txtEnd.value + 
    				"&ExcusedByPhone=" + chkExcusedByPhone.checked + 
    				"&ExcusedByEmail=" + chkExcusedByEmail.checked + 
    				"&Certificate=" + chkCertificate.checked, insertAbsence, false);
   			}
    	}
    	
    	function saveDelay()
    	{
    		var txtBegin = document.getElementById("txtNewDelayBegin");
    		var txtEnd = document.getElementById("txtNewDelayEnd");
    		var chkValid = document.getElementById("chkNewDelayValid");
    		var txtDescription = document.getElementById("chkNewDelayDescription");
    		
    		if(!markControl(txtBegin, txtBegin.value == "") && !markControl(txtEnd, txtEnd.value == ""))
   			{
    				UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=saveDelay&StudentId=" + document.getElementById("Id").value + 
    				"&Begin=" + txtBegin.value + 
    				"&End=" + txtEnd.value + 
    				"&Description=" + txtDescription.value + 
    				"&Valid=" + chkValid.checked, insertDelay, false);
   			}
    	}
    	
    	function insertDelay(result)
    	{
    		var txtBegin = document.getElementById("txtNewDelayBegin");
    		var txtEnd = document.getElementById("txtNewDelayEnd");
    		var chkValid = document.getElementById("chkNewDelayValid");
    		var txtDescription = document.getElementById("txtNewDelayDescription");
			var txtDuration = document.getElementById("txtNewDelayDuration");
			window.location.reload();
			txtBegin.value ="";
			txtEnd.value ="";
			txtDescription.value ="";
			txtDuration.value ="";
			chkValid.checked = "";
    	}
    	
    	function deleteDelay(id)
    	{
    		if(window.confirm("Soll die Verspätung wirklich gelöscht werden?"))
   			{
    			UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=deleteDelay&Id=" + id, removeDelay, false);
   			}
    	}
    	
    	function removeDelay(result)
    	{
    		var tableDelay = document.getElementById("tblDelay");
    		window.location.reload();
			// ToDo: hier aus der Tabelle entfernen bzw die seite neu laden 
    	}
    	
    	function insertAbsence(result)
    	{
    		var txtBegin = document.getElementById("txtNewAbsenceBegin");
    		var txtEnd = document.getElementById("txtNewAbsenceEnd");
    		var chkExcusedByPhone = document.getElementById("chkNewAbsenceExcusedByPhone");
    		var chkExcusedByEmail = document.getElementById("chkNewAbsenceExcusedByEmail");
    		var chkCertificate = document.getElementById("chkNewAbsenceCertificate");
			var txtDuration = document.getElementById("txtNewAbsenceDuration");
    		var tr = "<tr>";
    		tr += "<td><label><nobr>" + txtBegin.value + "</nobr></label></td>";
    		tr += "<td><label><nobr>" + txtEnd.value + "</nobr></label></td>";
    		tr += "<td align=\"center\"><label>" + txtDuration.value + "</label></td>";
    		tr += "<td align=\"center\"><input disabled=disabled type=\"checkbox\" " + (chkExcusedByPhone.checked != "" ? "checked=\"checked\"" : "") + " /></td>";
    		tr += "<td align=\"center\"><input disabled=disabled type=\"checkbox\" " + (chkExcusedByEmail.checked != "" ? "checked=\"checked\"" : "") + " /></td>";
    		tr += "<td align=\"center\"><input disabled=disabled type=\"checkbox\" " + (chkCertificate.checked != "" ? "checked=\"checked\"" : "") + " /></td>";
    		var tableAbsence = document.getElementById("tblAbsence");
			//tableAbsence.childNodes[1].childNodes.append(tr);
			// ToDo: hier in die Tabelle einfügen bzw die seite neu laden 7
			window.location.reload();
			txtBegin.value = "";
			txtEnd.value = "";
			txtDuration.value = "";
			chkExcusedByPhone.checked = "";
			chkExcusedByEmail.checked = "";
			chkCertificate.checked = "";
    	}
    	
    	function deleteAbsence(id)
    	{
    		if(window.confirm("Soll der Krankheitseintrag wirklich gelöscht werden?"))
   			{
    			UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=deleteAbsence&Id=" + id, removeAbsence, false);
   			}
    	}
    	
    	function removeAbsence(result)
    	{
    		var tableAbsence = document.getElementById("tblAbsence");
    		window.location.reload();
    	}
    	
    </script>
</head>
<body onload="onLoad();">
	<%
		int studentId = Integer.parseInt(request.getParameter("Id"));
		Student curStudent = new Student().setId(studentId);
		if(curStudent.getId() != -1)
		{
			curStudent.load();
		}
		
		String readonly = curStudent.getDisableflag()  > 0 ? "readonly=readonly" : "";
		SimpleDateFormat delayFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		SimpleDateFormat absenceFormatter = new SimpleDateFormat("dd.MM.yyyy");
	%>
	<form id="form" method="Post" action="StudentDetailServlet">
	<input type="hidden" name="Id" id="Id" value="<%= curStudent.getId() %>" />
	<input type="hidden" name="DisableFlag" value="<%= curStudent.getDisableflag() %>" />
	<input type="hidden" id="InstructorId" value="<%= curStudent.getInstructorId() %>" />
	<input type="hidden" name="GuardianId1" value="<%= curStudent.getGuardianId1() %>" />
	<input type="hidden" name="GuardianId2" value="<%= curStudent.getGuardianId2() %>" />
		<div id="tabControl" style="heigth: 100%; overflow: hidden">
			<ul style="font-size: 70%">
		        <li><a href="#tab1">Allgemein</a></li>
		        <li><a href="#tab5">Eltern</a></li>
		        <%
		        	if(curStudent.getId() > 0)
		        	{
		        		out.write("<li><a href=\"#tab2\">Noten</a></li>");
		        		out.write("<li><a href=\"#tab3\">Krankheit</a></li>\n");
		        		out.write("<li><a href=\"#tab4\">Fehlzeiten</a></li>\n");
		        	}
		        %>
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
							<select <%=readonly%> name="company" id="lstCompany" style="width: 100%" onchange="lstCompanyChange(this)">
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
							<select <%=readonly%> name="Instructor" id="lstInstructors" style="width: 100%">
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
						<td colspan="6">
							<table>
								<tr>
									<td colspan="2">
										<input <%=readonly%> type="checkbox" id="shortened" name="shortened" <%=curStudent.getShortened() ? "checked=\"checked\"" : "" %> />
										<label for="shortened">Verkürzt</label>
									</td>
									<td>&nbsp;</td>
									<td colspan="2">
										<input <%=readonly%> type="checkbox" id="sportFree" name="sportFree" <%=curStudent.getIsSportFree() ? "checked=\"checked\"" : "" %> />
										<label for="sportFree">Sport befreit</label>
									</td>
									<td>&nbsp;</td>
									<td colspan="2">
										<input <%=readonly%> type="checkbox" id="germanFree" name="germanFree" <%=curStudent.getIsGermanFree() ? "checked=\"checked\" ": "" %> />
										<label for="germanFree">Deutsch befreit</label>
									</td>
									<td>&nbsp;</td>
									<td colspan="2">
										<input <%=readonly%> type="checkbox" id="religonFree" name="religionFree" <%=curStudent.getIsReligionFree() ? "checked=\"checked\"" : "" %> />
										<label for="religonFree">Religion befreit</label>
									</td>
									<td>&nbsp;</td>
									<td>Vorbildung: </td>
									<td>
										<select <%=readonly%> name="TypificationId" style="width: 200px">
											<option value="-1"> - </option>
											<%
												for(Typification typification:ElementLists.getTypifications())
												{
													if(curStudent.getTypificationId() == typification.getId())
													{
														out.write("<option selected=\"true\" value=\"" + typification.getId() + "\">" + typification.getDescription() + "</option>\n");
													}
													else
													{
														out.write("<option value=\"" + typification.getId() + "\">" + typification.getDescription() + "</option>\n");
													}
												}
											%>
										</select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr style="height: 100%">
						<td style="heigth: 100%">&nbsp;</td>
					</tr>
					
					<tr>
						<td>
							<input <%=readonly != "" ? "disabled=disabled" : ""%> type="submit" id="btnSubmit" value="speichern" />
						</td>
					</tr>
				</table>
			</div>
			<div id="tab5" style="width: 100%">
				<table style="width: 100%">
					<tr>
						<td>Name: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianName1" name="guardianName1" value="<%= curStudent.getGuardian1().getName() %>"/></td>
						<td>Vorname: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianFirstName1" name="guardianFirstName1"  value="<%= curStudent.getGuardian1().getFirstname() %>"/></td>
						<td>Telefon: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianPhone1" name="guardianPhone1"  value="<%= curStudent.getGuardian1().getPhone() %>"/></td>
					</tr>
					<tr>
						<td>Straße: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianStreet1" name="guardianStreet1" value="<%= curStudent.getGuardian1().getStreet() %>"/></td>
						<td>PLZ: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianPLZ1" name="guardianPLZ1" value="<%= curStudent.getGuardian1().getPlz() %>"/></td>
						<td>Stadt: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianCity1" name="guardianCity1" value="<%= curStudent.getGuardian1().getCity() %>"/></td>
					</tr>
					<tr><td>&nbsp;</td></tr>
										<tr>
						<td>Name: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianName2" name="guardianName2"  value="<%= curStudent.getGuardian2().getName() %>"/></td>
						<td>Vorname: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianFirstName2" name="guardianFirstName2" value="<%= curStudent.getGuardian2().getFirstname() %>" /></td>
						<td>Telefon: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianPhone2" name="guardianPhone2" value="<%= curStudent.getGuardian2().getPhone() %>" /></td>
					</tr>
					<tr>
						<td>Straße: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianStreet2" name="guardianStreet2" value="<%= curStudent.getGuardian2().getStreet() %>"/></td>
						<td>PLZ: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianPLZ2" name="guardianPLZ2"  value="<%= curStudent.getGuardian2().getPlz() %>"/></td>
						<td>Stadt: </td>
						<td><input <%=readonly%> type="text" id="txtGuardianCity2" name="guardianCity2"  value="<%= curStudent.getGuardian2().getCity() %>"/></td>
					</tr>
					<tr>
						<td colspan="6"><input <%= !readonly.isEmpty() ? "disabled=disabled" : "" %> type="button" value="Adresse übernehmen" onClick="copyGuardianAdress()"/></td>
					</tr>
				</table>
			</div>
			<div id="tab2" style="width: 100%; display: none">
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
			<div id="tab3" style="width: 90%; display: none">
				<table style="width: 90%;" border="1" id="tblAbsence">
					<tr>
						<th>Beginn</th>
						<th>Ende</th>
						<th>Dauer (Tage)</th>
						<th>Entschuldigt(Telefon)</th>
						<th>Entschuldigt(Schriftlich)</th>
						<th>Attest</th>
						<th></th>
					</tr>
					<tr>
						<td><input style="width: 110px" type="text" id="txtNewAbsenceBegin" onblur="dateValidation(this); recalcDuration();"/></td>
						<td><input style="width: 110px" type="text" id="txtNewAbsenceEnd" onblur="dateValidation(this); recalcDuration();"/></td>
						<td><input style="width: 50px" type="text" readonly="readonly" id="txtNewAbsenceDuration" /></td>
						<td align="center"><input type="checkbox" id="chkNewAbsenceExcusedByPhone" /></td>
						<td align="center"><input type="checkbox" id="chkNewAbsenceExcusedByEmail" /></td>
						<td align="center"><input type="checkbox" id="chkNewAbsenceCertificate" /></td>
						<td><input type="button" style="width: 30px" value="+" onclick="saveAbsence()" /></td>
					</tr>
					<%
						for(Absence absence:curStudent.getAbsence())
						{
							out.write("<tr id=\"abs_" + absence.getId() + "\">\n");
							out.write("	<td>\n");
							out.write("		<label><nobr>" + absenceFormatter.format(absence.getStart()) + "</nobr></label>");
							out.write("	</td>\n");
							out.write("	<td>\n");
							out.write("		<label><nobr>" + absenceFormatter.format(absence.getEnd()) + "</nobr></label>");
							out.write("	</td>\n");
							out.write("	<td align=\"center\">\n");
							out.write("		<label>" + (DateHelper.DateDiffDays(absence.getStart(), absence.getEnd()) + 1)	 + "</label>");
							out.write("	</td>\n");
							out.write("	<td align=\"center\">\n");
							out.write("		<input  disabled=disabled type=\"checkbox\" " + (absence.getExcusedByPhone() ? "checked=\"checked\"" : "") + " />");
							out.write("	</td>\n");
							out.write("	<td align=\"center\">\n");
							out.write("		<input  disabled=disabled type=\"checkbox\" " + (absence.getExcusedByEmail() ? "checked=\"checked\"" : "") + " />");
							out.write("	</td>\n");
							out.write("	<td align=\"center\">\n");
							out.write("		<input disabled=disabled type=\"checkbox\" " + (absence.getCertificate() ? "checked=\"checked\"" : "") + " />");
							out.write("	</td>\n");
							out.write("	<td align=\"center\">\n");
							out.write("		<input style=\"width: 30px\" type=\"button\" value=\"-\" onclick=\"deleteAbsence(" + absence.getId() + ")\" />");
							out.write("	</td>\n");
							out.write("</tr>");
						}
					%>
				</table>
			</div>
			<div id="tab4" style="width: 90%; display: none">
				<table style="width: 90%;" border="1">
					<tr>
						<th>Beginn</th>
						<th>Ende</th>
						<th>Dauer</th>
						<th>Beschreibung</th>
						<th>Zählt</th>
						<th></th>
					</tr>
					<tr>
						<td><input style="width: 180px" type="text" id="txtNewDelayBegin" onblur="dateTimeValidation(this); recalcDelayDuration();"/></td>
						<td><input style="width: 180px" type="text" id="txtNewDelayEnd" onblur="dateTimeValidation(this); recalcDelayDuration();"/></td>
						<td><input style="width: 50px" type="text" readonly="readonly" id="txtNewDelayDuration" /></td>
						<td><input type="text" id="chkNewDelayDescription" style="width: 150px"/></td>
						<td align="center"><input type="checkbox" id="chkNewDelayValid" /></td>
						<td><input type="button" style="width: 30px" value="+" onclick="saveDelay()" /></td>
					</tr>
					<%
						for(Delay delay:curStudent.getDelays())
						{
							out.write("<tr>\n");
							out.write("	<td>\n");
							out.write("		<label><nobr>" + delayFormatter.format(delay.getStart()) + "</nobr></label>");
							out.write("	</td>\n");
							out.write("	<td>\n");
							out.write("		<label><nobr>" + delayFormatter.format(delay.getEnd()) + "</nobr></label>");
							out.write("	</td>\n");
							out.write("	<td align=\"center\">\n");
							out.write("		<label>" + (DateHelper.DateDiff(delay.getStart(), delay.getEnd()) / 1000 / 60) + "</label>");
							out.write("	</td>\n");
							out.write("	<td align=\"center\">\n");
							out.write("		<label>" + delay.getDescription() + "</label>");
							out.write("	</td>\n");
							out.write("	<td align=\"center\">\n");
							out.write("		<input  disabled=disabled type=\"checkbox\" " + (delay.getValid() ? "checked=\"checked\"" : "") + " />");
							out.write("	</td>\n");
							out.write("	<td align=\"center\">\n");
							out.write("		<input style=\"width: 30px\" type=\"button\" value=\"-\" onclick=\"deleteDelay(" + delay.getId() + ")\" />");
							out.write("	</td>\n");
							out.write("</tr>");
						}
					%>
				</table>
			</div>
		</div>
	</form>
</body>
</html>