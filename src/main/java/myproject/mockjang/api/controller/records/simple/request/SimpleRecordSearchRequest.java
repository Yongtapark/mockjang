package myproject.mockjang.api.controller.records.simple.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordSearchServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class SimpleRecordSearchRequest {

  private String codeId;

  private RecordType recordType;

  private LocalDateTime date;


  @Builder
  private SimpleRecordSearchRequest(String codeId, RecordType recordType, LocalDateTime date) {
    this.codeId = codeId;
    this.recordType = recordType;
    this.date = date;
  }

  public SimpleRecordSearchServiceRequest toServiceRequest() {
    return SimpleRecordSearchServiceRequest.builder().codeId(codeId).recordType(recordType)
        .date(date).build();
  }
}
