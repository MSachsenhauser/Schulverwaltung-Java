var req;
var resFunction = null;
var useResponseXML = false;
function UseAjax(url, resultFunction, useXML)
{
	req = new XMLHttpRequest();
	req.open("Get", url, true);
	useResponseXML = useXML;
	resFunction = resultFunction;
	if(resFunction != null)
		{
			req.onreadystatechange = callback;
		}
	req.send(null);
}

function callback()
{
	if(req.readyState == 4)
		{
			if(req.status == 200)
				{
					if(useResponseXML)
						{
							setTimeout("resFunction('" + req.responseXML + "')", 1);
						}
					else
						{
							setTimeout("resFunction('" + req.responseText + "')", 1);	
						}
				}
		}
}