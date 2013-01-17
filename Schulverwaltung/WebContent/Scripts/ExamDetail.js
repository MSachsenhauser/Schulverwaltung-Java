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
			document.getElementById("points2").value = points1 -1 ;
			document.getElementById("points3").value = points2 -1 ;
			document.getElementById("points4").value = points3 -1 ;
			document.getElementById("points5").value = points4 -1 ;
			document.getElementById("points6").value = points5 -1 ;
			
			document.getElementById("minPoints1").value = points1;
			document.getElementById("minPoints2").value = points2;
			document.getElementById("minPoints3").value = points3;
			document.getElementById("minPoints4").value = points4;
			document.getElementById("minPoints5").value = points5;
		}
	}
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