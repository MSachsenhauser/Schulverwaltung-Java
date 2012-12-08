package Controls;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Elements.Student;

/**
 * Servlet implementation class StudentDetailServlet
 */
@WebServlet("/StudentDetailServlet")
public class StudentDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    private HttpServletRequest request;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		int studentId = Integer.parseInt(request.getParameter("StudentId"));
		Student curStudent = new Student().setId(studentId);
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		try {
			curStudent.setName(this.getParamValue("lastname"))
			.setFirstname(getParamValue("firstname"))
			.setEmail(getParamValue("email"))
			.setStreet(getParamValue("street"))
			.setPlz(getParamValue("plz"))
			.setCity(getParamValue("city"))
			.setShortened(Boolean.parseBoolean(getParamValue("shortened")))
			.setBirthday(format.parse(getParamValue("birthday")))
			.setEntry(format.parse(getParamValue("entry")));
			if(curStudent.getId() == -1)
			{
				curStudent.addToDb();
			}
			else
			{
				curStudent.save();
			}
			RequestDispatcher view = request.getRequestDispatcher("StudentDetail.jsp?StudentId=" + studentId);
			view.forward(request, response);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
