package myproject.mockjang.api.service.records.simple.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class SimpleRecordSearchServiceRequest {

  private String codeId;

  private RecordType recordType;

  private LocalDateTime date;


  @Builder
  private SimpleRecordSearchServiceRequest(String codeId, LocalDateTime date,
      RecordType recordType) {
    this.codeId = codeId;
    this.date = date;
    this.recordType = recordType;
  }
}
