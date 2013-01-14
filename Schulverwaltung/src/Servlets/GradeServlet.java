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
    	orderFields.add("Id");
    	orderFields.add("Description");
    	orderFields.add("Teacher");
    	orderFields.add("Room");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("Description");
    	filterFields.add("Teacher");
    	filterFields.add("Room");
        this.Setup(Grade.class, "Administration/Grades.jsp", "qryGrade", orderFields, filterFields);
    }
}
