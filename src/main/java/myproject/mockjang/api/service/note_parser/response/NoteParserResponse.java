package myproject.mockjang.api.service.note_parser.response;

import java.util.HashMap;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NoteParserResponse {
  private HashMap<String,Integer> names;

  @Builder
  private NoteParserResponse(HashMap<String,Integer> names) {
    this.names = names;
  }

  public static NoteParserResponse of(HashMap<String,Integer> names) {
    return NoteParserResponse.builder()
        .names(names)
        .build();
  }
}
