package com.example.pipeline;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.databricks.sdk.core.ApiClient;
import com.databricks.sdk.core.DatabricksConfig;
import com.databricks.sdk.core.DatabricksException;

public class BmwApiClient extends ApiClient {
    private ObjectMapper om = null;

    public BmwApiClient(DatabricksConfig config) {
        super(config);
    }
    public <I, O> O POST(String path, I in, Class<O> target, Map<String, String> headers) {
        String body = "";
        int size = 0;
        try {
            body = serialize(in);
            size = body.getBytes("UTF-8").length;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        
        headers.put("Content-Length", String.valueOf(size));
        return super.POST(path, in, target, headers);
    }

    public String serialize(Object body) throws JsonProcessingException {
        ObjectMapper mapper = makeObjectMapper();
        return body == null ? null : mapper.writeValueAsString(body);
    }

    private ObjectMapper makeObjectMapper() {
        if (this.om == null) {
            this.om = new ObjectMapper();
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true).configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true).setSerializationInclusion(Include.NON_NULL);
            
        }
        return this.om;
    }
}
