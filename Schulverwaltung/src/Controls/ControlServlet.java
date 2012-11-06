package Controls;
import model.ZinsberechnungsBean;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControlServlet
 */
@WebServlet("/ControlServlet")
public class ControlServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//out.write("<html><body>");
		try
		{
			double anfangsKapital = Double.parseDouble(request.getParameter("Kapital"));
			double zinsSatz = Double.parseDouble(request.getParameter("Zinsen"));
			double laufzeit = Double.parseDouble(request.getParameter("Jahre"));
			double endbetrag = 0;
			//out.write("Anfangsbetrag: " + anfangsKapital + " € <br/>");
			//out.write("Zinssatz: " + zinsSatz + " % <br/>"); 
			//out.write("Laufzeit: " + laufzeit + " Jahre <br/>");
			out.write("vorm endbetrag");
			//out.write("---------------------------------------<br/>");
			ZinsberechnungsBean zb = new ZinsberechnungsBean().
					setAnfangskapital(anfangsKapital).
					setLaufzeit(laufzeit).
					setZinssatz(zinsSatz);
			endbetrag = (Math.round(zb.get_endKapital()) * 100) / 100.0;
			//out.write("Endbetrag: " + endbetrag + " € <br/>");
			
			request.setAttribute("GeldStart", anfangsKapital);
			request.setAttribute("Zins", zb.get_zinsSatz());
			request.setAttribute("Laufzeit", laufzeit);
			request.setAttribute("Endbetrag", endbetrag);
			RequestDispatcher view = request.getRequestDispatcher("Ergebnis.jsp");
			view.forward(request, response);
		}
		catch(Exception ex)
		{
			out.write(ex.getMessage());
			/*if(ex.getMessage().contains("empty String"))
			{
				out.write("Es wurden nicht alle Daten eingegeben <br/>");
			}
			if(ex.getMessage().contains("For input string:"))
			{
				out.write("Es wurden falsche Daten eingegeben <br/>");
			}
			out.write("Sie werden in 3 Sekunden weitergeleitet<br/>");
			out.write("<script>setTimeout('history.back()', 3000)</script>");*/
		}
		//out.write("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
