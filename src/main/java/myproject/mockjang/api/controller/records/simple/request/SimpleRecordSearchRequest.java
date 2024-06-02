package myproject.mockjang.api.controller.records.simple.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordSearchServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class SimpleRecordSearchRequest {

  @NotBlank(message = "{exception.codeId.blank}")
  private String codeId;

  @NotNull(message = "{exception.recordType.null}")
  private RecordType recordType;

  @NotNull(message = "{exception.date.null}")
  private LocalDateTime date;

  @NotBlank(message = "{exception.record.blank}")
  private String record;


  @Builder
  private SimpleRecordSearchRequest(String codeId, RecordType recordType, LocalDateTime date,
      String record) {
    this.codeId = codeId;
    this.recordType = recordType;
    this.date = date;
    this.record = record;
  }

  public SimpleRecordSearchServiceRequest toServiceRequest() {
    return SimpleRecordSearchServiceRequest.builder().codeId(codeId).recordType(recordType)
        .date(date).build();
  }
}
