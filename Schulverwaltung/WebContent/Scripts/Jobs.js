var $k = jQuery.noConflict();
var curRow = null;
var openDetails = true;
$k(document).ready(function(){
	$k("tr.DetailEntry").click(function()
			{
			if(openDetails)
				{
					var studentId = this.id;
					curRow = this;
					$k("#dialog").dialog({
				        autoOpen: true,
				        title: "Job bearbeiten",
				        modal: false, width: 850, height: 300        });
					$k("#dialogTarget").attr("src","JobDetail.jsp?Id=" + studentId); 
				}
			});
	$k("#btnNew").click(function()
			{
				$k("#dialog").dialog({
			        autoOpen: true,
			        title: "Job anlegen",
			        modal: false, width: 850, height: 300        });
				$k("#dialogTarget").attr("src","JobDetail.jsp?Id=-1"); 
			});
	});

function closeDialog()
{
	$k("#dialog").dialog('close');
	$k("#dialogTarget").attr("src","JobDetail.jsp?Id=-1"); 
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

function deleteStudents()
{
	if(window.confirm(unescape("Sollen die ausgew%E4hlten Berufe wirklich gel%F6scht werden?")))
		{
			UseAjax("http://localhost:8080/Schulverwaltung/DeleteJobsServlet?Ids=" + deletableIds.join(";"), reload, false);
		}
}

function reload(result)
{
	document.forms[0].submit();
}

function SetSortKey(sortIndex)
{
	document.getElementById("SortKey").value = sortIndex;
	reload(null);
}