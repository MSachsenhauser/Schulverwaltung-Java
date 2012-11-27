var $k = jQuery.noConflict();
var curRow = null;
$k(document).ready(function(){
	$k("tr.DetailEntry").click(function()
			{
			var studentId = this.id;
			curRow = this;
			$k("#dialog").dialog({
		        autoOpen: true,
		        title: "Sch&uuml;ler bearbeiten",
		        modal: false, width: 800, height: 500        });
			$k("#dialogTarget").attr("src","StudentDetail.jsp?StudentId=" + studentId); 
			});
	$k("#btnNew").click(function()
			{
				$k("#dialog").dialog({
			        autoOpen: true,
			        title: "Sch&uuml;ler anlegen",
			        modal: false, width: 800, height: 500        });
				$k("#dialogTarget").attr("src","StudentDetail.jsp?StudentId=-1"); 
			});
	});

function closeDialog()
{
	$k("#dialog").dialog('close');
	$k("#dialogTarget").attr("src",""); 
}