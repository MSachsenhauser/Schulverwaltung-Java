package servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import elements.Subject;


@WebServlet("/SubjectServlet")
public class SubjectServlet extends ListServlet<Subject> {
	private static final long serialVersionUID = 1L;

    public SubjectServlet() {
        super();
        
        ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("Id");
    	orderFields.add("Description");
    	orderFields.add("Short");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("Description");
    	filterFields.add("Short");
        this.Setup(Subject.class, "Administration/Subjects.jsp", "subject", orderFields, filterFields);
    }
}
