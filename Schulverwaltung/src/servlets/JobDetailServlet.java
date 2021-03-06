package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elements.Job;
import elements.Student;


/**
 * Servlet implementation class JobDetailServlet
 */
@WebServlet("/JobDetailServlet")
public class JobDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private HttpServletRequest request;
    
    public JobDetailServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.request = request;
		int id = Integer.parseInt(request.getParameter("Id"));
		Job curJob = new Job().setId(id);
		curJob.setDescription(this.getParamValue("Description"))
			  .setDuration(Double.parseDouble(this.getParamValue("Duration")))
			  .setDisableflag(Integer.parseInt(this.getParamValue("DisableFlag")));
		if(curJob.getId() == -1)
		{
			curJob.addToDb();
		}
		else
		{
			curJob.save();
		}
		RequestDispatcher view = request.getRequestDispatcher("Administration/JobDetail.jsp?Id=" + curJob.getId());
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
