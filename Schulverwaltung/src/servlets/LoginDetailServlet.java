package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.*;
import database.Error;
import elements.Instructor;
@WebServlet("/LoginDetailServlet")
public class LoginDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginDetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("Username");
		userName = userName != null ? userName : "";
		Login curLogin = new Login().setUserName(userName);
		
		try (Database db = new Database())
		{
			curLogin.setEmail(request.getParameter("Email"));
			curLogin.setPassword(request.getParameter("Password"));
			int count = db.getInt("SELECT COUNT(*) FROM login WHERE Login =?", userName);
			if(count == 0)
			{
				curLogin.addToDb();
			}
			else
			{
				curLogin.save();
			}
		}
		catch(Exception ex)
		{
			Error.out(ex);
		}
		
		RequestDispatcher view = request.getRequestDispatcher("Administration/LoginDetail.jsp?Id=" + curLogin.getUserName());
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
