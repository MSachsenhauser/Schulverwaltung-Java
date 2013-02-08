package servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import elements.Exam;


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
    	orderFields.add("Description");
    	orderFields.add("Subject");
    	orderFields.add("Teacher");
    	orderFields.add("type");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("Description");
    	filterFields.add("Subject");
    	filterFields.add("Teacher");
    	filterFields.add("type");
        this.Setup(Exam.class, "Exams.jsp", "qryExam", orderFields, filterFields);
    }
}
