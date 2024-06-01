package myproject.mockjang.api.controller.records.mockjang.cow.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordFindAllByCodeIdAndRecordTypeServiceRequest;
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
