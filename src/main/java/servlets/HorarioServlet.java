package servlets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import converter.Converter;
import horario.Horario;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class HorarioServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
private Horario horario = new Horario();

    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get path to files folder
        String mainPath = "";
        try {
            URI uri = getServletContext().getResource("/WEB-INF/resources/").toURI();
            mainPath = Paths.get(uri).toString();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        
        //get path to selected file
        String filename = request.getParameter("filename");
        Path path = Paths.get(mainPath, filename);
        
        //make the right conversion
        if(filename.endsWith(".json"))
        	horario = Converter.jsonToJava(path.toString());
        else if(filename.endsWith(".csv"))
        	horario = Converter.csvToJava(path.toString());
        
        
        //Para podermos receber o objeto java no horario.jsp com request.getAttribute("horario")
        request.setAttribute("horario", horario);

        //Go to horario page with updated request
        getServletContext().getRequestDispatcher("/horario.jsp").forward(request, response);
		
	}

}
