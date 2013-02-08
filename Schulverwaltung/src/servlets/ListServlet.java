package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elements.IDatabaseObject;
import database.Error;

	public class ListServlet<T extends IDatabaseObject<T>> extends HttpServlet{

		private static final long serialVersionUID = 1L;
		private Class<T> currentClass;
		
		private ArrayList<String> filterFields;
		private Boolean needSort = false;
		private String oldSortKey;
		private ArrayList<String> orderFields;
		private String pageName;
		private String tableName;
		
		public ListServlet() {
	        super();
	    }
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			String sortKey = request.getParameter("SortKey");
			if(sortKey == null || sortKey.isEmpty())
			{
				sortKey = "0";
			}
			
			try
			{
				String needSort = request.getParameter("NeedSort");
				this.needSort = needSort != null && needSort.equals("1");
			}
			catch(Exception ex)
			{
				this.needSort = false;
			}
			
			Boolean showDisabled = request.getParameter("ShowDisabled") != null ? 
								   true : 
								   false;

			if(sortKey != null && orderFields != null)
			{		
				String orderKey = orderFields.get(Integer.parseInt(sortKey));
					
					if(orderKey.equals(this.oldSortKey) && this.needSort)
					{
						orderKey += " DESC";
					}
					this.oldSortKey = orderKey;
			}
			
			ArrayList<T> list = new ArrayList<T>();
			for(int id:ServletExtension.LoadIdList(request.getParameter("Filter"), this.oldSortKey, this.tableName, showDisabled, this.filterFields))
			{
				try
				{
					T newObject = currentClass.newInstance();
					newObject.setId(id);
					newObject.load();
					list.add(newObject);
				}
				catch(Exception ex)
				{
					Error.out(ex);
				}
			}

		    request.setAttribute("List", list);
		    request.getRequestDispatcher(this.pageName).forward(request, response);
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			this.doGet(request, response);
		}
		
		public void Setup(Class<T> currentClass, String pageName, String tableName, ArrayList<String> orderFields, ArrayList<String> filterFields)
		{
			this.currentClass = currentClass;
	        this.pageName = pageName;
	        this.tableName = tableName;
	        this.orderFields = orderFields;
	        this.filterFields = filterFields;
		}
}
