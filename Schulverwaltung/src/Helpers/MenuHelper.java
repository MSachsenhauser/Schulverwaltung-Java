package Helpers;

import java.util.ArrayList;

public class MenuHelper {
	private static ArrayList<MenuItem> menuItems;
	static {
		menuItems = new ArrayList<MenuItem>();
		menuItems.add(new MenuItem("Start", "Main.jsp"));
		menuItems.add(new MenuItem("Administration", "Administration.jsp"));
		//menuItems.add(new MenuItem("Stundenpläne", "TimeTables.jsp"));
		menuItems.add(new MenuItem("Noten", "ExamServlet"));
		menuItems.add(new MenuItem("Schüler", "StudentServlet"));
		menuItems.add(new MenuItem("Import", "Import.jsp"));
	}
	
	public static String GenerateTopMenu(String curPage)
	{
		String result = "<div>\n";
		result += "	<table>\n";
		result += "		<tr>\n";

		for(MenuItem item:menuItems)
		{
			result += "			<td class=\"" + 
						(item.getLink().equals(curPage) ? "SelectedMenuItem" : "MenuItem") + "\">" + 
						"<a href=\"" + item.getLink() + "\">" + item.getDisplayName() + "</a></td>";
		}
		result += "		</tr>\n";
		result += "	</table>\n";
		result += "</div>\n";
		return result;
	}
}
