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

import elements.Exam;
import elements.Mark;
import elements.Student;
import database.Error;;

@WebServlet("/ExamDetailServlet")
public class ExamDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public ExamDetailServlet() {
        super();
    }

    private HttpServletRequest request;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.request = request;
		int id = Integer.parseInt(request.getParameter("Id"));
		Exam curExam = new Exam().setId(id);
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		try {
			curExam.setDisableflag(Integer.parseInt(this.getParamValue("DisableFlag")));
			curExam.setTypeId(Integer.parseInt(this.getParamValue("Type")));
			curExam.setGroupSubjectId(Integer.parseInt(this.getParamValue("Subject")));
			curExam.setAnnounceDate(format.parse(this.getParamValue("AnnounceDate")));
			curExam.setExecutionDate(format.parse(this.getParamValue("ExecutionDate")));
			curExam.setMaxPoints(Double.parseDouble(this.getParamValue("maxPoints")));
			curExam.setMinPoints1(Double.parseDouble(this.getParamValue("minPoints1")));
			curExam.setMinPoints2(Double.parseDouble(this.getParamValue("minPoints2")));
			curExam.setMinPoints3(Double.parseDouble(this.getParamValue("minPoints3")));
			curExam.setMinPoints4(Double.parseDouble(this.getParamValue("minPoints4")));
			curExam.setMinPoints5(Double.parseDouble(this.getParamValue("minPoints5")));
			
			String[] marks = this.getParamValue("Marks").split(";");
			for(String element:marks)
			{
				Mark mark = new Mark();
				try
				{
					String[] splittedMark = element.split(",");
					mark.setDisableflag(0);
					String markId = "";
					String studentId = "";
					if(splittedMark[0].contains("-"))
					{
						String[] split = splittedMark[0].split("-");
						markId = split[0];
						studentId = split[1];
					}
					else
					{
						markId = "-1";
						studentId = splittedMark[0];
					}
					
					if(studentId != "")
					{
						mark.setId(Integer.parseInt(markId));
						mark.setStudentId(Integer.parseInt(studentId));
						mark.setPoints(Double.parseDouble(splittedMark[1]));
						mark.setMark(Integer.parseInt(splittedMark[2]));
						mark.setTrend(splittedMark[3]);
						curExam.getMarks().add(mark);
					}
				}
				catch (Exception ex)
				{
					Error.out(ex);
				}
			}
			
			if(curExam.getId() == -1)
			{
				curExam.addToDb();
			}
			else
			{
				curExam.save();
			}
			RequestDispatcher view = request.getRequestDispatcher("ExamDetail.jsp?Id=" + curExam.getId());
			view.forward(request, response);
		} catch (ParseException e) {
			Error.out(e);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
