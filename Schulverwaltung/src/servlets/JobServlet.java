package servlets;

import java.util.*;

import javax.servlet.annotation.WebServlet;

import elements.Job;


@WebServlet("/JobServlet")
public class JobServlet extends ListServlet<Job> {
	private static final long serialVersionUID = 1L;

    public JobServlet() {
    	super();
    	ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("id");
    	orderFields.add("description");
    	orderFields.add("duration");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("description");
    	filterFields.add("duration");
        this.Setup(Job.class, "Administration/Jobs.jsp", "job", orderFields, filterFields);
    }
}
