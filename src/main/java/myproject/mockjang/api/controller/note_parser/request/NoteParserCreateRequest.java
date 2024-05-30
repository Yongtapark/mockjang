package myproject.mockjang.api.controller.note_parser.request;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.note_parser.request.NoteParserCreateServiceRequest;

@Getter
@NoArgsConstructor
public class NoteParserCreateRequest {
  @NotNull(message = "내용은 필수로 입력하셔야 합니다.")
  private String context;

  @Builder
  public NoteParserCreateRequest(String context) {
    this.context = context;
  }
  public NoteParserCreateServiceRequest toServiceRequest(HashMap<String, Integer> names) {
    return NoteParserCreateServiceRequest.builder().context(context).names(names).build();
  }
}
