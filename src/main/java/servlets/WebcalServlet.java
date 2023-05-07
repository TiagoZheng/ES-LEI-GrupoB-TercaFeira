package servlets;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URL;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.BufferedReader;


public class WebcalServlet extends HttpServlet {

    private static final long serialVersionUID = -5038031250598060872L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String webcalUri = request.getParameter("webcaluri");
        System.out.println("webcalUri: " + webcalUri);

        String spec = null;
        if (webcalUri != null && !webcalUri.isEmpty()) {
            spec = webcalUri.trim();
        }
        if (spec != null) {
            try {
                // Open a connection to the webcal URI
                spec = spec.replace("webcal", "https");
                URL url = new URL(spec);
                URLConnection connection = url.openConnection();

                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
             // Read the contents of the input stream into a string
                BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                  sb.append(line);
                }
                reader.close();
                String csvData = sb.toString();
                System.out.println(csvData);
            } catch (MalformedURLException e) {
                System.out.println("webcal URI is malformed: no protocol");
            }
        } else {
            System.out.println("parameter webcal is null or empty");
        }

        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}























