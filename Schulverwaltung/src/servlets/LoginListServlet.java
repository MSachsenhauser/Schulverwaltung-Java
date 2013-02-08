package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import database.Login;


@WebServlet("/LoginListServlet")
public class LoginListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private Boolean needSort = false;

    private String oldSortKey = "";
    public LoginListServlet() {
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

		ArrayList<String> orderFields = new ArrayList<String>();
		orderFields.add("Login");
		orderFields.add("Email");
		if(sortKey != null && orderFields != null)
		{		
			String orderKey = orderFields.get(Integer.parseInt(sortKey));
				
				if(orderKey.equals(this.oldSortKey) && this.needSort)
				{
					orderKey += " DESC";
				}
				this.oldSortKey = orderKey;
		}
		try(Database db = new Database())
		{
			ArrayList<Login> list = new ArrayList<Login>();
			String filter = request.getParameter("Filter") ;
			filter = filter != null ? filter : "";
			filter = "%" + filter + "%";
			ResultSet result = db.getDataRows("SELECT * FROM login WHERE Login like ? OR Email like ?", filter, filter);	
			while(result.next())
			{
				list.add(new Login().setUserName(result.getString("Login")).setEmail(result.getString("Email")).setPassword(result.getString("Password")));
			}
			request.setAttribute("List", list);
		}
		catch(Exception ex)
		{
			database.Error.out(ex);
		}
		
		request.getRequestDispatcher("Administration/Logins.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
