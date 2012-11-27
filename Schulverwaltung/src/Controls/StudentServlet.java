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
		String filter = request.getAttribute("Filter").toString();
		String sortKey = request.getAttribute("SortKey").toString();
		String sql = "SELECT * FROM Schueler ";
		
		// Dann jetzt noch auf Parameter umstellen dann passts
		if(!filter.isEmpty())
		{
			sql += " WHERE ";
			// Hier dann die Ganzen dursuchenden Felder dazuschreiben
		}
		
		if(!sortKey.isEmpty())
		{
			sql += " Order By " + sortKey;
		}
		
		LinkedList<Student> students = new LinkedList<Student>();
	    Database db = new Database();
	    ResultSet result = db.getDataRows(sql);
	    try {
			while(result.next())
			{
				Student student = new Student();
				student.setId(result.getInt("Id"));
				// Hier nur die Daten laden die wir auch anzeigen wegen der Ladezeit!!
				// Bei dem Detailfenster werden sowieso alle Daten nochmal geladen
				
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
