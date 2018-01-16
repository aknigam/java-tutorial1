package jackson;

import com.cvent.reporting.api.authoring.ReportDefinition;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

public class JsonIncludeTests {


    public static void main(String[] args) {
        
    }


    public static void runPOC() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();

        File f = new File("/Users/a.nigam/Documents/workspace/java-tutorial/src/main/resource/anonymous-answer-summary.json");
        String resourceString = readJson(f);

        System.out.println(resourceString);
//        String line = "$include{metadata}";
//        System.out.println(  line.substring(line.indexOf("{")+ 1 , line.lastIndexOf("}") ));

        ReportDefinition def = objectMapper.readValue(resourceString, ReportDefinition.class);
        System.out.println(def.getMetadata().getId());
        System.out.println(def.getMetadata().getGroups());



    }

    private static String readJson(File f) throws IOException {


        StringWriter sw = new StringWriter();

        try (BufferedReader reader = new BufferedReader(new FileReader(f))){

            String line = null;
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                // read next line

                String trimmedLine = line.trim();
                if(trimmedLine.startsWith("$")){
                    String includedFileName = getIncludedFileName(trimmedLine);
                    line = readJson(new File(f.getParent()+ "/"+ includedFileName));
                }

                sw.append(line).append("\n");
            }
            reader.close();
        }

        return sw.toString();
    }

    private static String getIncludedFileName(String line) {
        return   line.substring(line.indexOf("{")+ 1 , line.lastIndexOf("}") );
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(ReportDefinition.class, new CustomDeserialiser<ReportDefinition>());
//        mapper.registerModule(module);
        return mapper;
    }
}
