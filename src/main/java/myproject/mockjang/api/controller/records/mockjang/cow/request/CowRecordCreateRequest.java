package myproject.mockjang.api.controller.records.mockjang.cow.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordCreateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class CowRecordCreateRequest {
  @NotBlank(message = "소 이름은 공백일 수 없습니다.")
  private String cowCode;
  @NotNull(message = "기록 타입은 반드시 입력하셔야 합니다.")
  private RecordType recordType;
  @NotNull(message = "기록 날짜는 반드시 입력하셔야 합니다.")
  private LocalDateTime date;
  @NotBlank(message = "기록 메모는 공백일 수 없습니다.")
  private String memo;

  @Builder
  private CowRecordCreateRequest(String cowCode, RecordType recordType, LocalDateTime date,
      String memo) {
    this.cowCode = cowCode;
    this.recordType = recordType;
    this.date = date;
    this.memo = memo;
  }

  public CowRecordCreateServiceRequest toServiceRequest(){
    return CowRecordCreateServiceRequest.builder()
        .cowCode(cowCode)
        .recordType(recordType)
        .date(date)
        .memo(memo)
        .build();
  }
}
