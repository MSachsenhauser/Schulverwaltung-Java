package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elements.Exam;
import elements.Job;


/**
 * Servlet implementation class ExamServlet
 */
@WebServlet("/ExamServlet")
public class ExamServlet extends ListServlet<Exam> {
	private static final long serialVersionUID = 1L;
       
    public ExamServlet() {
        super();
        ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("id");
    	orderFields.add("Subject");
    	orderFields.add("Teacher");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("Subject");
    	filterFields.add("Teacher");
        this.Setup(Exam.class, "Exams.jsp", "qryExam", orderFields, filterFields);
    }
}
