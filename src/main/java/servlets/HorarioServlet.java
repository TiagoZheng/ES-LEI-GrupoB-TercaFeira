package servlets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import converter.Converter;
import horario.Horario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class HorarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Horario horario;

    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//URL resource = getServletContext().getResource("/WEB-INF/resources/teste4.json");
		
		String mainPath = "";
        try {
            URI uri = getServletContext().getResource("/WEB-INF/resources/").toURI();
            mainPath = Paths.get(uri).toString();

        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        Path path = Paths.get(mainPath, "teste4.json");
		
		
        horario = Converter.jsonToJava(path.toString());
		request.setAttribute("horario", horario);
		
		getServletContext().getRequestDispatcher("/horario.jsp").forward(request, response);
		
//		response.setContentType("text/plain");
//		response.getWriter().println("linha1");
//		response.getWriter().println("linha2");
//		
//		response.getWriter().append("Content: ").append(Arrays.toString(request.getContextPath().getBytes()));
		
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
		System.out.println("POST called");
	}

}
