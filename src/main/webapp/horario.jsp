<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*, java.io.File, horario.Horario, horario.Aula, converter.Converter" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <link rel="stylesheet" media="screen" type="text/css" title="Preferred" href="number-guess.css"/>

	<title>horario page</title>
</head>
<body>

	<% 
		Horario horario = (Horario)request.getAttribute("horario");
	
		Horario subHorario = horario.getAulasFromUC("Teoria dos Jogos e dos Contratos");
		
		Horario subHorario2 = horario.getAulasFromUC("Arquitetura de Redes");
		
		Converter.stringToJson(Converter.javaToJson(horario), getServletContext().getRealPath("WEB-INF/download/horario.json"));
		Converter.jsonToCsv(getServletContext().getRealPath("WEB-INF/download/horario.json"), getServletContext().getRealPath("WEB-INF/download/horario.csv"));
	%>
	
	<h1>Horario Page (ficheiro horario.jsp)</h1>
	
	<p>O horario selecionado tem um total de <%= horario.getAulas().size() %> aulas.</p>
	
	<form method="post" action="downloadservlet">
	    <label for="format">Selecione o formato:</label>
	    <select id="format" name="format">
	        <option value="csv">CSV</option>
	        <option value="json">JSON</option>
	    </select>
	    <input type="submit" value="Download" />
	</form>
	
	
	<h3>Horario apenas com as aulas de Teoria dos Jogos e dos Contratos:</h3>
	
	<p><%= subHorario %></p>
	<p> Primeira aula: </p>
	<p><%= subHorario.getAulas().get(0).getData() %> às <%= subHorario.getAulas().get(0).getHoraInicio() %> na sala <%= subHorario.getAulas().get(0).getSala() %></p>
	
	<h3>Horario apenas com as aulas de Arquitetura de Redes:</h3>
	
	<p><%= subHorario2 %></p>
	<p>Primeira aula: </p>
	<p><%= subHorario.getAulas().get(0).getData() %> às <%= subHorario.getAulas().get(0).getHoraInicio() %> na sala <%= subHorario.getAulas().get(0).getSala() %></p>
	
	
</body>
</html>