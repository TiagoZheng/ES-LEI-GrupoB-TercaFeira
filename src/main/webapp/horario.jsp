<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*, java.io.File, horario.Horario, horario.Aula, converter.Converter" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <link rel="stylesheet" media="screen" type="text/css" title="Preferred" href="style.css"/>

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
	
			
	
	<div id="container">
      
    	<header id="header">
			
    		<div id="monthDisplay"></div>
    		<h1>Horário</h1>
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
	
	<aside id="aside">
		<h3>Horario apenas com as aulas de Teoria dos Jogos e dos Contratos e Arquitetura de Redes:</h3>
		
		<form method="post" action="downloadservlet">
	    <label for="format">Selecione o formato:</label>
		    <select id="format" name="format">
		        <option value="csv">CSV</option>
		        <option value="json">JSON</option>
		    </select>
		    <input type="submit" value="Download" />
		</form>
	</aside>
	
	<script type="text/javascript">
		let nav = 0; // corresponde ao mês em que estamos a navegar
		let clicked = null; //corresponder a um dia selecionado
		let isMonthView = true;
		let weekNav = 0;
		// array onde vai ser guardado o ficheiro .json e restantes eventos
		let events = localStorage.getItem('events') ? JSON.parse(localStorage.getItem('events')): [];
		const weekCalendar = document.getElementById("weekCalendar");
		const monthCalendar = document.getElementById('calendar')
		const toggleViewButton = document.getElementById("toggleViewButton");
		const newEventModal = document.getElementById('newEventModal');
		const deleteEventModal = document.getElementById('deleteEventModal');
		const backDrop = document.getElementById('modalBackDrop');
		const eventTitleInput = document.getElementById('eventTitleInput');
		const dayEventList = document.getElementById('dayEventList');
		const weekdays =["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	
	
		function addEvent(date, titulo){
			events.push({
				date: date,
				title: titulo,
			});
			localStorage.setItem('events', JSON.stringify(events)); 
		}
	
		/*função para chamar o modal diário após clique num dia
			- @params date : recebe o dia que o user marcou*/	
			
		function toggleView(){
			nav=0;
			weekNav=0;
			if(isMonthView){
				weekCalendar.style.display = 'none';
				monthCalendar.style.display='flex';
				load();
			}else{
				weekCalendar.style.display = 'flex';
				monthCalendar.style.display='none';
				load();
			}
		}
	
	
	
		function openModal(date){
			clicked = date;
			let eventForDay = events.filter(e => e.date === clicked);
			
			if(eventForDay.length > 0){
				for(const element of eventForDay){
					const eventDiv = document.createElement('p');
					eventDiv.innerText = element.title;
					document.getElementById('dayEventList').appendChild(eventDiv);
				}
				deleteEventModal.style.display ='block';
				
			}else{
				newEventModal.style.display = 'block';
			}
			backDrop.style.display = 'block';
		}
	
		function closeModal(){
			eventTitleInput.classList.remove('error');
			newEventModal.style.display='none';
			deleteEventModal.style.display='none';
			backDrop.style.display='none';
			eventTitleInput.value='';
			dayEventList.innerHTML='';
			clicked = null;
			load();
		}
	
		function saveEvent(){
			if(eventTitleInput.value){
				eventTitleInput.classList.remove('error');
				events.push({
					date:clicked,
					title: eventTitleInput.value,
				});
				localStorage.setItem('events', JSON.stringify(events));
			}else{
				eventTitleInput.classList.add('error');
			}
			closeModal();
		}
	
	
		function deleteEvent(event){
			 events = events.filter(e => e !== event);
			 localStorage.setItem('events', JSON.stringify(events));
			 closeModal();
		}
	
		// funcao vai ser chamada para carregar o calendario ou "refresh"
		function load(){
			const date = new Date();
			
			if(isMonthView){
				if(nav !== 0){
					date.setMonth(new Date().getMonth() + nav);
				}
				const day = date.getDate();
				const month = date.getMonth();
				const year = date.getFullYear();
				const firstDayOfMonth = new Date(year, month, 1);	// primeiro dia do mês
				const daysInMonth = new Date(year, month+1, 0).getDate();	//ultimo dia do mês
				const dateString = firstDayOfMonth.toLocaleString('en-us', {
					weekday: 'long',
					year: 'numeric',
					month:'numeric',
					day:'numeric',
				});
				console.log(dateString);
				
		
				const paddingDays = weekdays.indexOf(dateString.split(", ")[0]);	// desvio de dias no calendário tendo em conta em que dia da semana o mês começa
				document.getElementById("monthDisplay").innerText = `${date.toLocaleString('pt-pt', {month: 'long'})} , ${year}`;
				
				monthCalendar.innerHTML = '';
				
				for(let i=1; i <= paddingDays + daysInMonth; i++){
					
					const daySquare = document.createElement('div'); //entrada diária na tabela
					daySquare.classList.add('day');
					
					const dayString = `${month + 1}/${i - paddingDays}/${year}`;
					
					if(i > paddingDays){	
						daySquare.innerText = i - paddingDays;	// inserir dia do mês nos quadrados
						
						const eventForDay = events.find(e => e.date === dayString);
					
						if(eventForDay){
							const eventDiv = document.createElement('div');
							eventDiv.classList.add('event');
							eventDiv.innerText = eventForDay.title;
							daySquare.appendChild(eventDiv);
						}
						
						daySquare.addEventListener('click', () => openModal(dayString));	//evento ao clicar nalgum dia
						
					}else{
						daySquare.classList.add('padding');	// dia em branco
					}
					monthCalendar.appendChild(daySquare);
				}
			} else {
				weekCalendar.innerHTML = '';
				const today = new Date(); // Obtém a data atual
				if(weekNav !== 0){
					today.setDate(today.getDate() + weekNav*7);
				}
				const dayOfWeek = date.getDay(); // Obtém o dia da semana (0-6, onde 0 é domingo)
				// Calcula a diferença entre o dia atual e o primeiro dia da semana (domingo)
				const diff = (dayOfWeek >= 0 ? dayOfWeek : 7) - 0;
				// Define a data para o primeiro dia da semana
				const firstDayOfWeek = new Date(today.setDate(today.getDate() - diff));
				const month = firstDayOfWeek.getMonth();
				const year = firstDayOfWeek.getFullYear();
				const day = firstDayOfWeek.getDate();
				document.getElementById("monthDisplay").innerText = `${firstDayOfWeek.toLocaleString('pt-pt', {month: 'long'})} , ${year}`;
				
				for(let i=0; i<7; i++){
					const dayString = `${month + 1}/${firstDayOfWeek.getDate()}/${year}`;
					const weekdayDiv = document.createElement('div');
					weekdayDiv.classList.add('weekdayDiv');
					weekdayDiv.innerText = firstDayOfWeek.getDate();
					weekCalendar.appendChild(weekdayDiv);
					let eventsForDay = events.filter(e => e.date === dayString);
					
					if(eventsForDay.length > 0){
						for(const element of eventsForDay){
							const eventDiv = document.createElement('div');
							eventDiv.classList.add('eventDiv');
							weekdayDiv.appendChild(eventDiv);
							eventDiv.innerText = element.title;
						}
					}
					
					firstDayOfWeek.setDate(firstDayOfWeek.getDate() + 1);
				}
			}
			
		} load();	
	
		/*iniciar Botões e eventos de back e next*/
		function initButtons(){
			document.getElementById("nextButton").addEventListener('click', () => {
				if(isMonthView){
					nav++;
				} else {
					weekNav++;
				}
				load();
			});
			document.getElementById("backButton").addEventListener('click', () => {
				if(isMonthView){
					nav--;
				} else {
					weekNav--;
				}
				load();
			});
			
			toggleViewButton.addEventListener('click', () => {
				isMonthView = !isMonthView;
				toggleView();	
			});
			document.getElementById('saveButton').addEventListener('click', saveEvent);
			document.getElementById('cancelButton').addEventListener('click', closeModal);
			//document.getElementById('deleteButton').addEventListener('click', deleteEvent);
			document.getElementById('closeButton').addEventListener('click', closeModal);
	
		}
		initButtons();
		load();
	</script>
	<%
		for(int i=0; i < subHorario2.getAulas().size(); i++){
			Aula thisAula = subHorario2.getAulas().get(i);
			String[] dateList= thisAula.getData().split("/"); 
			String titulo = thisAula.getUnidadeCurricular();
			String dateString = dateList[1] + "/" + dateList[0] + "/" + dateList[2];
			String horaInicio = thisAula.getHoraInicio();
			String sala = thisAula.getSala();
	%>	
	
	<script type="text/javascript">
		addEvent('<%= dateString %>','<%= titulo %>, <%= sala %>, <%= horaInicio %>');
	</script>
	<% } %>
	<%
		for(int i=0; i < subHorario.getAulas().size(); i++){
			Aula thisAula = subHorario.getAulas().get(i);
			String[] dateList= thisAula.getData().split("/"); 
			String titulo = thisAula.getUnidadeCurricular();
			String dateString = dateList[1] + "/" + dateList[0] + "/" + dateList[2];
			String horaInicio = thisAula.getHoraInicio();
			String sala = thisAula.getSala();
	%>	
	
	<script type="text/javascript">
		addEvent('<%= dateString %>','<%= titulo %>, <%= sala %>, <%= horaInicio %>');
	</script>
	<% } %>
	
</body>
</html>