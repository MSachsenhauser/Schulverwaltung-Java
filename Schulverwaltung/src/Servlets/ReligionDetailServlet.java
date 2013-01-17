package Servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Elements.Religion;
import Elements.Student;

@WebServlet("/ReligionDetailServlet")
public class ReligionDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReligionDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    private HttpServletRequest request;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		int Id = Integer.parseInt(request.getParameter("Id"));
		Religion curReligion = new Religion().setId(Id);
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		curReligion.setDescription(this.getParamValue("Description"))
		.setSubjectId(Integer.parseInt(getParamValue("SubjectId")))
		.setDisableflag(Integer.parseInt(getParamValue("Disableflag")));

		if(curReligion.getId() == -1)
		{
			curReligion.addToDb();
		}
		else
		{
			curReligion.save();
		}
		RequestDispatcher view = request.getRequestDispatcher("ReligionDetail.jsp?Id=" + curReligion.getId());
		view.forward(request, response);
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
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
}
