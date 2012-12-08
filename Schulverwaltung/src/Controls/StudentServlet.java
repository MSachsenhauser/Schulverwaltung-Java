package Controls;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.Database;
import Elements.*;
/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String oldSortKey = "nachname";
    private static String oldFilter = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filter = request.getParameter("Filter");
		String sortKey = request.getParameter("SortKey");
		Boolean showDisabled = request.getParameter("ShowDisabled") != null ? 
							   true : 
							   false;
		String sql = "SELECT * FROM student ";
		sql += " WHERE DisableFlag = " + (!showDisabled ? "0" : "0 OR DisableFlag = 1");
		// Dann jetzt noch auf Parameter umstellen dann passts
		if(filter != null)
		{
			if(!filter.isEmpty())
			{
				sql += "AND( ";
				sql += "Firstname like '" + filter + "%'";
				sql += "OR Name like '" + filter + "%' ";
				sql += ")";
				// Hier dann die Ganzen dursuchenden Felder dazuschreiben
			}
		}
		if(sortKey != null)
		{
			if(!sortKey.isEmpty())
			{
				String orderKey = "";
				switch(sortKey)
				{
					case "0": orderKey = "id"; break; 
					case "1": orderKey = "firstname"; break;
					case "2": orderKey = "name"; break;
					default: orderKey = "name";
				}
				if(orderKey.equals(StudentServlet.oldSortKey))
					{
						orderKey += " DESC";
					}
				StudentServlet.oldSortKey = orderKey;
				
				sql += " Order By " + orderKey;
			}
		}
		System.out.println(sql);
		LinkedList<Student> students = new LinkedList<Student>();
	    Database db = new Database();
	    ResultSet result = db.getDataRows(sql);
	    try {
			while(result.next())
			{
				Student student = new Student();
				student.setId(result.getInt("Id"));
				student.load();
				students.add(student);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    db.closeConnection();
	    request.setAttribute("Students", students);
	    
	    request.getRequestDispatcher("Students.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
