package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		maxFileSize = 1024 * 1024 * 10, // 10 MB
		maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 9179338714598464162L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Part filePart = request.getPart("file");
		
		//Check if user set new name and if csv/json extensions were given 
		String fileName;
		if(request.getParameter("filename").isEmpty()) 
			fileName = filePart.getSubmittedFileName();
		else {
			fileName = request.getParameter("filename");
			if(!(fileName.endsWith(".csv") || fileName.endsWith(".json"))) {
				if(filePart.getSubmittedFileName().endsWith(".csv"))
					fileName += ".csv";
				else if(filePart.getSubmittedFileName().endsWith(".json"))
					fileName += ".json";
			}
		}
			
		//Save file 
		String path = getServletContext().getRealPath("/WEB-INF/resources/");
		for (Part part : request.getParts()) 
			part.write(path + fileName);
		System.out.println("File uploaded to path " + path + fileName);
		
		//Go back to homepage
		getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
	}

}
