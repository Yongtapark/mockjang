package myproject.mockjang.api.controller.records.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.request.CowRecordFindAllByCodeIdAndRecordTypeServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class CowRecordFindAllByCodeIdAndRecordTypeRequest {

  private String cowCode;
  private RecordType recordType;

  @Builder
  private CowRecordFindAllByCodeIdAndRecordTypeRequest(String cowCode, RecordType recordType) {
    this.cowCode = cowCode;
    this.recordType = recordType;
  }

  public CowRecordFindAllByCodeIdAndRecordTypeServiceRequest toServiceRequest() {
    return CowRecordFindAllByCodeIdAndRecordTypeServiceRequest.builder()
        .cowCode(cowCode)
        .recordType(recordType)
        .build();
  }

}
