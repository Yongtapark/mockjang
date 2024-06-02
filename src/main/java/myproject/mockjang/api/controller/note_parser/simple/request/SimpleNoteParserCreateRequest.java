package myproject.mockjang.api.controller.note_parser.simple.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.note_parser.mockjang.request.NoteParserCreateServiceRequest;
import myproject.mockjang.api.service.note_parser.simple.request.SimpleNoteParserCreateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class SimpleNoteParserCreateRequest {
  @NotBlank(message = "내용은 필수로 입력하셔야 합니다.")
  private String context;
  @NotNull(message = "기록 타입은 반드시 입력하셔야 합니다.")
  private RecordType recordType;
  @NotNull(message = "입력 날짜는 반드시 입력하셔야 합니다.")
  private LocalDateTime date;

  @Builder
  public SimpleNoteParserCreateRequest(String context, RecordType recordType, LocalDateTime date) {
    this.context = context;
    this.recordType = recordType;
    this.date = date;
  }
  public SimpleNoteParserCreateServiceRequest toServiceRequest(HashMap<String, Integer> names) {
    return SimpleNoteParserCreateServiceRequest.builder().context(context).names(names).build();
  }
}
