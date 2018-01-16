package jackson;

import com.cvent.reporting.api.authoring.ReportDefinition;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class CustomDeserialiser extends StdDeserializer<ReportDefinition> {

    public CustomDeserialiser(){
        this(null);
    }
    protected CustomDeserialiser(Class<?> vc) {
        super(vc);
    }

    @Override
    public ReportDefinition deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ReportDefinition val = jp.readValueAs(ReportDefinition.class);
        return val;
    }
}
