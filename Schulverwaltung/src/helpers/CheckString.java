package helpers;

public class CheckString 
{
	public static String replaceXss(String text)
	{
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&#39;").replace("\"", "&quot;");
	}
	
	public static boolean xssCheck(String income)
	{		
		boolean result = false;
		result = income.indexOf("<script>") > 0 || result;
		result = income.indexOf("<%") > 0 || result;
		result = income.indexOf("$(") > 0 || result;
		return result;
	}
}