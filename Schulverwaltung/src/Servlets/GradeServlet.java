package Servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import Elements.Grade;

@WebServlet("/GradeServlet")
public class GradeServlet extends ListServlet<Grade> {
	private static final long serialVersionUID = 1L;
       
    public GradeServlet() {
        super();
        ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("id");
    	orderFields.add("description");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("description");
        this.Setup(Grade.class, "Administration/Grades.jsp", "grade", orderFields, filterFields);
    }
}
