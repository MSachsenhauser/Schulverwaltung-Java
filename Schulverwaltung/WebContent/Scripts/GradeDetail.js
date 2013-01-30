var groupStudents = new Array();

function addGroup()
{
	var description = document.getElementById("txtGroupDescription").value;
	if(!markElement(description == null || description == "", "txtGroupDescription"))
	{
		var gradeId = document.getElementById("Id").value;
		UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=addGroup&GradeId=" + gradeId + "&Description=" + description, insertGroupItem, false);
	}
}

function insertGroupItem(result)
{
	document.getElementById("txtGroupDescription").value = "";
	var group = result.split(";");
	document.getElementById("lstGroups").options.add(new Option(group[1], group[0]));
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

function addStudents()
{
	var studentList = document.getElementById("lstStudents");
	var classMember = document.getElementById("lstClassMember");
	var selectedGroup = document.getElementById("lstGroups").value;
	if(!markElement(selectedGroup == null || selectedGroup == "" || selectedGroup == "-1", "lstGroups"))
	{
		for(var i = 0; i < studentList.options.length; i++)
		{
			var option = studentList.options[i];
			if(option.selected)
			{
				groupStudents.push(selectedGroup + ";" + option.value);
				classMember.options.add(option);
			}
		}
	}
}

function removeStudents()
{
	var studentList = document.getElementById("lstStudents");
	var classMember = document.getElementById("lstClassMember");
	var selectedGroup = document.getElementById("lstGroups").value;
	if(!markElement(selectedGroup == null || selectedGroup == "" || selectedGroup == "-1", "lstGroups"))
	{
		for(var i = 0; i < classMember.options.length; i++)
		{
			var option = classMember.options[i];
			if(option.selected)
			{
				for(var j = 0; j < groupStudents.length; j++)
				{
					if(groupStudents[j] == selectedGroup + ";" + option.value)
					{
						groupStudents.splice(j, 1);
					}
				}
				
				studentList.options.add(option);
			}
		}
	}
}

function onLoad()
{
	var classMember = document.getElementById("lstClassMember");
	var selectedGroup = document.getElementById("lstGroups").value;
	if(!markElement(selectedGroup == null || selectedGroup == "" || selectedGroup == "-1", "lstGroups"))
	{
		for(var i = 0; i < classMember.options.length; i++)
		{
			var option = classMember.options[i];
			groupStudents.push(selectedGroup + ";" + option.value);
		}
	}
}

function onSave()
{
	document.getElementById("groupStudents").value = groupStudents.toString();
	document.forms[0].submit();
}

function loadGroupStudents()
{
	var groupId = document.getElementById("lstGroups").value;
	UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=loadGroupStudents&GroupId=" + groupId, insertGroupStudents, false);
}

function insertGroupStudents(result)
{
	var students = result.split("|");
	var classMember = document.getElementById("lstClassMember");
	classMember.options.length = 0;
	for(var i = 0; i < students.length; i++)
	{
		var student = students[i].split(";");
		if(student[0] != null && student[1] != null)
		{
			classMember.options.add(new Option(student[0] + ": " + student[1], student[0]));
		}
	}
}