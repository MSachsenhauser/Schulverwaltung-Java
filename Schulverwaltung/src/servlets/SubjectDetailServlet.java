package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elements.Subject;


@WebServlet("/SubjectDetailServlet")
public class SubjectDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectDetailServlet() {
        super();
    }

    private HttpServletRequest request;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		int Id = Integer.parseInt(request.getParameter("Id"));
		Subject curSubject = new Subject().setId(Id);
		curSubject.setDescription(this.getParamValue("description"))
		.setShortName(this.getParamValue("shortName"))
		.setDisableflag(Integer.parseInt(this.getParamValue("DisableFlag")));

		if(curSubject.getId() == -1)
		{
			curSubject.addToDb();
		}
		else
		{
			curSubject.save();
		}
		RequestDispatcher view = request.getRequestDispatcher("Administration/SubjectDetail.jsp?Id=" + curSubject.getId());
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
		this.doGet(request, response);
	}
}