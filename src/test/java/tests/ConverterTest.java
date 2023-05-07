package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.io.File;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import converter.Converter;
import horario.Aula;
import horario.Horario;

class ConverterTest {
	
	private Aula aula = new Aula("ME","Teoria dos Jogos e dos Contratos", "01789TP01","MEA1","30",
			"Sex","13:00:00","14:30:00","02/12/2022","AA2.25","34");
	
	private Horario horario = new Horario();
			
	
	private String jsonObject = "{\"Curso\":\"ME\",\"Unidade Curricular\":\"Teoria dos Jogos e dos Contratos\","
				+ "\"Turno\":\"01789TP01\",\"Turma\":\"MEA1\",\"Inscritos no turno\":\"30\",\"Dia da semana\":\"Sex\","
				+ "\"Hora inicio da aula\":\"13:00:00\",\"Hora fim da aula\":\"14:30:00\",\"Data da aula\":\"02/12/2022\","
				+ "\"Sala atribuida a aula\":\"AA2.25\",\"Lotacao da sala\":\"34\"}";
	
	private String jsonArray = "[{\"Inscritos no turno\":\"30\",\"Turma\":\"MEA1\",\"Turno\":\"01789TP01\",\"Hora inicio da aula\":\"13:00:00\",\"Lotacao da sala\":\"34\",\"Hora fim da aula\":\"14:30:00\",\"Curso\":\"ME\",\"Unidade Curricular\":\"Teoria dos Jogos e dos Contratos\",\"Dia da semana\":\"Sex\",\"Data da aula\":\"02/12/2022\",\"Sala atribuida a aula\":\"AA2.25\"},"
			+ "{\"Inscritos no turno\":\"52\",\"Turma\":\"EI-A6, EI-A5, EI-A4, EI-A3, EI-A2, EI-A1\",\"Turno\":\"03703TP02\",\"Hora inicio da aula\":\"14:30:00\",\"Lotacao da sala\":\"80\",\"Hora fim da aula\":\"16:00:00\",\"Curso\":\"LETI, LEI, LEI-PL\",\"Unidade Curricular\":\"Cálculo I\",\"Dia da semana\":\"Qui\",\"Data da aula\":\"15/12/2022\",\"Sala atribuida a aula\":\"C6.10\"},"
			+ "{\"Inscritos no turno\":\"35\",\"Turma\":\"FCC2\",\"Turno\":\"M8642TP01\",\"Hora inicio da aula\":\"11:00:00\",\"Lotacao da sala\":\"68\",\"Hora fim da aula\":\"12:30:00\",\"Curso\":\"LFC\",\"Unidade Curricular\":\"Reporte Financeiro\",\"Dia da semana\":\"Qua\",\"Data da aula\":\"16/11/2022\",\"Sala atribuida a aula\":\"C4.06\"},"
			+ "{\"Inscritos no turno\":\"12\",\"Turma\":\"ET-C4, ET-C3\",\"Turno\":\"03708PL07\",\"Hora inicio da aula\":\"11:00:00\",\"Lotacao da sala\":\"66\",\"Hora fim da aula\":\"12:30:00\",\"Curso\":\"LETI, LEI, LEI-PL, LIGE, LIGE-PL\",\"Unidade Curricular\":\"Arquitetura de Redes\",\"Dia da semana\":\"Qui\",\"Data da aula\":\"17/11/2022\",\"Sala atribuida a aula\":\"C7.06\"}]";
	
