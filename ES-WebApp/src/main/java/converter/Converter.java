package converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

import horario.Aula;
import horario.Horario;

public class Converter {

	private static ObjectMapper mapper = new ObjectMapper();
	

//	--------------------------------- CSV TO JAVA ---------------------------------
	// recebe o caminho dum ficheiro csv e transforma-o num ficheiro .json e chama recursivamente
	// o método jsonToJava
	
	
	
	public static Horario csvToJava(String csvPath) {
		String jsonPath = "src/test/resources/json/transit.json";
		csvToJson(csvPath, jsonPath);
		return jsonToJava(jsonPath);
	}

//	--------------------------------- CSV TO JSON ---------------------------------
	
	public static void csvToJson(String inputPath, String outputPath) {

		File input = new File(inputPath);
		File output = new File(outputPath);

		try {
			List<Map<String, Object>> objects = readObjectsFromCsv(input);
			writeAsJson(objects, output);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static List<Map<String, Object>> readObjectsFromCsv(File file) throws IOException {
		CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
		ObjectMapper csvMapper = new CsvMapper();
		try (MappingIterator<Map<String, Object>> iterator = csvMapper.readerFor(Map.class).with(bootstrap).readValues(file)) {
			return iterator.readAll();
		}
	}

	private static void writeAsJson(List<Map<String, Object>> objects, File file) throws IOException {
		renameKeys(objects);
		mapper.writeValue(file, renameKeys(objects));
	}
	
	private static List<Map<String, Object>> renameKeys(List<Map<String, Object>> objects) {
		List<Map<String, Object>> newObjects = new ArrayList<>();
		for(Map<String,Object> map : objects) {
			Map<String,Object> newMap = newMapWithUpdatedKeys(map);
			newObjects.add(newMap);   
		}
		return newObjects;
	}
	
	private static Map<String,Object> newMapWithUpdatedKeys(Map<String,Object> map){
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		Map<String,Object> newMap = new HashMap<>();	
		while (it.hasNext()) {
        	Entry<String, Object> entry = it.next();
            if (entry.getKey().startsWith("Cur"))
            	newMap.put("curso", entry.getValue());	            
            else if (entry.getKey().startsWith("Unid")) 
            	newMap.put("unidadeCurricular", entry.getValue());	            
            else if (entry.getKey().startsWith("Turn"))
            	newMap.put("turno", entry.getValue());	            
            else if (entry.getKey().startsWith("Turm")) 
            	newMap.put("turma", entry.getValue());	            
            else if (entry.getKey().startsWith("Inscr")) 
            	newMap.put("inscritos", entry.getValue());	            
            else if (entry.getKey().startsWith("Dia")) 
            	newMap.put("diaDaSemana", entry.getValue());	            
            else if (entry.getKey().startsWith("Hora in")) 
            	newMap.put("horaInicio", entry.getValue());	            
            else if (entry.getKey().startsWith("Hora fim")) 
            	newMap.put("horaFim", entry.getValue());
            else if (entry.getKey().startsWith("Data")) 
            	newMap.put("data", entry.getValue());            
            else if (entry.getKey().startsWith("Sala")) 
            	newMap.put("sala", entry.getValue());
            else if (entry.getKey().startsWith("Lot")) 
            	newMap.put("lotacao", entry.getValue());
        }
		return newMap;
	}
	
//	private static List<Map<String, Object>> formatBrokenStrings(List<Map<String, Object>> objects) {
//	List<Map<String, Object>> newObjects = new ArrayList<>();
//	
//	for(Map<String,Object> map : objects) {
//		Map<String,Object> newMap = new HashMap<>();
//		for (Map.Entry<String,Object> entry : map.entrySet()) {
//			String s2 = new String(entry.getKey().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//			newMap.put(s2, entry.getKey());
//		}
//		newObjects.add(newMap);
//	}
//	return newObjects;
//}
	
	
//	-------------------------------------------------------------------------------
//	--------------------------------- JSON TO CSV ---------------------------------

	public static void jsonToCsv(String inputPath, String outputPath) {
		File input = new File(inputPath);
		File output = new File(outputPath);
		
			try {
				readJson(input, output);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private static void readJson(File input, File output) throws IOException {
		JsonNode jsonTree = new ObjectMapper().readTree(input);
		Builder csvSchemaBuilder = CsvSchema.builder();
		JsonNode firstObject = jsonTree.elements().next();
		firstObject.fieldNames().forEachRemaining(csvSchemaBuilder::addColumn );
		CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();
      
		CsvMapper csvMapper = new CsvMapper();
		csvMapper.writerFor(JsonNode.class)
			.with(csvSchema)
			.writeValue(output, jsonTree);
	}
	
//	-----------------------------------------------------------------------------
//	--------------------------------- JAVA/JSON ---------------------------------

	
	public static String javaToJson(Horario horario) { 
		return horario.toString();  //TODO
	}
	
	public static String javaToJson(Aula aula) {
		try {
			return mapper.writeValueAsString(aula);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "javaToJson: writeError";
		}
	}
	
	
	public static Horario jsonToJava(String json) {
		
		if(!(json.startsWith("{") || json.startsWith("["))) { // is path to json file
			json=jsonToString(json);
		} 
		
		Horario horario = new Horario();
		try {
			if (json.startsWith("[")) { // is jsonArray
				List<Map<String, Object>> listaDeAulas = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
				for (Map<String, Object> map : listaDeAulas) {
					Aula aula = mapper.convertValue(map, Aula.class);
					horario.addAula(aula);
				}
			} else { // is jsonObject
				Aula aula = mapper.readValue(json, Aula.class);
				horario.addAula(aula);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return horario;
	}

	
	public static String jsonToString(String filePath) {
		String s="";
		try {
			s = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static void main(String[] args) {
		
//		Converter.csvToJson("src/main/resources/csv/teste2.csv", "src/main/resources/json/teste.json");
//		String s = Converter.jsonToString("src/main/resources/json/teste.json");
//		
//		System.out.println(Converter.jsonToJava(s).toString());
		
//		System.out.println(Converter.jsonToJava(Converter.jsonToString("src/main/resources/json/testePesado.json")).getAulas().getFirst().getCurso());
//		
//		Converter.csvToJson("src/main/resources/csv/horarioSemAcentos.csv",	"src/main/resources/json/testePesado.json");
//		
//		Converter.jsonToCsv("src/main/resources/json/abrirNoBrowser.json",	"src/main/resources/csv/teste5.csv");
		
//		Horario horario= Converter.jsonToJava();
//		
//		System.out.println(horario.getAulas().getFirst().toString());
	
		
	}

}
