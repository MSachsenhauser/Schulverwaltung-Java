var req;
var resFunction = null;
function UseAjax(url, resultFunction)
{
	req = new XMLHttpRequest();
	req.open("Get", url, true);
	resFunction = resultFunction;
	req.onreadystatechange = callback;
	req.send(null);
}

function callback()
{
	if(req.readyState == 4)
		{
			if(req.status == 200)
				{
					 setTimeout("resFunction(" + req.responseText + ")", 1);
				}
		}
}