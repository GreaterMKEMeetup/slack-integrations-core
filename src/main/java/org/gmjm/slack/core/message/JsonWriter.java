package org.gmjm.slack.core.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonWriter {
  DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("	", DefaultIndenter.SYS_LF);
  DefaultPrettyPrinter printer = new DefaultPrettyPrinter()
      .withArrayIndenter(indenter)
      .withObjectIndenter(indenter);

  private ObjectWriter objectWriter = new ObjectMapper().writer(printer);

  public String toJson(Object object) throws RuntimeException {
    try {
      return objectWriter.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to write object as JSON.", e);
    }
  }

}
