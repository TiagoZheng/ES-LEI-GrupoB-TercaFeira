package horario;

public class Aula {
	
	private String curso, unidadeCurricular, turno, turma, inscritos, diaDaSemana,
	horaInicio, horaFim, data,
	sala, lotacao;

	
	public Aula(String curso, String unidadeCurricular, String turno, String turma, String inscritos,
			String diaDaSemana, String horaInicio, String horaFim, String data, String sala, String lotacao) {
		super();
		this.curso = curso;
		this.unidadeCurricular = unidadeCurricular;
		this.turno = turno;
		this.turma = turma;
		this.inscritos = inscritos;
		this.diaDaSemana = diaDaSemana;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.data = data;
		this.sala = sala;
		this.lotacao = lotacao;
	}

	
	public Aula() {
		unidadeCurricular = "ME";
		horaInicio = "hora";
		data = "data";
	}

	


	@Override
	public String toString() {
		return "Aula [unidadeCurricular=" + unidadeCurricular + "]";
	}

	public String getCurso() {
		return curso
				;
	}



	public void setCurso(String curso) {
		this.curso = curso;
	}



	public String getUnidadeCurricular() {
		return unidadeCurricular;
	}



	public void setUnidadeCurricular(String unidadeCurricular) {
		this.unidadeCurricular = unidadeCurricular;
	}



	public String getTurno() {
		return turno;
	}



	public void setTurno(String turno) {
		this.turno = turno;
	}



	public String getTurma() {
		return turma;
	}



	public void setTurma(String turma) {
		this.turma = turma;
	}



	public String getInscritos() {
		return inscritos;
	}



	public void setInscritos(String inscritos) {
		this.inscritos = inscritos;
	}



	public String getDiaDaSemana() {
		return diaDaSemana;
	}



	public void setDiaDaSemana(String diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}



	public String getHoraInicio() {
		return horaInicio;
	}



	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}



	public String getHoraFim() {
		return horaFim;
	}



	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}



	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	public String getSala() {
		return sala;
	}



	public void setSala(String sala) {
		this.sala = sala;
	}



	public String getLotacao() {
		return lotacao;
	}



	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

		
}
