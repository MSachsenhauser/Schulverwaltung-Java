package Servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import Elements.Teacher;

@WebServlet("/TeacherServlet")
public class TeacherServlet extends ListServlet<Teacher> {
	private static final long serialVersionUID = 1L;
       
    public TeacherServlet() {
        super();
        ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("id");
    	orderFields.add("name");
    	orderFields.add("firstname");
    	orderFields.add("phone");
    	orderFields.add("email");
    	orderFields.add("birthday");
    	orderFields.add("workhours");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("name");
    	filterFields.add("firstname");
    	filterFields.add("phone");
    	filterFields.add("email");
    	filterFields.add("birthday");
    	filterFields.add("workhours");
   	
        this.Setup(Teacher.class, "Administration/Teachers.jsp", "teacher", orderFields, filterFields);
    }
}
