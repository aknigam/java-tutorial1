package jackson;

import com.cvent.reporting.api.authoring.ReportDefinition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.regex.Pattern;

/*

"$includeObject{metadata.json}"

has to replaced with following json object:

{
  "id": "anonymousAnswerSummary",
  "title": "answerSummary_title__resx",
  "groups": [ "anonymousNonExportSurveyGroup" ],
  "associatedContextParamNames": [ "surveyStub" ],
  "entityType": "Survey"
}


The value of $ref uses the JSON Reference notation, and the portion starting with # uses the JSON Pointer notation.
This notation lets you specify the target file or a specific part of a file you want to reference.
In the previous example, #/components/schemas/User means the resolving starts from the root of the current document,
and then finds the values of components, schemas, and User one after another.

https://tools.ietf.org/html/draft-pbryan-zyp-json-ref-03

https://tools.ietf.org/html/draft-pbryan-zyp-json-ref-03

https://github.com/AndersDJohnson/jackson-json-reference

http://json-reference.thephpleague.com/simple-example




 */
public class ReportingContentReader {


    private ObjectMapper mapper;
    private Map<String, String> includes;

    public static void main(String[] args) throws IOException {

        ReportingContentReader j= new ReportingContentReader();

//        runPOC();

        j.parseReportWithParent();


    }

    private void parseReportWithParent() throws IOException {
        System.out.println(System.getProperty("path"));
        InputStream stream = this.getClass().getResourceAsStream("known-as.json");

        String contentString = Resources.toString(ReportingContentReader.class.getResource("known-as.json"),
                Charsets.UTF_8);

//        File r = new File("/Users/a.nigam/Documents/workspace/java-tutorial/src/main/resource/include-test/known-answer-summary.json");

        File f = new File(ReportingContentReader.class.getResource("known-as.json").getFile());
        readJson(f);
        mapper = new ObjectMapper();

        ReportWithParent report = mapper.readValue(contentString, ReportWithParent.class);

        File p = new File(report.getParent());

        includes =  report.getIncludes();

        String reportJson = readJson(p);

        System.out.println(reportJson);


    }

    /**
     * This approach does not work.
     *
     */
    public void handleHierarchialJson() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        File p = new File("/Users/a.nigam/Documents/workspace/java-tutorial/src/main/resource/employee.json");
        File c = new File("/Users/a.nigam/Documents/workspace/java-tutorial/src/main/resource/employeeOverride.json");

        ReportDefinition pdef = objectMapper.readValue(readJson(p), ReportDefinition.class);
        ReportDefinition cdef = objectMapper.readValue(readJson(c), ReportDefinition.class);

        System.out.println(objectMapper.writeValueAsString(pdef));

        cdef = partialUpdate(pdef, cdef, objectMapper);

//        System.out.println(objectMapper.writeValueAsString(cdef));
        System.out.println(objectMapper.writeValueAsString(pdef));


    }

    public void handleHierarchialJson1() throws IOException {
        mapper = getObjectMapper();
        File p = new File("/Users/a.nigam/Documents/workspace/java-tutorial/src/main/resource/parentchild/anonymous-answer-summary-parent.json");
        File c = new File("/Users/a.nigam/Documents/workspace/java-tutorial/src/main/resource/parentchild/anonymous-answer-summary-child.json");

        JsonNode pdef = mapper.readTree(p);
        JsonNode cdef = mapper.readTree(c);


        System.out.println(mapper.writeValueAsString(pdef));
        put(pdef, cdef, true);

//        System.out.println(objectMapper.writeValueAsString(cdef));
        System.out.println(mapper.writeValueAsString(pdef));


    }

    protected  void put(JsonNode previousValue, JsonNode value, boolean overwrite) {


            ObjectReader updater = mapper.readerForUpdating(previousValue);
            try {
                value  = mapper.convertValue(value, JsonNode.class);
                ObjectNode valTree = mapper.valueToTree(value);
                updater.readValue(value );
            } catch (IllegalArgumentException | IOException e) {
                throw new IllegalStateException("Unable to put the value", e);
            }

    }

    public <T> T partialUpdate(T target, T source, ObjectMapper objectMapper) throws IOException{
        String json = toJson(source, objectMapper);
        ObjectReader reader = objectMapper.readerForUpdating(target);

        ObjectReader cr = objectMapper.readerForUpdating(source);
        ObjectReader r = cr.withValueToUpdate(target);

            return reader.readValue(json);

    }

    public String toJson(Object obj, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed to generate json for: " + obj, ex);
        }
    }

    public void runPOC() throws IOException {
        ObjectMapper objectMapper = getObjectMapper();


        File f = new File("/Users/a.nigam/Documents/workspace/java-tutorial/src/main/resource/include-test/anonymous-answer-summary-1.json");
        String resourceString = readJson(f);

        System.out.println(resourceString);
//        String line = "$include{metadata}";
//        System.out.println(  line.substring(line.indexOf("{")+ 1 , line.lastIndexOf("}") ));

        ReportDefinition def = objectMapper.readValue(resourceString, ReportDefinition.class);
        System.out.println(def.getMetadata().getId());
        System.out.println(def.getMetadata().getGroups());


//        f = new File("/Users/a.nigam/Documents/workspace/java-tutorial/src/main/resource/anonymous-answer-summary-backup.json");
//        resourceString = readJson(f);
//        ReportDefinition def1 = objectMapper.readValue(resourceString, ReportDefinition.class);
//        System.out.println(def == def1);


    }

    private String readJson(File f) throws IOException {



        StringWriter sw = new StringWriter();
        Pattern p = Pattern.compile("");
        try (BufferedReader reader = new BufferedReader(new FileReader(f))){

            String line = null;
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                // read next line

//                String trimmedLine = line.trim();


                if(line.contains("$includeObject{")){
                    String includedFileName = getIncludedFileName(line);
                    String includedContent = null;
                    if(includes.get(includedFileName) != null){
                        includedContent = readJson(new File(f.getParent() + "/" + includes.get(includedFileName)));

                    }
                    else
                    {
                        includedContent = readJson(new File(f.getParent() + "/" + includedFileName));
                    }

                    line = line.replace("\"$includeObject{"+includedFileName+"}\"" , includedContent);

                }

                if(line.contains("$includeArray{")){
                    String includedFileName = getIncludedArrayFileName(line);
                    String includedContent = readJson(new File(f.getParent() + "/" + includedFileName));
                    includedContent = includedContent.substring(includedContent.indexOf("[")+1, includedContent.lastIndexOf("]"));
                    line = line.replace("\"$includeArray{"+includedFileName+"}\"" , includedContent);
                }

                sw.append(line).append("\n");
            }
            reader.close();
        }

        return sw.toString();
    }

    private String getIncludedFileName(String line) {
        String includeName = line.substring(line.indexOf("$includeObject{") + 15, line.lastIndexOf("}"));


        return includeName;
    }

    private String getIncludedArrayFileName(String line) {
        return   line.substring(line.indexOf("$includeArray{")+ 14 , line.lastIndexOf("}") );
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(ReportDefinition.class, new CustomDeserialiser<ReportDefinition>());
//        mapper.registerModule(module);
        return mapper;
    }
}
