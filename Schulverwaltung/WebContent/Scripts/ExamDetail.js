function generateIHKGradingKey()
{
	var maxPointControl = document.getElementById("maxPoints");
	if(numberValidation(maxPointControl))
	{
		var maxPoints = maxPointControl.value;
		var points1 = Math.round(maxPoints * 0.92);
		var points2 = Math.round(maxPoints * 0.81);
		var points3 = Math.round(maxPoints * 0.67);
		var points4 = Math.round(maxPoints * 0.5);
		var points5 = Math.round(maxPoints * 0.30);
		if(maxPoints > 5)
		{
			document.getElementById("points1").value = maxPoints;
			document.getElementById("points2").value = points1 -1;
			document.getElementById("points3").value = points2 -1;
			document.getElementById("points4").value = points3 -1;
			document.getElementById("points5").value = points4 -1;
			document.getElementById("points6").value = points5 -1;
			
			document.getElementById("minPoints1").value = points1;
			document.getElementById("minPoints2").value = points2;
			document.getElementById("minPoints3").value = points3;
			document.getElementById("minPoints4").value = points4;
			document.getElementById("minPoints5").value = points5;
		}
	}
}

function generateMark(ctrl)
{
	var points = parseInt(ctrl.value);
	
	var points1 = document.getElementById("points1").value;
	var points2 = document.getElementById("points2").value;
	var points3 = document.getElementById("points3").value;
	var points4 = document.getElementById("points4").value;
	var points5 = document.getElementById("points5").value;
	
	var minPoints1 = document.getElementById("minPoints1").value;
	var minPoints2 = document.getElementById("minPoints2").value;
	var minPoints3 = document.getElementById("minPoints3").value;
	var minPoints4 = document.getElementById("minPoints4").value;
	var minPoints5 = document.getElementById("minPoints5").value;
	
	var mark = 0;
	var trend = 0;
	if(points <= points1 && points >= 0)
	{
		if(points <= points1 && points >= minPoints1)
		{
			mark = 1;
			if(points == points1)
			{
				trend = "+";
			}
			if(points == minPoints1)
			{
				trend = "-";
			}
		}
		if(points <= points2 && points >= minPoints2)
		{
			mark = 2;
			if(points == points2)
			{
				trend = "+";
			}
			if(points == minPoints2)
			{
				trend = "-";
			}
		}
		if(points <= points3 && points >= minPoints3)
		{
			mark = 3;
			if(points == points3)
			{
				trend = "+";
			}
			if(points == minPoints3)
			{
				trend = "-";
			}
		}
		if(points <= points4 && points >= minPoints4)
		{
			mark = 4;
			if(points == points4)
			{
				trend = "+";
			}
			if(points == minPoints4)
			{
				trend = "-";
			}
		}
		if(points <= points5 && points >= minPoints5)
		{
			mark = 5;
			if(points == points5)
			{
				trend = "+";
			}
			if(points == minPoints5)
			{
				trend = "-";
			}
		}
		if(points <= minPoints5 - 1 && points >= 0)
		{
			mark = 6;
			if(points == (minPoints5 - 1))
			{
				trend = "+";
			}
		}
		markControl(ctrl, false);
		var parentId = ctrl.parentNode.parentNode.id;
		if(parentId.contains("-"))
		{	
			parentId = parentId.split("-")[1];
		}
		document.getElementById("mark_" + parentId).value = mark;
		document.getElementById("trend_" + parentId).value = trend;
	}
	else
	{
		var parentId = ctrl.parentNode.parentNode.id;
		document.getElementById("mark_" + parentId).value = "";
		document.getElementById("trend_" + parentId).value = "0";
		markControl(ctrl, true);
	}
}

function loadSubjects(ctrl)
{
	var gradeId = ctrl.value;
	if(gradeId != null && gradeId != "")
	{
		UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadSubjectsFromGrade&GradeId=" + gradeId, insertSubjects, false);
	}
}

function insertSubjects(result)
{
	var subjectList = document.getElementById("lstSubjects");
	subjectList.options.length = 0;
	subjectList.options.add(new Option(" - ", -1));
	var subjects = result.split("|");
	for(var i = 0; i < subjects.length; i++)
	{
		var subject = subjects[i].split(";");
		if(subject[0] != "" && subject[1] != "")
		{
			subjectList.options.add(new Option(subject[1], subject[0]));
		}
	}
	var subjectId = document.getElementById("SubjectId").value;
	document.getElementById("lstSubjects").value = subjectId;
	loadStudents(document.getElementById("lstSubjects"));
}

function generateCustomGradingKey(ctrl)
{
	var id = ctrl.id.replace("minPoints", "");
	var points = ctrl.value;
	if(numberValidation(ctrl) && points > 0)
	{
		document.getElementById("points" + (parseInt(id) + 1)).value = points - 1;
	}
}

function loadStudents(ctrl)
{
	var subjectId = ctrl.value;
	var gradeId = document.getElementById("lstGrades").value;
	if(gradeId != null && gradeId != "")
	{
		UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadStudentsFromSubject&SubjectId=" + subjectId + "&GradeId=" + gradeId, insertStudents, false);
	}
}

