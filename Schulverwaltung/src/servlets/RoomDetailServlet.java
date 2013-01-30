package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import elements.Room;
import elements.Student;


@WebServlet("/RoomDetailServlet")
public class RoomDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    private HttpServletRequest request;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.request = request;
		int id = Integer.parseInt(request.getParameter("Id"));
		Room curRoom = new Room().setId(id);
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		
		curRoom.setNumber(this.getParamValue("Number"))
		.setDescription(getParamValue("Description"))
		.setDisableflag(Integer.parseInt(getParamValue("DisableFlag")));
		if(curRoom.getId() == -1)
		{
			curRoom.addToDb();
		}
		else
		{
			curRoom.save();
		}
		RequestDispatcher view = request.getRequestDispatcher("Administration/RoomDetail.jsp?Id=" + curRoom.getId());
		view.forward(request, response);
	}

	private String getParamValue(String name)
	{
		try
		{
			Object retVal = this.request.getParameter(name);
			return retVal.toString();
		}
		catch(Exception ex)
		{
			return "";
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
}
