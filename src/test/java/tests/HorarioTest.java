package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import converter.Converter;
import horario.Horario;

class HorarioTest {

	private Horario horario = new Horario();
	private String pathToCsv = "src/test/resources/csv/teste2.csv";
	private String pathToCsv2 = "src/test/resources/csv/teste.csv";
	
	@Test
	void getAulasFromUCtest() {
		horario = Converter.csvToJava(pathToCsv);
		Horario novoHorario = horario.getAulasFromUC("Arquitetura de Redes");
		assertEquals("Arquitetura de Redes",novoHorario.getAulas().getFirst().getUnidadeCurricular());
		assertEquals("Arquitetura de Redes",novoHorario.getAulas().getLast().getUnidadeCurricular());
	}
	@Test
    void addAulasTest() {

        horario = Converter.csvToJava(pathToCsv);
        Horario horarioToAdd = Converter.csvToJava(pathToCsv2);

        int expectedSize = horario.getAulas().size() + horarioToAdd.getAulas().size();
        horario.addAulas(horarioToAdd);

        assertEquals(expectedSize, horario.getAulas().size());
    }
}
