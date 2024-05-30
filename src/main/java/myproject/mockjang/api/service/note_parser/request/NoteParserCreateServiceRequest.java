package myproject.mockjang.api.service.note_parser.request;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoteParserCreateServiceRequest {
  private String context;
  private HashMap<String,Integer> names;

  @Builder
  private NoteParserCreateServiceRequest(String context, HashMap<String,Integer> names) {
    this.context = context;
    this.names = names;
  }

}
