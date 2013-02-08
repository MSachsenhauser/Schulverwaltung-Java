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

import elements.Student;
import elements.Teacher;


@WebServlet("/TeacherDetailServlet")
public class TeacherDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private HttpServletRequest request;
    public TeacherDetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.request = request;
		int Id = Integer.parseInt(request.getParameter("Id"));
		Teacher curTeacher = new Teacher().setId(Id);
		String birthDay = getParamValue("birthday");
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		try {
			curTeacher.setName(this.getParamValue("name"))
			.setFirstname(getParamValue("firstname"))
			.setPhone(getParamValue("phone"))
			.setEmail(getParamValue("email"))
			.setShortName(getParamValue("shortName"))
			.setRoomId(Integer.parseInt(getParamValue("roomId")))
			.setWorkhours(Double.parseDouble(getParamValue("workhours")))
			.setDisableflag(Integer.parseInt(getParamValue("DisableFlag")));
			if(birthDay != null && !birthDay.isEmpty())
			{
				curTeacher.setBirthday(format.parse(birthDay));
			}
			if(curTeacher.getId() == -1)
			{
				curTeacher.addToDb();
			}
			else
			{
				curTeacher.save();
			}	
		} catch (ParseException e) {
			Error.out(e);
		}
		RequestDispatcher view = request.getRequestDispatcher("Administration/TeacherDetail.jsp?Id=" + curTeacher.getId());
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