	private String jsonArray2 = "[{\"Curso\":\"ME\",\"Unidade Curricular\":\"Teoria dos Jogos e dos Contratos\",\"Turno\":\"01789TP01\",\"Turma\":\"MEA1\",\"Inscritos no turno\":\"30\",\"Dia da semana\":\"Sex\",\"Hora inicio da aula\":\"13:00:00\",\"Hora fim da aula\":\"14:30:00\",\"Data da aula\":\"02/12/2022\",\"Sala atribuida a aula\":\"AA2.25\",\"Lotacao da sala\":\"34\"},"
			+ "{\"Curso\":\"LETI, LEI, LEI-PL\",\"Unidade Curricular\":\"Cálculo I\",\"Turno\":\"03703TP02\",\"Turma\":\"EI-A6, EI-A5, EI-A4, EI-A3, EI-A2, EI-A1\",\"Inscritos no turno\":\"52\",\"Dia da semana\":\"Qui\",\"Hora inicio da aula\":\"14:30:00\",\"Hora fim da aula\":\"16:00:00\",\"Data da aula\":\"15/12/2022\",\"Sala atribuida a aula\":\"C6.10\",\"Lotacao da sala\":\"80\"},"
			+ "{\"Curso\":\"LFC\",\"Unidade Curricular\":\"Reporte Financeiro\",\"Turno\":\"M8642TP01\",\"Turma\":\"FCC2\",\"Inscritos no turno\":\"35\",\"Dia da semana\":\"Qua\",\"Hora inicio da aula\":\"11:00:00\",\"Hora fim da aula\":\"12:30:00\",\"Data da aula\":\"16/11/2022\",\"Sala atribuida a aula\":\"C4.06\",\"Lotacao da sala\":\"68\"},"
			+ "{\"Curso\":\"LETI, LEI, LEI-PL, LIGE, LIGE-PL\",\"Unidade Curricular\":\"Arquitetura de Redes\",\"Turno\":\"03708PL07\",\"Turma\":\"ET-C4, ET-C3\",\"Inscritos no turno\":\"12\",\"Dia da semana\":\"Qui\",\"Hora inicio da aula\":\"11:00:00\",\"Hora fim da aula\":\"12:30:00\",\"Data da aula\":\"17/11/2022\",\"Sala atribuida a aula\":\"C7.06\",\"Lotacao da sala\":\"66\"}]";
	
	
	private String pathToJsonObject = "src/test/resources/json/jsonObject.json";
	private String pathToJsonArray = "src/test/resources/json/jsonArray.json";
	private String pathToCsv = "src/test/resources/csv/teste2.csv";
	private String pathToOneLineCsv = "src/test/resources/csv/oneLine.csv";
	private String initialText = "[{\"Inscritos no turno\":\"30\",\"Turma\":\"MEA1";
	
//	--------------------------------- CSV/JSON ---------------------------------	
	
	@Test
	void givenJsonStringCreatesJsonFile() {
		Converter.stringToJson(jsonArray, pathToJsonArray);
		String fileContent = Converter.readFile(pathToJsonArray);
		assertEquals(true, fileContent.startsWith(initialText));
	}
	
	@Test
	void givenCsvReturnsJson() {
		Converter.csvToJson(pathToCsv, "src/test/resources/json/csvToJsonTest.json");	
		assertEquals(true, Converter.readFile("src/test/resources/json/csvToJsonTest.json").startsWith(initialText));
	}
	
	@Test
	void givenJsonArrayReturnsCsv() {
		Converter.jsonToCsv(pathToJsonArray, "src/test/resources/csv/jsonToCsvTest.csv");
		File expected = new File(pathToCsv);
		File actual = new File("src/test/resources/csv//jsonToCsvTest.csv");
		assertEquals(expected.getTotalSpace(), actual.getTotalSpace());
	}
	
	@Test
	void givenJsonObjectReturnsCsv() {
		Converter.jsonToCsv(pathToJsonObject, "src/test/resources/csv/jsonObjectToCsvTest.csv");
		File expected = new File(pathToOneLineCsv);
		File actual = new File("src/test/resources/csv//jsonObjectToCsvTest.csv");
		assertEquals(expected.getTotalSpace(), actual.getTotalSpace());
	}
	
	@Test
	void givenCsvReturnsJavaObject() {
		horario = Converter.csvToJava(pathToCsv);
		assertEquals("17/11/2022",horario.getAulas().getLast().getData());
	}
	
	
//	--------------------------------- JAVA/JSON ---------------------------------
	
	@Test
	void givenJsonObjectReturnsJavaObject() {
		Aula aula = Converter.jsonToJava(jsonObject).getAulas().getFirst();
		assertEquals("ME", aula.getCurso());
	}
	
	@Test
	void givenJsonArrayReturnsJavaObject() {
		assertEquals("ME", Converter.jsonToJava(jsonArray).getAulas().getFirst().getCurso());
		assertEquals("LETI, LEI, LEI-PL, LIGE, LIGE-PL", Converter.jsonToJava(jsonArray).getAulas().getLast().getCurso());
	}
	
	@Test
	void givenJavaObjectReturnsJson() {
		
		assertEquals(jsonObject, Converter.javaToJson(aula));
		
		horario = Converter.csvToJava(pathToCsv);
		assertEquals(jsonArray2, Converter.javaToJson(horario));
	}
	
	
	
//	--------------------------------- OTHER ---------------------------------

	@Test
    void deserializeJson() throws IOException {
        String json = "{\"curso\":\"lei\",\"inscritos\":30}";

        JsonNode jsonTree = new ObjectMapper().readTree(json);

        assertEquals("lei", jsonTree.get("curso").asText());
        assertEquals(30, jsonTree.get("inscritos").asInt());
    }
	
	@Test
	void givenUTF8StringReturnsISOString() throws UnsupportedEncodingException {
		String UTF8string = "SeminÃ¡rio inÃ­cio LotaÃ§Ã£o";
		assertEquals("Seminário início Lotação", new String(UTF8string.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

		String UTF8string2 = "SeminÃ¡rio inÃ­cio LotaÃ§Ã£o atribuÃ­da Ã  aula";
		assertEquals("Seminário início Lotação atribuída à aula", new String(UTF8string2.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
	}
	
}
