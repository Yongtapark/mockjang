package myproject.mockjang.api.service.records.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class CowRecordFindAllByCodeIdAndRecordTypeServiceRequest {

  private String cowCode;
  private RecordType recordType;

  @Builder
  private CowRecordFindAllByCodeIdAndRecordTypeServiceRequest(String cowCode, RecordType recordType) {
    this.cowCode = cowCode;
    this.recordType = recordType;
  }
}
