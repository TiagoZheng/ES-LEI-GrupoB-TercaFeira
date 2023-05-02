<%@ page import = "java.util.*, horario.Horario, horario.Aula, converter.Converter" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html 
    PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"> 
<head>
    <meta http-equiv="Content-Type" content='text/html; charset=UTF-8'/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <link rel="stylesheet" media="screen" type="text/css" title="Preferred" href="number-guess.css"/>
    <title>JSP Number Guess</title>
</head>
<body>

<%
	Aula aula = new Aula("ME","Teoria dos Jogos e dos Contratos", "01789TP01","MEA1","30","Sex","13:00:00","14:30:00","02/12/2022","AA2.25","34");
	//Horario horario = Converter.csvToJava("teste2.csv");
%>

	<h1>Visualizador de horarios</h1>
	
    <div class='content'>

<%  
	String utilizador = request.getParameter("name")+"";
	if(!utilizador.equals("null")) {
%>
		<h2>Bem vindo <%=utilizador%></h2>
<%
	} else {
%>
		<h2>Bem vindo!</h2>
		
		<form method="post" action="/">
			<label for="name" >Indique o seu nome: </label>
			<input type="text" id="name" name="name"></input>
			<button type="submit">Submeter</button>
		</form>
<% 
	}
%>
	<br></br>

		<h2>Visualizar horarios:</h2>
		
		<p><a href="/horario">Horario1</a></p>
		
	<br></br>
		
		<h2>Importar horarios</h2>
		<form method="post" action="UploadServlet" enctype="multipart/form-data">
			<input type="file" name="file" value="" multiple="multiple"></input>
			<input type="submit" value="Import"></input>
		</form>
		
    </div>

</body>
</html>