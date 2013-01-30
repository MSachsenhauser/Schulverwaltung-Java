package servlets;

import helpers.ImportHelper;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class ImportServlet
 */
@WebServlet("/ImportServlet")
public class ImportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ImportServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
				 DataInputStream in = new DataInputStream(request.getInputStream());
				 int formDataLength = request.getContentLength();
		
				 byte dataBytes[] = new byte[formDataLength];
				 int byteRead = 0;
				 int totalBytesRead = 0;
				 while (totalBytesRead < formDataLength) {
					 byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
					 totalBytesRead += byteRead;
				 }
		
				 String file = new String(dataBytes);
				 String saveFile = file.substring(file.indexOf("filename=\"") + 10);
				 saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
				 saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,saveFile.indexOf("\""));
		
				 int lastIndex = contentType.lastIndexOf("=");
				 String boundary = contentType.substring(lastIndex + 1,contentType.length());
				 int pos;
				 pos = file.indexOf("filename=\"");
				 pos = file.indexOf("\n", pos) + 1;
				 pos = file.indexOf("\n", pos) + 1;
				 pos = file.indexOf("\n", pos) + 1;
		
				 int boundaryLocation = file.indexOf(boundary, pos) - 4;
				 int startPos = ((file.substring(0, pos)).getBytes()).length;
				 int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
		
				 ImportHelper.Import(new ByteArrayInputStream(dataBytes, startPos, (endPos-startPos)));
				 
				 request.setAttribute("State", "Import erfolgreich abgeschlossen");
			 }
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
			 request.setAttribute("State", "Import fehlgeschlagen");
		 }
		 request.getRequestDispatcher("Import.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
