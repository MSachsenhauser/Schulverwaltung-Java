package Servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Elements.Student;
import Elements.TimeTable;

/**
 * Servlet implementation class TimeTableServlet
 */
@WebServlet("/TimeTableServlet")
public class TimeTableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TimeTableServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("Administration/TimeTables.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
