package myproject.mockjang.api.service.records.simple.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleRecordRemoveServiceRequest {

  Long id;

  @Builder
  private SimpleRecordRemoveServiceRequest(Long id) {
    this.id = id;
  }
}
