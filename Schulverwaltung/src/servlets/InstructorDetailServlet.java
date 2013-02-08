package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Database;
import database.Error;
import elements.Grade;
import elements.Instructor;


@WebServlet("/InstructorDetailServlet")
public class InstructorDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    private HttpServletRequest request;
    public InstructorDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
    	this.request = request;
		int id = Integer.parseInt(request.getParameter("Id"));
		Instructor curInstructor = new Instructor().setId(id);
		try (Database db = new Database()) {

			curInstructor.setDisableflag(Integer.parseInt(this.getParamValue("DisableFlag")));
			curInstructor.setName(this.getParamValue("Name"));
			curInstructor.setFirstname(this.getParamValue("Firstname"));
			curInstructor.setPhone(this.getParamValue("Phone"));
			curInstructor.setEmail(this.getParamValue("Email"));
			curInstructor.setCompanyId(Integer.parseInt(this.getParamValue("CompanyId")));
			
			if(curInstructor.getId() == -1)
			{
				curInstructor.addToDb();
			}
			else
			{
				curInstructor.save();
			}
		} catch (Exception ex) {
			Error.out(ex);
		}
		
		RequestDispatcher view = request.getRequestDispatcher("Administration/InstructorDetail.jsp?Id=" + curInstructor.getId());
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	private String getParamValue(String name)
	{
		try
		{
			Object retVal = this.request.getParameter(name);
			return retVal.toString();
		}
		catch(Exception ex)
		{
			return "";
		}
	}
}
