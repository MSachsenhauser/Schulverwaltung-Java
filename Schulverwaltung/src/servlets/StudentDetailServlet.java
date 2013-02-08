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

import elements.Guardian;
import elements.Student;


@WebServlet("/StudentDetailServlet")
public class StudentDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private HttpServletRequest request;
    public StudentDetailServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.request = request;
		int studentId = Integer.parseInt(request.getParameter("Id"));
		Student curStudent = new Student().setId(studentId);
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		try (Database db = new Database()) {
			curStudent.setName(this.getParamValue("lastname"))
			.setFirstname(getParamValue("firstname"))
			.setEmail(getParamValue("email"))
			.setStreet(getParamValue("street"))
			.setPlz(getParamValue("plz"))
			.setCity(getParamValue("city"))
			.setInstructorId(Integer.parseInt(getParamValue("Instructor")))
			.setJobId(Integer.parseInt(getParamValue("Job")))
			.setTypificationId(Integer.parseInt(getParamValue("TypificationId")))
			.setShortened(getParamValue("shortened").equals("on"))
			.setIsGermanFree(getParamValue("germanFree").equals("on"))
			.setIsReligionFree(getParamValue("religionFree").equals("on"))
			.setIsSportFree(getParamValue("sportFree").equals("on"))
			.setBirthday(format.parse(getParamValue("birthday")))
			.setEntry(format.parse(getParamValue("entry")))
			.setReligionId(Integer.parseInt(getParamValue("religion")))
			.setGuardianId1(Integer.parseInt(getParamValue("GuardianId1")))
			.setGuardianId2(Integer.parseInt(getParamValue("GuardianId2")))
			.setDisableflag(Integer.parseInt(this.getParamValue("DisableFlag")));
			if(curStudent.getId() == -1)
			{
				curStudent.addToDb();
			}
			else
			{
				curStudent.save();
			}
			
			curStudent.getGuardian1().setFirstname(getParamValue("guardianFirstName1"));
			curStudent.getGuardian1().setName(getParamValue("guardianName1"));
			curStudent.getGuardian1().setPhone(getParamValue("guardianPhone1"));
			curStudent.getGuardian1().setCity(getParamValue("guardianCity1"));
			curStudent.getGuardian1().setPlz(getParamValue("guardianPLZ1"));
			curStudent.getGuardian1().setStreet(getParamValue("guardianStreet1"));
			if(curStudent.getGuardianId1() == -1)
			{
				if(!curStudent.getGuardian1().getName().isEmpty())
				{
					curStudent.getGuardian1().addToDb();
					db.NoQuery("INSERT INTO student2guardian (studentId, guardianId) VALUES (?, ?)", curStudent.getId(), curStudent.getGuardian1().getId());
				}
			}
			else
			{
				curStudent.getGuardian1().save();
			}
			
			curStudent.getGuardian2().setFirstname(getParamValue("guardianFirstName2"));
			curStudent.getGuardian2().setName(getParamValue("guardianName2"));
			curStudent.getGuardian2().setPhone(getParamValue("guardianPhone2"));
			curStudent.getGuardian2().setCity(getParamValue("guardianCity2"));
			curStudent.getGuardian2().setPlz(getParamValue("guardianPLZ2"));
			curStudent.getGuardian2().setStreet(getParamValue("guardianStreet2"));
			
			if(curStudent.getGuardianId2() == -1)
			{
				if(!curStudent.getGuardian1().getName().isEmpty())
				{
					curStudent.getGuardian2().addToDb();
					db.NoQuery("INSERT INTO student2guardian (studentId, guardianId) VALUES (?, ?)", curStudent.getId(), curStudent.getGuardian2().getId());
				}
			}
			else
			{
				curStudent.getGuardian2().save();
			}
			
		} catch (Exception ex) {
			Error.out(ex);
		}
		
		RequestDispatcher view = request.getRequestDispatcher("StudentDetail.jsp?Id=" + curStudent.getId());
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
