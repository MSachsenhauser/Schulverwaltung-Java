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

import database.Error;
import elements.Company;
import elements.Exam;
import elements.Mark;

/**
 * Servlet implementation class CompanyDetailServlet
 */
@WebServlet("/CompanyDetailServlet")
public class CompanyDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private HttpServletRequest request;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyDetailServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.request = request;
		int id = Integer.parseInt(request.getParameter("Id"));
		Company curCompany = new Company().setId(id);
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		try {
			curCompany.setDisableflag(Integer.parseInt(this.getParamValue("DisableFlag")));
			curCompany.setName(this.getParamValue("Name"));
			curCompany.setPhone(this.getParamValue("Phone"));
			curCompany.setCity(this.getParamValue("City"));
			curCompany.setStreet(this.getParamValue("Street"));
			curCompany.setPlz(this.getParamValue("Plz"));
			if(curCompany.getId() == -1)
			{
				curCompany.addToDb();
			}
			else
			{
				curCompany.save();
			}
			RequestDispatcher view = request.getRequestDispatcher("CompanyDetail.jsp?Id=" + curCompany.getId());
			view.forward(request, response);
		} catch (Exception e) {
			Error.out(e);
		}
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
