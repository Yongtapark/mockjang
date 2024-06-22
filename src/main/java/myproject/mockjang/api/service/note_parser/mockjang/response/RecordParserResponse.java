package myproject.mockjang.api.service.note_parser.mockjang.response;

import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecordParserResponse {

  private HashMap<String, Integer> names;

  @Builder
  private RecordParserResponse(HashMap<String, Integer> names) {
    this.names = names;
  }

  public static RecordParserResponse of(HashMap<String, Integer> names) {
    return RecordParserResponse.builder()
        .names(names)
        .build();
  }
}
