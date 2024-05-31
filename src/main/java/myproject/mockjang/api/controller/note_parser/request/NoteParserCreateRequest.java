package myproject.mockjang.api.controller.note_parser.request;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.note_parser.request.NoteParserCreateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class NoteParserCreateRequest {
  @NotBlank(message = "내용은 필수로 입력하셔야 합니다.")
  private String context;
  @NotNull(message = "기록 타입은 반드시 입력하셔야 합니다.")
  private RecordType recordType;
  @NotNull(message = "입력 날짜는 반드시 입력하셔야 합니다.")
  private LocalDateTime date;

  @Builder
  public NoteParserCreateRequest(String context, RecordType recordType, LocalDateTime date) {
    this.context = context;
    this.recordType = recordType;
    this.date = date;
  }
  public NoteParserCreateServiceRequest toServiceRequest(HashMap<String, Integer> names) {
    return NoteParserCreateServiceRequest.builder().context(context).names(names).build();
  }
}
