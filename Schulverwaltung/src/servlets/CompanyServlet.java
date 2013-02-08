package servlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import elements.Company;

@WebServlet("/CompanyServlet")
public class CompanyServlet extends ListServlet<Company> {
	private static final long serialVersionUID = 1L;
       
    public CompanyServlet() {
        super();
        ArrayList<String> orderFields = new ArrayList<String>();
    	orderFields.add("id");
    	orderFields.add("Name");
    	orderFields.add("Street");
    	orderFields.add("City");
    	ArrayList<String> filterFields = new ArrayList<String>();
    	filterFields.add("Name");
    	filterFields.add("Street");
    	filterFields.add("City");
        this.Setup(Company.class, "Administration/Companies.jsp", "company", orderFields, filterFields);
    }
}
