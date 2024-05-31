package myproject.mockjang.api.service.records.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class CowRecordCreateServiceRequest {
  private String cowCode;
  private String memo;
  private LocalDateTime date;
  private RecordType recordType;

  @Builder
  private CowRecordCreateServiceRequest(String cowCode, String memo, LocalDateTime date,
      RecordType recordType) {
    this.cowCode = cowCode;
    this.memo = memo;
    this.date = date;
    this.recordType = recordType;
  }
}