function changeStudentState(ctrl)
{
	var parentId = ctrl.parentNode.parentNode.id;
	var points = document.getElementById("points_" + parentId);
	var mark = document.getElementById("mark_" + parentId);
	var trend = document.getElementById("trend_" + parentId);
	points.value ="";
	mark.value ="";
	trend.value = "0";
	if(ctrl.checked)
	{
		points.disabled = "";
		mark.disabled = "";
		trend.disabled = "";
	}
	else
	{
		markControl(points, false);
		markControl(mark, false);
		markControl(trend, false);
		points.disabled = "disabled";
		mark.disabled = "disabled";
		trend.disabled = "disabled";
	}
}

function insertStudents(result)
{
	var marks = document.getElementById("Marks").value.split("|");
	document.getElementById("Marks").value = "";
	var studentTable = document.getElementById("tab2");
	studentTable.innerHTML = "";
	var html = "<table>";
	html += " <tr>";
	html += "  <th style=\"text-align: left\">Anwesend</th>";
	html += "  <th style=\"text-align: left\">Name</th>";
	html += "  <th>Punkte</th>";
	html += "  <th>Note</th>";
	html += "  <th>Tendenz</th>";
	html += " </tr>";
	var students = result.split("|");
	for(var i = 0; i < students.length; i++)
	{
		var student = students[i].split(";");
		if(student[0] != null && student[0] != "")
		{ 
			var element = null;
			if(marks != "")
			{
				for(var j = 0; j < marks.length; j++)
				{
					var splittedMark = marks[j].split(";");
					if(splittedMark[1] == student[0])
					{
						element = splittedMark;
						break;
					}
				}
			}
			var  points = "";
			var mark = "";
			var trend = "0";
			var markId = "";
			var checked = "checked=\"checked\"";
			var disabled = "";
			var option0selected = "selected=\"selected\"";
			var option1selected = "";
			var option2selected = "";
			if (element != null)
			{
				//mark.getId() + ";" + mark.getStudentId() + ";" + mark.getPoints() + ";" + mark.getMark() + ";" + mark.getTrend()
				markId = element[0] + "-";
				points = element[2];
				mark = element[3];
				trend = element[4];
				if(trend == "0")
				{
					option0selected = "selected=\"selected\"";
				}
				if(trend == "+")
				{
					option0selected = "";
					option1selected = "selected=\"selected\"";
				}
				if(trend == "-")
				{
					option0selected = "";
					option2selected = "selected=\"selected\"";
				}
			}
			
			if(marks != "" && element == null)
			{
				checked = "";
				disabled = "disabled=\"disabled\"";
			}
			
			html += "<tr id=\"" + markId + student[0] + "\">";
			html += "  <td><input type=\"checkbox\" onclick=\"changeStudentState(this)\" id=\"chk_" + student[0] + "\" " + checked + " /></td>";
			html += "  <td style=\"width: 100%\"><label for=\"chk_" + student[0] + "\">" + student[1] + " " + student[2] + "</label></td>";
			html += "  <td><input class=\"noEnterSubmit\" " + disabled + " value=\"" + points + "\" id=\"points_" + student[0] + "\" type=\"text\" style=\"width: 50px\" onblur=\"numberValidation(this); generateMark(this);\"/></td>";
			html += "  <td><input class=\"noEnterSubmit\" " + disabled + " value=\"" + mark + "\" id=\"mark_" + student[0] + "\" type=\"text\" style=\"width: 50px\" onblur=\"numberValidation(this);\"/></td>";
			html += "  <td><select id=\"trend_" + student[0] + "\" " + disabled + "><option value=\"0\" " + option0selected + "></option><option value=\"+\" " + option1selected + ">+</option><option " + option2selected + " value=\"-\">-</option></select></td>";
			html += "</tr>";
		}
	}
	studentTable.innerHTML = html;
	$(".noEnterSubmit").keypress(function(e){
	    if (e.which == 13) 
    	{
    		e.preventDefault();
    	}
	});
}

function onLoad()
{
	loadSubjects(document.getElementById("lstGrades"));
}

function onSave()
{
	var table = document.getElementById("tab2").childNodes[0];
	if(table != null)
	{
		var studentMarks = "";
		for(var i = 0; i < table.childNodes[1].childNodes.length; i++)
		{
			var row = table.childNodes[1].childNodes[i];
			var rowId = row.id;
			var studentId = rowId;
			if(studentId.contains("-"))
			{	
				studentId = studentId.split("-")[1];
			}
			if(studentId != "")
			{
				if(document.getElementById("chk_" + studentId).checked)
				{
					var points = document.getElementById("points_" + studentId).value;
					var mark = document.getElementById("mark_" + studentId).value;
					var trend = document.getElementById("trend_" + studentId).value;
					studentMarks += rowId + "," + points + "," + mark + "," + trend + ";";
				}
			}
		}
		studentMarks = studentMarks.substr(0, studentMarks.length -1);
		document.getElementById("Marks").value = studentMarks;
	}
	
	document.forms[0].submit();
}