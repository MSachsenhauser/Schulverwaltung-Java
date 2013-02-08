package servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import elements.Instructor;

@WebServlet("/InstructorServlet")
public class InstructorServlet extends ListServlet<Instructor> {
	private static final long serialVersionUID = 1L;
       
    public InstructorServlet() {
        super();
        ArrayList<String> orderFields = new ArrayList<String>();
        orderFields.add("Id");
        orderFields.add("Name");
        orderFields.add("Company");
        orderFields.add("Firstname");
        ArrayList<String> filterFields = new ArrayList<String>();
        filterFields.add("Name");
        filterFields.add("Company");
        filterFields.add("Firstname");
        this.Setup(Instructor.class, "Administration/Instructors.jsp", "qryInstructor", orderFields, filterFields);
    }
}
