package Servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import Elements.Room;

/**
 * Servlet implementation class RoomServlet
 */
@WebServlet("/RoomServlet")
public class RoomServlet extends ListServlet<Room> {
	private static final long serialVersionUID = 1L;

    public RoomServlet() {
        super();
        ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("id");
    	orderFields.add("number");
    	orderFields.add("description");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("number");
    	filterFields.add("description");
        this.Setup(Room.class, "Administration/Rooms.jsp", "room", orderFields, filterFields);
    }
}
