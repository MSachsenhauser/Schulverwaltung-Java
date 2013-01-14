var $k = jQuery.noConflict();
var weekList = new Array();
var timeTable = new Array();
var subjectOptions = new Array();
function init()
{
	weekList.push("Montag");
	weekList.push("Dienstag");
	weekList.push("Mittwoch");
	weekList.push("Donnerstag");
	weekList.push("Freitag");
	for(var i = 0; i < 5; i++)
	{
		var hours = new Array();
		for(var x = 0; x < 10; x++)
		{
			hours.push(-1);
		}
		timeTable.push(hours);
	}
}

function closeDialog()
{
	document.getElementById("lstSubjects").value = "-1";
	$k("#dialog").dialog('close'); 
}

var curColumn = -1;
var curRow = -1;
function openHourSelector(column, row)
{
	var group = document.getElementById("lstGroups").value;
	var grade = document.getElementById("lstGrades").value;
	if(!markElement(group == null || group == "" || group == "-1", "lstGroups") && 
	   !markElement(grade == null || grade == "" || grade == "-1", "lstGrades"))
	{

		if(openDialog)
		 {
			curColumn = column;
			curRow = row;
			$k("#dialog").dialog({
		        autoOpen: true,
		        title: weekList[column] + " " + (row +1) + ". Stunde",
		        modal: true, width: 350, height: 200});
		 }
		 openDialog = true;
	}
}

function markElement(needMark, elementName)
{
	var element = document.getElementById(elementName);
	if(needMark)
	{
		element.style.borderColor = "Red";
		element.style.borderStyle = "Solid";
		element.style.borderWidth = "1";
	}
	else
	{
		element.style.borderColor = "";
		element.style.borderStyle = "";
		element.style.borderWidth = "";
	}
	
	return needMark;
}

function submitHourSelection()
{
	var subject = document.getElementById("lstSubjects").value;
	var error = false;
	error = markElement((subject == null || subject == "" || subject == "-1"), "lstSubjects");

	if(!error)
	{
		timeTable[curColumn][curRow] = subject;
		UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadHourDetails&SubjectId=" + subject, setHourContent, false);
		closeDialog();
	}
}

var openDialog = true;

function removeHour(column, row)
{
	var hourCell = document.getElementById("hour_" + column + "_" + row);
	hourCell.innerHTML = "";
	timeTable[column][row] = -1;
	openDialog = false;
}

function setHourContent(result)
{
	var details = result.split(";");
	var hourCell = document.getElementById("hour_" + curColumn + "_" + curRow);
	var html = "<table style=\"width: 100%; font-size: smaller\" cellspacing=\"0\" cellpadding=\"0\">";
	html += "<tr>";
	html += "<td align=\"center\"><b>" + details[0] + "</b></td>";
	html += "<td align=\"right\"><img src=\"Images/delete.gif\" onclick=\"removeHour(" + curColumn + ", " + curRow + ")\"/></td>";
	html += "</tr>";
	html += "<tr>";
	html += "<td align=\"center\">&nbsp;&nbsp;<b>" + details[1] + "</b></td>";
	html += "</tr>";
	html += "<tr>";
	html += "<td align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + details[2] + "</td>";
	html += "</tr>";
	html += "<tr>";
	html += "</tr>";
	html += "</table>";
	hourCell.innerHTML = html;
	
	curColumn = -1;
	curRow = -1;
}

function onSave()
{
	var groupId = document.getElementById("lstGroups").value;
	var validTill = document.getElementById("validTill").value;
	if(!markElement(groupId == null || groupId == "" || groupId == "-1", "lstGroups") &&
	   !markElement(validTill == null || validTill == "", "validTill"))
	{
		UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=saveTimeTable&GroupId=" + groupId + "&ValidTill=" + validTill, saveHours, false);
	}
}

function loadTimeTable()
{	
	var groupId = document.getElementById("lstGroups").value;
	if(!markElement(groupId == null || groupId =="" || groupId =="-1", "lstGroups"))
	{
		UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadPlan&GroupId=" + groupId, generateTimeTable, false);
	}
}

function generateTimeTable(result)
{
	var data = result.split("*");
	document.getElementById("validTill").value = data[0];
	var hours = data[1].split("|");
	for(var i = 0; i < hours.length; i++)
	{
		var hour = hours[i].split(";");
		var subjectId = hour[0];
		var row = hour[2];
		var column = hour[1];
		timeTable[column][row] = subjectId;
		var html = "<table style=\"width: 100%; font-size: smaller\" cellspacing=\"0\" cellpadding=\"0\">";
		html += "<tr>";
		html += "<td align=\"center\"><b>" + hour[3] + "</b></td>";
		html += "<td align=\"right\"><img src=\"Images/delete.gif\" onclick=\"removeHour(" + column + ", " + row + ")\"/></td>";
		html += "</tr>";
		html += "<tr>";
		html += "<td align=\"center\">&nbsp;&nbsp;<b>" + hour[4] + "</b></td>";
		html += "</tr>";
		html += "<tr>";
		html += "<td align=\"center\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + hour[5] + "</td>";
		html += "</tr>";
		html += "<tr>";
		html += "</tr>";
		html += "</table>";
		document.getElementById("hour_" + column + "_" + row).innerHTML = html;
	}
}

function onLoad()
{
	if(!markElement(groupId == null || groupId == "" || groupId == "-1", "lstGroups") &&
		!markElement(validTill == null || validTill == ""), "validTill")
		{
			UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadTimeTable&GroupId=" + groupId, saveHours, false);
		}
}

function saveHours(result)
{
	if (result == "-1")
	{
		markElement(true, "validTill");
	}
	else
	{
		for (var i = 0; i < timeTable.length; i++)
		{
			for(var j = 0; j < timeTable[i].length; j++)
			{
				var subjectId = timeTable[i][j];
				if(subjectId != null && subjectId != "" && subjectId != "-1")
				{
					UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=savePlanHour&TimeTableId=" + result + "&Weekday=" + (i +1) + "&Hour=" + (j +1) + "&SubjectId=" + subjectId, null, false);
				}
			}
		}
		
		document.location.href = document.location.href;
	}
}

function loadGroups(ctrl)
{
	UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadGroups&GradeId=" + ctrl.value, setGroups, false);
}

function setGroups(result)
{
	setListItems("lstGroups", result);
}

function loadSubjects(ctrl)
{
	UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadAvailableSubjects&GroupId=" + ctrl.value, setSubjects, false);
}

function setSubjects(result)
{
	setListItems("lstSubjects", result);
}

function setListItems(listName, list)
{
	var items = list.split("|");
	var listElement = document.getElementById(listName);
	listElement.options.length = 0;
	listElement.options.add(new Option("-", "-1"));
	for(var i = 0; i < items.length; i++)
	{
		if(items[i] != "")
		{
			var item = items[i].split(';');
			listElement.options.add(new Option(item[0], item[1]));
		}
	}
}