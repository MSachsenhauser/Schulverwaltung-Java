package helpers;

public class CheckString 
{
	public static boolean XssCheck(String income)
	{		
		boolean result = false;
		result = income.indexOf("<script>") > 0 || result;
		result = income.indexOf("<%") > 0 || result;
		result = income.indexOf("$(") > 0 || result;
		return result;
	}
}