var submitButton = "btnSubmit";
var oldDisabled = false;
function numberValidation(ctrl)
{
	var result = true;
	if(ctrl.value != "")
	{
			var number = ctrl.value;
				result = !isNaN(number);
	}
	else
	{
		result = true;
	}
	
	markControl(ctrl, !result);
	enableSubmitButton(result);
	return result;
}

function dateValidation(ctrl)
{
	if(ctrl.value != "")
	{
		try
		{
			var date = ctrl.value.split(".");
			if(date != null && date.length == 3)
			{
				if(!isNaN(date[0]) && !isNaN(date[1]) && !isNaN(date[2]))
				{
					date = new Date(date[2], (date[1] -1), date[0]);
					markControl(ctrl, false);
					enableSubmitButton(true);
				}
				else
				{
					markControl(ctrl, true);
					enableSubmitButton(false);
				}
			}
			else
			{
				markControl(ctrl, true);
				enableSubmitButton(false);
			}
		}
		catch(err)
		{
			markControl(ctrl, true);
			enableSubmitButton(false);
		}
	}
	else
	{
		markControl(ctrl, false);
		enableSubmitButton(true);
	}
}

function markControl(ctrl, needMark)
{
	if (needMark)
	{
		ctrl.style.borderColor = "Red";
		ctrl.style.borderWidth = "1";
		ctrl.style.borderStyle = "solid";
	}
	else
	{
		ctrl.style.borderColor = "";
		ctrl.style.borderWidth = "";
		ctrl.style.borderStyle = "";
	}
}

function enableSubmitButton(enable)
{
	if(!enable)
	{
		document.getElementById(submitButton).disabled = "disabled";
	}
	else
	{
		if(oldDisabled && enable)
		{
			document.getElementById(submitButton).disabled = "";
		}
	}
	oldDisabled = enable;
}