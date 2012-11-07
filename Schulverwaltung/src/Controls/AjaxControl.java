package Controls;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AjaxControl
 */
@WebServlet("/AjaxControl")
public class AjaxControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxControl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");
		if(key != null)
		{
			int keyInt = (int) key.charAt(0);
			String decimalString = Integer.toString(keyInt);
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-chache");
			response.getWriter().write(decimalString);
		}
		else
		{
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-chache");
			response.getWriter().write("?");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
