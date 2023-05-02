package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import converter.Converter;
import horario.Horario;

class HorarioTest {

	private Horario horario = new Horario();
	private String pathToCsv = "src/test/resources/csv/teste2.csv";
	
	@Test
	void getAulasFromUCtest() {
		horario = Converter.csvToJava(pathToCsv);
		Horario novoHorario = horario.getAulasFromUC("Arquitetura de Redes");
		assertEquals("Arquitetura de Redes",novoHorario.getAulas().getFirst().getUnidadeCurricular());
		assertEquals("Arquitetura de Redes",novoHorario.getAulas().getLast().getUnidadeCurricular());
	}

}
