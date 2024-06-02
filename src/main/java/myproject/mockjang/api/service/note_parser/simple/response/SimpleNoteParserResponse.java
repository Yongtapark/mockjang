package myproject.mockjang.api.service.note_parser.simple.response;

import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SimpleNoteParserResponse {

  private HashMap<String, Integer> names;

  @Builder
  private SimpleNoteParserResponse(HashMap<String, Integer> names) {
    this.names = names;
  }

  public static SimpleNoteParserResponse of(HashMap<String, Integer> names) {
    return SimpleNoteParserResponse.builder()
        .names(names)
        .build();
  }
}
