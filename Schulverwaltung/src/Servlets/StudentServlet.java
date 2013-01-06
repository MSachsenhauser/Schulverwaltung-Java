package Servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import Elements.*;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends ListServlet<Student> {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
        ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("id");
    	orderFields.add("Firstname");
    	orderFields.add("Name");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("Firstname");
    	filterFields.add("Name");
        this.Setup(Student.class, "Students.jsp", "student", orderFields, filterFields);
    }
}
