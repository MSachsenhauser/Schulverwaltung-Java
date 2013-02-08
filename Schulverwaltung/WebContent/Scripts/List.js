var detailFileName = "";
var deleteServlet = "";
var deleteText = "";
var elementName = "";
var $k = jQuery.noConflict();
var curRow = null;
var openDetails = true;
var dialogHeight = 300;
var dialogWidth = 850;
$k(document).ready(function(){
	$k("tr.DetailEntry").click(function()
			{
			if(openDetails)
				{
					var id = this.id;
					curRow = this;
					$k("#dialog").dialog({
				        autoOpen: true,
				        resizable: false,
				        title: elementName + " bearbeiten",
				        modal: false, width: dialogWidth, height: dialogHeight        });
					$k("#dialogTarget").attr("src", detailFileName + "?Id=" + id); 
				}
			});
	$k("#btnNew").click(function()
			{
				$k("#dialog").dialog({
			        autoOpen: true,
			        resizable: false,
			        title: elementName + " anlegen",
			        close: reload,
			        modal: false, width: dialogWidth, height: dialogHeight        });
				$k("#dialogTarget").attr("src",detailFileName + "?Id=-1"); 
			});
	});

function closeDialog()
{
	$k("#dialog").dialog('close');
	$k("#dialogTarget").attr("src", detailFileName + "?Id=-1");
	document.getElementById("NeedSort").value = 0;
	setTimeout("reload(null)", 500);
}

var deletableIds = new Array();
function checkDeleteEnty(ctrl)
{
	openDetails = false;
	if(ctrl.checked)
		{
			deletableIds.push(ctrl.parentNode.parentNode.id);
		}
	else
		{
			for(var i = 0; i < deletableIds.length; i++)
				{
					var id = deletableIds[i];
					if(id == ctrl.parentNode.id)
						{
							deletableIds.splice(i, i+1);
						}
				}
		}
}

function closeWithOpenEdit(id)
{
	alert("test");
	$k("#dialog").dialog({
        autoOpen: true,
        resizable: false,
        title: elementName + " bearbeiten",
        modal: false, width: dialogWidth, height: dialogHeight        });
	$k("#dialogTarget").attr("src", detailFileName + "?Id=" + id); 
}

function deleteEntries()
{
	if(window.confirm(unescape(deleteText)))
		{
			UseAjax("http://localhost:8080/Schulverwaltung/AjaxServlet?Action=" + deleteServlet + "&Ids=" + deletableIds.join(";"), reload, false);
		}
}

function reload(result)
{
	document.forms[0].submit();
	document.getElementById("NeedSort").value = 0;
}

function SetSortKey(sortIndex)
{
	document.getElementById("SortKey").value = sortIndex;
	document.getElementById("NeedSort").value = 1;
	reload(null);
}