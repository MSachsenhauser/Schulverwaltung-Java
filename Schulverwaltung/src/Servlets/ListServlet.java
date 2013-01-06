package Servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Elements.IDatabaseObject;

	public class ListServlet<T extends IDatabaseObject<T>> extends HttpServlet{

		private static final long serialVersionUID = 1L;
		private String oldSortKey;
		
		private String pageName;
		private String tableName;
		private ArrayList<String> orderFields;
		private ArrayList<String> filterFields;
		private Class<T> currentClass;
		public ListServlet() {
	        super();
	    }
		
		public void Setup(Class<T> currentClass, String pageName, String tableName, ArrayList<String> orderFields, ArrayList<String> filterFields)
		{
			this.currentClass = currentClass;
	        this.pageName = pageName;
	        this.tableName = tableName;
	        this.orderFields = orderFields;
	        this.filterFields = filterFields;
		}
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String sortKey = request.getParameter("SortKey");
			if(sortKey == null || sortKey.isEmpty())
			{
				sortKey = "0";
			}
			
			Boolean showDisabled = request.getParameter("ShowDisabled") != null ? 
								   true : 
								   false;

			if(sortKey != null && orderFields != null )
			{		
				String orderKey = orderFields.get(Integer.parseInt(sortKey));
					
					if(orderKey.equals(this.oldSortKey))
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
					ex.printStackTrace();
				}
			}

		    request.setAttribute("List", list);
		    
		    request.getRequestDispatcher(this.pageName).forward(request, response);
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			this.doGet(request, response);
		}
}
