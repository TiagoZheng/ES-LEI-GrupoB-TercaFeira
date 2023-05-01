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
		
		//Get path to files folder
        String mainPath = "";
        try {
            URI uri = getServletContext().getResource("/WEB-INF/resources/").toURI();
            mainPath = Paths.get(uri).toString();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }

        //TODO: Receber filename do horario que foi selecionado na homepage
        Path path = Paths.get(mainPath, "teste4.json");
        //teste
        String filename = request.getParameter("filename");
        System.out.println(filename);
//        System.out.println(new File(filename).getName());

        //TODO: verificar formato do ficheiro e fazer a conversao indicada
        Horario horario = Converter.jsonToJava(path.toString());

        //Para podermos receber o objeto java no horario.jsp com request.getAttribute("horario")
        request.setAttribute("horario", horario);

        //Go to horario page with updated request
        getServletContext().getRequestDispatcher("/horario.jsp").forward(request, response);
		
		
	}
	

}
