<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*, horario.Horario, horario.Aula, converter.Converter" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="style.css" />
    <title>Calendário</title>
  </head>
  <body>
	  
    <div id="container">
      
    	<header id="header">

    		<div id="monthDisplay"></div>
    		<div>
    			<button id="toggleViewButton">Alternar vista</button>
    		
    			<button id="backButton">Back</button>
    			<button id="nextButton">Next</button>
    		</div>
    		
    	</header>
    	
		 <div class="weekdays" id="weekdays">
            <div>Domingo</div>
            <div>Segunda</div>
            <div>Terça</div>
            <div>Quarta</div>
            <div>Quinta</div>
            <div>Sexta</div>
            <div>Sábado</div>
          </div>
          
          <div id="calendar"></div>
          <div id="weekCalendar"></div>
    </div>


	<div id='newEventModal'>
		<h2>New Event</h2>
		<input id="eventTitleInput" placeholder="Event Title"/>
		<button id="saveButton">Save</button>
		<button id="cancelButton">Cancel</button>
	</div>
	
	<div id="deleteEventModal">
		<h2>Events for the day</h2>
		<div id="dayEventList"></div>
		<button id="deleteButton">delete</button>	
		<button id="closeButton">close</button>
	</div>
	<div id="modalBackDrop"></div>
	
	<section></section>
    <script src="script.js">
    	
    </script>
  </body>
</html>
