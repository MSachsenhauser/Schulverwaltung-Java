package servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import elements.Religion;

/**
 * Servlet implementation class ReligionServlet
 */
@WebServlet("/ReligionServlet")
public class ReligionServlet extends ListServlet<Religion> {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReligionServlet() {
        super();
        ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("id");
    	orderFields.add("description");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("description");
        this.Setup(Religion.class, "Administration/Religions.jsp", "religion", orderFields, filterFields);
    }
}
