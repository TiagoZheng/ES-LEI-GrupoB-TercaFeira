package converter;

import java.io.File;
import java.io.FileWriter;
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
import com.fasterxml.jackson.databind.ObjectWriter;

import horario.Aula;
import horario.Horario;

public class Converter {
	
	private Converter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

	private static ObjectMapper mapper = new ObjectMapper();
	

//	--------------------------------- CSV TO JAVA ---------------------------------
	
	public static Horario csvToJava(String csvPath) {
		String tempJson="";
        try {
            // Create temporary file
            tempJson = Files.createTempFile("transit", ".json").toString();
            System.out.println("Temp file : " + tempJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		csvToJson(csvPath, tempJson);
		return jsonToJava(tempJson);
	}


//	--------------------------------- CSV TO JSON ---------------------------------
	
	public static void csvToJson(String inputPath, String outputPath) {
        try {
            List<Map<String, Object>> objects = readObjectsFromCsv(new File(inputPath));
            mapper.writeValue(new File(outputPath), renameKeys(objects));
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
                newMap.put("Curso", entry.getValue());
            else if (entry.getKey().startsWith("Unid")) 
                newMap.put("Unidade Curricular", entry.getValue());
            else if (entry.getKey().startsWith("Turn"))
                newMap.put("Turno", entry.getValue());
            else if (entry.getKey().startsWith("Turm")) 
                newMap.put("Turma", entry.getValue());
            else if (entry.getKey().startsWith("Inscr")) 
                newMap.put("Inscritos no turno", entry.getValue());
            else if (entry.getKey().startsWith("Dia")) 
                newMap.put("Dia da semana", entry.getValue());
            else if (entry.getKey().startsWith("Hora in")) 
                newMap.put("Hora inicio da aula", entry.getValue());
            else if (entry.getKey().startsWith("Hora fim")) 
                newMap.put("Hora fim da aula", entry.getValue());
            else if (entry.getKey().startsWith("Data")) 
                newMap.put("Data da aula", entry.getValue());
            else if (entry.getKey().startsWith("Sala")) 
                newMap.put("Sala atribuida a aula", entry.getValue());
            else if (entry.getKey().startsWith("Lot")) 
                newMap.put("Lotacao da sala", entry.getValue());
        }
        return newMap;
    }
	
	public static void stringToJson(String jsonString, String path) {
		
        try (FileWriter fileWriter = new FileWriter(path)){
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		
	
//	-------------------------------------------------------------------------------
//	--------------------------------- JSON TO CSV ---------------------------------

	public static void jsonToCsv(String inputPath, String outputPath) {
        try {
            JsonNode jsonTree = mapper.readTree(new File(inputPath));

            Builder csvSchemaBuilder = CsvSchema.builder()
                    .addColumn("Curso")
                    .addColumn("Unidade Curricular")
                    .addColumn("Turno")
                    .addColumn("Turma")
                    .addColumn("Inscritos no turno")
                    .addColumn("Dia da semana")
                    .addColumn("Hora inicio da aula")
                    .addColumn("Hora fim da aula")
                    .addColumn("Data da aula")
                    .addColumn("Sala atribuida a aula")
                    .addColumn("Lotacao da sala");
            CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

            CsvMapper csvMapper = new CsvMapper();
            csvMapper.writerFor(JsonNode.class)
                    .with(csvSchema)
                    .writeValue(new File(outputPath), jsonTree);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
//	-----------------------------------------------------------------------------
//	--------------------------------- JAVA/JSON ---------------------------------

	public static String javaToJson(Horario horario) { 

      ObjectWriter objectWriter = new ObjectMapper().writer();
      String json = "";
      try {
          json = objectWriter.writeValueAsString(horario.getAulas());
      } catch (JsonProcessingException e) {
          e.printStackTrace();
      }
      return json;
  }
	
	public static String javaToJson(Aula aula) {
      try {
          return mapper.writeValueAsString(aula);
      } catch (JsonProcessingException e) {
          return "javaToJson: writeError";
      }
  }
	
	public static Horario jsonToJava(String json) {

        if(!(json.startsWith("{") || json.startsWith("["))) { // is path to json file
            json=readFile(json);
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

    public static String readFile(String filePath) {
        String s="";
        try {
            s = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
    
}
