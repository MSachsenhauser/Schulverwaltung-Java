package Helpers;

public class CheckString 
{
	
		public static boolean testen(String income)
		{		

			
			boolean have = false;
			
			int check = income.indexOf("<script>");
			check = income.indexOf("<%");
			check = income.indexOf("$(");
			
			
			if (check >= 0)
				{
					have = true;
				}
			
			
			return have;
		}
}

