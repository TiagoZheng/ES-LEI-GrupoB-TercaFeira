package servlets;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 9179338714598464162L;

	
	   @Override
	   protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		   
		   String pathToCsv = getServletContext().getRealPath("/WEB-INF/download/horario.csv");
		   String pathToJson = getServletContext().getRealPath("/WEB-INF/download/horario.json");
		   
	       String fileType = request.getParameter("format");
	       String filePath = "";

	        if (fileType.equalsIgnoreCase("csv")) {
	            filePath = pathToCsv;
	            response.setContentType("text/csv");
	        } else if (fileType.equalsIgnoreCase("json")) {
	            filePath = pathToJson;
	            response.setContentType("application/json");
	        }

	        File file = new File(filePath);

	        // Set response headers
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + new File(filePath).getName() + "\"");
	        response.setContentLength((int) file.length());

	        // Open file streams
	        try (FileInputStream in = new FileInputStream(file)){
	        	 OutputStream out = response.getOutputStream();

		        // Copy file content to response
		        byte[] buffer = new byte[4096];
		        int bytesRead;
		        while ((bytesRead = in.read(buffer)) != -1) {
		            out.write(buffer, 0, bytesRead);
		        }
		        out.flush();
		        out.close();
	        }
	       
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        doGet(request, response);
	    }

}
