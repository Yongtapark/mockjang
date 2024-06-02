package myproject.mockjang.api.service.note_parser.simple.request;

import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class SimpleNoteParserCreateServiceRequest {

  private String context;
  private LocalDateTime date;
  private RecordType recordType;
  private HashMap<String, Integer> names;

  @Builder
  private SimpleNoteParserCreateServiceRequest(String context, LocalDateTime date,
      RecordType recordType, HashMap<String, Integer> names) {
    this.context = context;
    this.date = date;
    this.recordType = recordType;
    this.names = names;
  }

}
