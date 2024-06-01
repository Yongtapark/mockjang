package myproject.mockjang.api.service.records.mockjang.cow.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class CowRecordResponse {
  private Long id;
  private Cow cow;
  private String memo;
  private LocalDateTime date;
  private RecordType recordType;

  @Builder
  private CowRecordResponse(Long id, Cow cow, String memo, LocalDateTime date,
      RecordType recordType) {
    this.id = id;
    this.cow = cow;
    this.memo = memo;
    this.date = date;
    this.recordType = recordType;
  }

  public static CowRecordResponse of(CowRecord record) {
    return CowRecordResponse.builder()
        .id(record.getId())
        .cow(record.getCow())
        .memo(record.getRecord())
        .date(record.getDate())
        .recordType(record.getRecordType())
        .build();
  }
}
