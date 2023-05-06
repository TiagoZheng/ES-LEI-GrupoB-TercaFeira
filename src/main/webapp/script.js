let nav = 0; // corresponde ao mês em que estamos a navegar
let clicked = null; //corresponder a um dia selecionado

// array onde vai ser guardado o ficheiro .json e restantes eventos
let events = localStorage.getItem('events') ? JSON.parse(localStorage.getItem('events')): [];	
const calendar = document.getElementById('calendar');
const newEventModal = document.getElementById('newEventModal');
const deleteEventModal = document.getElementById('deleteEventModal');
const backDrop = document.getElementById('modalBackDrop');
const eventTitleInput = document.getElementById('eventTitleInput');
const dayEventList = document.getElementById('dayEventList');
const weekdays =["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

/*função para chamar o modal diário após clique num dia
	- @params date : recebe o dia que o user marcou*/
function openModal(date){
	clicked = date;
	var eventForDay = events.filter(e => e.date === clicked);
	
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
		
	calendar.innerHTML = '';
	
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
		calendar.appendChild(daySquare);
	}
} load();	

/*iniciar Botões e eventos de back e next*/
function initButtons(){
	document.getElementById("nextButton").addEventListener('click', () => {
		nav++;
		load();
	})
	document.getElementById("backButton").addEventListener('click', () => {
		nav--;
		load();
	})
	document.getElementById('saveButton').addEventListener('click', saveEvent);
	document.getElementById('cancelButton').addEventListener('click', closeModal);
	document.getElementById('deleteButton').addEventListener('click', deleteEvent);
	document.getElementById('closeButton').addEventListener('click', closeModal);
}
initButtons();
load();






