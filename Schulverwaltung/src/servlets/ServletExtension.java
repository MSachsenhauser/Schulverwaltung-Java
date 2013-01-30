package servlets;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.Database;


public class ServletExtension {
	public static ArrayList<Integer> LoadIdList(String filter, String sortKey, String tableName, Boolean showDisabled, ArrayList<String> filterFields)
	{
		ArrayList<Integer> ids = new ArrayList<Integer>();
		String sql = "SELECT id FROM " + tableName;
		sql += " WHERE DisableFlag = " + (!showDisabled ? "0" : "0 OR DisableFlag = 1");
		ArrayList<Object> params = new ArrayList<Object>();
		if(filter != null)
		{		
			String sqlFilter = "";
			if(!filter.isEmpty() && filterFields != null && filterFields.size() > 0)
			{
				sqlFilter += " AND( ";
				for(int i = 0; i < filterFields.size(); i++)
				{
					sqlFilter += (i > 0 ? " OR " : "") +  filterFields.get(i) + " like ?";
					params.add(filter + "%");
				}
				sqlFilter += ")";
				sql += sqlFilter;
			}
		}
		if(!sortKey.isEmpty())
		{
			sql += " Order By " + sortKey;
		}
		
		try (Database db = new Database())
		{
			ResultSet result;
			if(params.size() > 0)
			{
				result = db.getDataRows(sql, params);
			}
			else
			{
				result = db.getDataRows(sql);
			}
			
			while(result.next())
			{
				ids.add(result.getInt(1));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return ids;
	}
}
