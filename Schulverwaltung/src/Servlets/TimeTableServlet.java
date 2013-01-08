package Servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import Elements.Timetable;

/**
 * Servlet implementation class TimeTableServlet
 */
@WebServlet("/TimeTableServlet")
public class TimeTableServlet extends ListServlet<Timetable> {
	private static final long serialVersionUID = 1L;

    public TimeTableServlet() {
        super();
        ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("id");
    	orderFields.add("validTill");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("validTill");
        this.Setup(Timetable.class, "Administration/TimeTables.jsp", "timetable", orderFields, filterFields);
    }
}
