package myproject.mockjang.api.controller.records.simple.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordUpdateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class SimpleRecordUpdateRequest {
  @NotNull(message = "{exception.id.null}")
  private Long id;
  private String codeId;
  private RecordType recordType;
  private LocalDateTime date;
  private String record;

  @Builder
  private SimpleRecordUpdateRequest(Long id, String codeId, RecordType recordType,
      LocalDateTime date, String record) {
    this.id = id;
    this.codeId = codeId;
    this.recordType = recordType;
    this.date = date;
    this.record = record;
  }

  public SimpleRecordUpdateServiceRequest toServiceRequest() {
    return SimpleRecordUpdateServiceRequest.builder()
        .id(id)
        .codeId(codeId)
        .recordType(recordType)
        .date(date)
        .record(record)
        .build();
  }
}
