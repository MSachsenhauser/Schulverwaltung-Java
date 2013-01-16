function generateIHKGradingKey()
{
	var maxPointControl = document.getElementById("maxPoints");
	if(numberValidation(maxPointControl))
	{
		var maxPoints = maxPointControl.value;
		var points1 = maxPoints * 0.92;
		var points2 = maxPoints * 0.81;
		var points3 = maxPoints * 0.67;
		var points4 = maxPoints * 0.5;
		var points5 = maxPoints * 0.30;
		
		// Herr Pfleiderer Fragen ob bei , immer .5 oder dann auf bzw abrunden - 
		document.getElementById("minPoints1").value = Math.round(points1 * 100) /100;
		document.getElementById("minPoints2").value = Math.round(points2 * 100) /100;
		document.getElementById("minPoints3").value = Math.round(points3 * 100) /100;
		document.getElementById("minPoints4").value = Math.round(points4 * 100) /100;
		document.getElementById("minPoints5").value = Math.round(points5 * 100) /100;
	}
}