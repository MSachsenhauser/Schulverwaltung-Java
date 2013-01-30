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

import database.Database;
import database.Error;

import elements.Grade;
import elements.Student;


/**
 * Servlet implementation class GradeDetailServlet
 */
@WebServlet("/GradeDetailServlet")
public class GradeDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradeDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    private HttpServletRequest request;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		int gradeId = Integer.parseInt(request.getParameter("Id"));
		Grade curGrade = new Grade().setId(gradeId);
		try (Database db = new Database()) {

			curGrade.setDescription(this.getParamValue("description"))
			.setRoomId(Integer.parseInt(this.getParamValue("room")))
			.setTeacherId(Integer.parseInt(this.getParamValue("teacher")))
			.setDisableflag(Integer.parseInt(this.getParamValue("DisableFlag")));
			if(curGrade.getId() == -1)
			{
				curGrade.addToDb();
			}
			else
			{
				curGrade.save();
			}
			String[] studentGroup = this.getParamValue("groupStudents").split(",");
			if(studentGroup.length > 0)
			{
				String groupId = studentGroup[0].split(";")[0];
				db.NoQuery("DELETE FROM student2group WHERE GroupId=?", groupId);
			}
			
			for(int i = 0; i < studentGroup.length; i++)
			{
				String[] ids = studentGroup[i].split(";");
				if(ids != null && ids.length > 0)
				{
					db.NoQuery("INSERT INTO student2group(groupid, studentid) values (?, ?)", ids[0], ids[1]);
				}
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Error.out(ex);
		}
		
		RequestDispatcher view = request.getRequestDispatcher("Administration/GradeDetail.jsp?Id=" + curGrade.getId());
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
