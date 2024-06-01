package myproject.mockjang.api.service.records.mockjang.cow.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CowRecordRemoveServiceRequest {

  private Long id;

  @Builder
  private CowRecordRemoveServiceRequest(Long id) {
    this.id = id;
  }
}
