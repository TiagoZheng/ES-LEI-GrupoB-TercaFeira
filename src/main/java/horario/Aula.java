package horario;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Aula implements Serializable{
	
	private static final long serialVersionUID = 6528857634606511979L;
	
	@JsonProperty("Curso")
    private String curso;
    @JsonProperty("Unidade Curricular")
    private String unidadeCurricular;
    @JsonProperty("Turno")
    private String turno;
    @JsonProperty("Turma")
    private String turma;
    @JsonProperty("Inscritos no turno")
    private String inscritos;
    @JsonProperty("Dia da semana")
    private String diaDaSemana;
    @JsonProperty("Hora inicio da aula")
    private String horaInicio;
    @JsonProperty("Hora fim da aula")
    private String horaFim;
    @JsonProperty("Data da aula")
    private String data;
    @JsonProperty("Sala atribuida a aula")
    private String sala;
    @JsonProperty("Lotacao da sala")
    private String lotacao;
	
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
		return curso;
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
